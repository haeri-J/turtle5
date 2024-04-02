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
        if (client != null && client.getPasswordId().equals(loginRequestDto.getPassword())) {
            return "success";
        } else {
            return "failure";
        }
    }
}
