package com.example.demo.repository;

import com.example.demo.entity.PW;
import org.springframework.data.repository.CrudRepository;

public interface PasswordRepository extends CrudRepository<PW, Integer> {
}
