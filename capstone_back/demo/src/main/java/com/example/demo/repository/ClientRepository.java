package com.example.demo.repository;

import com.example.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);


    Client findByNameAndPhoneNo(String name, String phoneNo);

    Client findByNameAndPhoneNoAndEmail(String name, String phoneNo, String email);
}
