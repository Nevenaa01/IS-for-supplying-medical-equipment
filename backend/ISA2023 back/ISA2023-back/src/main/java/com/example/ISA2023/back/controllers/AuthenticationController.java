package com.example.ISA2023.back.controllers;

import com.example.ISA2023.back.dtos.JwtAuthenticationRequest;
import com.example.ISA2023.back.dtos.UserTokenState;
import com.example.ISA2023.back.user.User;
import com.example.ISA2023.back.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {


    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        Optional<User> existUser = this.userService.findByUsername(user.getUsername());

        if (existUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = this.userService.save(user);

        userService.sendVerificationEmail(savedUser);
        System.out.println("Sending email...");

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping(value = "login", consumes = "application/json")
    public ResponseEntity<UserTokenState> login(@RequestBody JwtAuthenticationRequest loginDto, HttpServletRequest request){
        return ResponseEntity.ok(userService.login(loginDto));
    }
}
