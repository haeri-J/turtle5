package com.example.demo.service;


import com.example.demo.dto.ClientForm;
import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // 3. 클라이언트 정보를 저장하고 저장된 정보를 반환하는 메서드
    public Client saveClient(ClientForm dto) {
        Client client = dto.toEntity(); //dto를 엔티티 형태로 변환
//        if (client.getClientId() != null) {
//            return null;
//        }
        //.save는 내장함수로 엔티티 형태로 디비에 넣어줌
        return clientRepository.save(client);
    }
}
