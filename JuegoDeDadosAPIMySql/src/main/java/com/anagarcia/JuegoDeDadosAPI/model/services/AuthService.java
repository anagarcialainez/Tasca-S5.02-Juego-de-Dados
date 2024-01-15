package com.anagarcia.JuegoDeDadosAPI.model.services;

import com.anagarcia.JuegoDeDadosAPI.model.domain.Usuario;
import com.anagarcia.JuegoDeDadosAPI.model.dto.AuthResponse;
import com.anagarcia.JuegoDeDadosAPI.model.dto.LoginDto;
import com.anagarcia.JuegoDeDadosAPI.model.dto.UserRegistrationDto;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthResponse register (UserRegistrationDto registrationDto);
    AuthResponse authenticate (LoginDto loginDto);

    Usuario getUsuarioFromAuthentication(Authentication authentication);
}


