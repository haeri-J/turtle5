package com.example.demo.service;


import com.example.demo.dto.ClientForm;
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

    // 3. 클라이언트 정보를 저장하고 저장된 정보를 반환하는 메서드
    public Client saveClient(ClientForm dto) {
        // 비밀번호 해싱
        String hashedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        // PW 테이블에 비밀번호 저장
        PW passwordEntity = new PW();
        passwordEntity.setPasswordHash(hashedPassword);
        passwordRepository.save(passwordEntity);

        Client client = dto.toEntity(passwordEntity); //dto를 엔티티 형태로 변환
        if (client.getClientId() != null) {//아직 회원가입이니깐 id에 널값이 들어가있어야함
            return null;
        }
        //.save는 내장함수로 엔티티 형태로 디비에 넣어줌
        return clientRepository.save(client);
    }
}
