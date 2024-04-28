package com.example.demo.service;

import com.example.demo.dto.CustomerUserDetail;
import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }

        Client userData = clientRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일로는 찾을 수 없습니다: " + username));

        return new CustomerUserDetail(userData);
    }

}
