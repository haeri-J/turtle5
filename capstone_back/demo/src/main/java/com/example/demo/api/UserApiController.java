package com.example.demo.api;

import com.example.demo.dto.AddUserRequest;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Configuration
public class UserApiController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> signup(AddUserRequest request) {
        userService.save(request);
        //return "redirect:/login";
        return ResponseEntity.ok("회원가입 성공");
    }
}
