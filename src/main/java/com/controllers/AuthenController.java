package com.controllers;

import com.dtos.AuthenDto;
import com.services.AuthenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenController {

    private final AuthenService authenService;

    public AuthenController(AuthenService authenService) {
        this.authenService = authenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenDto authenDto) {
        try {
            return ResponseEntity.ok(authenService.register(authenDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenDto loginDto) {
        try {
            AuthenDto user = authenService.login(loginDto.getPseudo(), loginDto.getMotDePasse());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage()); // 401 = Non autorisé
        }
    }
}