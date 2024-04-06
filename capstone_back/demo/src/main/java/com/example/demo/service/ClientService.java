package com.example.demo.service;


import com.example.demo.dto.ClientForm;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.PW;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordRepository passwordRepository; // 비밀번호 정보를 다루는 레포지토리

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // 클라이언트 정보를 저장하고 저장된 정보를 반환하는 메서드
    public Client saveClient(ClientForm dto) {

        // 이메일이 시스템에 이미 존재하는 경우
        if(!isEmailUnique(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일이므로 다른 이메일 아이디를 사용해 주세요.");        }

        // 비밀번호 해싱
        String hashedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        // PW 테이블에 비밀번호 저장
        PW passwordEntity = new PW();
        passwordEntity.setPasswordHash(hashedPassword);
        passwordRepository.save(passwordEntity);

        Client client = dto.toEntity(passwordEntity); //dto를 엔티티 형태로 변환
        if (client.getClientId() != null) { //아직 회원가입이니깐 id에 널값이 들어가 있어야함
            return null;
        }
        //.save는 내장함수로 dto에 저장된 내용을 엔티티 형태로 디비에 넣어줌(이때 실제 id 할당)
        return clientRepository.save(client);
    }
    // 클라이언트 등록 전 isEmailUnique() 메소드를 이용하여 제공된 이메일 주소가 고유한지 확인
    public boolean isEmailUnique(String email) {
        return clientRepository.findByEmail(email) == null; // null이면 이메일 사용 가능
    }

    public String login(LoginRequestDto loginRequestDto) {
        // Retrieve user by email from the database
        Client client = clientRepository.findByEmail(loginRequestDto.getEmail());
        PW hashedPW = passwordRepository.findByPasswordId(client.getPasswordId());

        // Check if the user exists and if the provided password matches
        if (client != null && bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), hashedPW.getPasswordHash())) {
            return "success";
        } else {
            return "failure";
        }
    }
}

