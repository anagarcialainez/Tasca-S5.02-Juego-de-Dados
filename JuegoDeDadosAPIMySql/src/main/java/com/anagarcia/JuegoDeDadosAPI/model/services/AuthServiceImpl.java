package com.anagarcia.JuegoDeDadosAPI.model.services;

import com.anagarcia.JuegoDeDadosAPI.config.JwtService;
import com.anagarcia.JuegoDeDadosAPI.model.domain.Role;
import com.anagarcia.JuegoDeDadosAPI.model.domain.Usuario;
import com.anagarcia.JuegoDeDadosAPI.model.dto.AuthResponse;
import com.anagarcia.JuegoDeDadosAPI.model.dto.LoginDto;
import com.anagarcia.JuegoDeDadosAPI.model.dto.UserRegistrationDto;
import com.anagarcia.JuegoDeDadosAPI.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthResponse register(UserRegistrationDto registrationDto) {
        var user = Usuario.builder()
                .username(registrationDto.getUsername())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.PLAYER)
                .build();
        usuarioRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        var user = usuarioRepository.findUsuarioByUsername(loginDto.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    //Obtener el objeto Usuario correspondiente al usuario actualmente autenticado.
    @Override
    public Usuario getUsuarioFromAuthentication(Authentication authentication) {
        String username = authentication.getName();
        return usuarioRepository.findUsuarioByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

}
