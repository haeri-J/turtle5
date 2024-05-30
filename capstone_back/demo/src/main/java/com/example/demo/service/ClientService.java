package com.example.demo.service;


import com.example.demo.dto.*;
import com.example.demo.entity.AlarmLog;
import com.example.demo.entity.Client;
import com.example.demo.entity.PW;
import com.example.demo.entity.WebCamLog;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.jwt.RedisUtil;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordRepository passwordRepository; // 비밀번호 정보를 다루는 레포지토리
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private WebCamLogRepository webCamLogRepository;
    @Autowired
    private AlarmLogRepository alarmLogRepository;


    @Autowired
    private final PasswordEncoder passwordEncoder;
    // 클라이언트 정보를 저장하고 저장된 정보를 반환하는 메서드
    public Client saveClient(ClientForm dto) {
        // 이메일이 시스템에 이미 존재하는 경우
        if (!isEmailUnique(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일이므로 다른 이메일 아이디를 사용해 주세요.");
        }

        // 비밀번호 해싱
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        // PW 테이블에 비밀번호 저장
        PW passwordEntity = new PW();
        passwordEntity.setPasswordHash(hashedPassword);
        passwordRepository.save(passwordEntity);

        Client client = dto.toEntity(passwordEntity); //dto를 엔티티 형태로 변환
        if (client.getClientId() != null) { //아직 회원가입이니깐 id에 널값이 들어가 있어야함
            return null;
        }
        //.save는 내장함수로 엔티티 형태로 디비에 넣어줌(이때 실제 id 할당)
        return clientRepository.save(client);
    }

    // 클라이언트 등록 전 isEmailUnique() 메소드를 이용하여 제공된 이메일 주소가 고유한지 확인
    public boolean isEmailUnique(String email) {
        return clientRepository.findByEmail(email).isEmpty(); // null이면 이메일 사용 가능
    }

    public boolean checkEmailUnique(String email) {
        return !isEmailUnique(email);
    }

    public JwtToken login(String emaill, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(emaill, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성 -> 둘 중 어느 토큰 생성??
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    public String logout(String accessToken, Client client) {

        // refreshToken 테이블의 refreshToken 삭제
        refreshTokenRepository.deleteByToken(client.getEmail());
        // Redis에 accessToken 사용 못하도록 등록
        redisUtil.setBlackList(accessToken, "accessToken", 5);

        return "Successfully Logout";
    }

    //아이디 찾기 서비스 구현
    public String checktoFindId(FindIDDto findIDDto) {
        String checkname = findIDDto.getName();
        String checkphoneNo = findIDDto.getPhoneNo();
        Client checkClient = clientRepository.findByNameAndPhoneNo(checkname,checkphoneNo);
        if (checkClient != null) {
            return checkClient.getEmail();
        } else {
            return "회원 정보를 조회할 수 없습니다.";
        }
    }

    public String checktoFindPassword(FindPasswordDto findPasswordDto) {
        Client checkClient = clientRepository.findByNameAndPhoneNoAndEmail(findPasswordDto.getName(),findPasswordDto.getPhoneNo(),findPasswordDto.getEmail());
        if (checkClient != null) {
            return checkClient.getEmail();
        } else {
            return "fail";
        }
    }

    public PW setPassword(SetPassword setPassword) {
        Client setdata = clientRepository.findByEmail(setPassword.getEmail()).orElseThrow(() -> new UsernameNotFoundException("잘못된 입력값입니다."));
        String hashedPassword = passwordEncoder.encode(setPassword.getPassword());
        // PW 테이블에 비밀번호 저장
        PW passwordEntity = new PW();
        passwordEntity.setPasswordId(setdata.getPasswordId().getPasswordId());
        passwordEntity.setPasswordHash(hashedPassword);
        return passwordRepository.save(passwordEntity);

    }

    public UserInfoDto getforMypage(Long clientId) {
        Client userInfo = clientRepository.findById(clientId).orElseThrow(()->new UsernameNotFoundException("사용자의 정보를 찾을 수 없습니다."));

        LocalDate todayDate = LocalDate.now();
        LocalDateTime startOfDay = todayDate.atStartOfDay();
        LocalDateTime endOfDay = todayDate.atTime(LocalTime.MAX);

        // 오늘 날짜의 WebCamLog 조회
        List<WebCamLog> todaysWebCamLogs = webCamLogRepository.findByClientIdAndStartTimeBetween(userInfo, startOfDay, endOfDay);
        // 오늘 날짜의 AlarmLog 조회
        List<AlarmLog> todaysAlarmLogs = alarmLogRepository.findByClientIdAndDateTimeBetween(userInfo, startOfDay, endOfDay);

        // 웹캠의 총 실행 시간 계산
        double totalWebCamDuration = todaysWebCamLogs.stream()
                .mapToLong(log -> Duration.between(log.getStartTime().toLocalTime(), log.getEndTime().toLocalTime()).toMinutes())
                .sum();
        double totalAlarmCounut = (long) todaysAlarmLogs.size();

        return new UserInfoDto(userInfo.getName(),userInfo.getEmail(),userInfo.getPhoneNo(),(int)totalAlarmCounut, (int)totalWebCamDuration);
    }

    public MemberCount countMember() {


        List<Long> allClientIds = clientRepository.findAll().stream()
                    .map(Client::getClientId)
                    .toList();

        LocalDate todayDate = LocalDate.now();
        LocalDateTime startOfDay = todayDate.atStartOfDay();
        LocalDateTime endOfDay = todayDate.atTime(LocalTime.MAX);

        // 오늘 날짜의 AlarmLog 조회
        List<AlarmLog> todaysAlarmLogs = alarmLogRepository.findByDateTimeBetween(startOfDay, endOfDay);

        // 오늘 날짜의 AlarmLog에 연결된 고유한 Client의 수를 계산
        long count = todaysAlarmLogs.stream()
                .map(AlarmLog::getClientId)
                .distinct()
                .count();


        return new MemberCount(allClientIds.size(),(int) count);


    }
}

