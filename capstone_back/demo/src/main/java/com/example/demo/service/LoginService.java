package com.example.demo.service;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private ClientRepository clientRepository;

    public String login(LoginRequestDto loginRequestDto) {
        // Retrieve user by email from the database
        Client client = clientRepository.findByEmail(loginRequestDto.getEmail());

        // Check if the user exists and if the provided password matches
        // Client에서 PW의 password를 참조하는 외래키로 값을 get하면 되는데 아직 외래키 설정 못함
//        if (client != null && client.getPasswordId().equals(loginRequestDto.getPassword())) {
//            return "success";
//        } else {
//            return "failure";
//        }
        return null;
    }
}
