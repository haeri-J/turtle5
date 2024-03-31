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

    // 클라이언트 정보를 저장하고 저장된 정보를 반환하는 메서드
    public Client saveClient(ClientForm dto) {
        Client client = dto.toEntity();
//        if (client.getClientId() != null) {
//            return null;
//        }
        return clientRepository.save(client);
    }
}
