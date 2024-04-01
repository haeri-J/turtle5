package com.example.demo.repository;

import com.example.demo.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {//id타입 int

}
