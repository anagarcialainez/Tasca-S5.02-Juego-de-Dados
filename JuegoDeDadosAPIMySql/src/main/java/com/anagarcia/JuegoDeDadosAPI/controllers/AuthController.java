package com.anagarcia.JuegoDeDadosAPI.controllers;


import com.anagarcia.JuegoDeDadosAPI.model.dto.AuthResponse;
import com.anagarcia.JuegoDeDadosAPI.model.dto.LoginDto;
import com.anagarcia.JuegoDeDadosAPI.model.dto.UserRegistrationDto;
import com.anagarcia.JuegoDeDadosAPI.model.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserRegistrationDto registrationDto){
        return ResponseEntity.ok(authService.register(registrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(authService.authenticate(loginDto));
    }

}
