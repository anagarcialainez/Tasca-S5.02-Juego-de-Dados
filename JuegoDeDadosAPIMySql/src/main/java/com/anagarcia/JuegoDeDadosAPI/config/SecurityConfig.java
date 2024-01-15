package com.anagarcia.JuegoDeDadosAPI.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig{

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(auth -> auth
                       .requestMatchers("/players/registration").permitAll()
                       .requestMatchers("/players/ranking/**", "/players").permitAll()
                       .requestMatchers("/api/auth/**").permitAll()
                       .requestMatchers("/players/{id}/games/**").authenticated()
                       .anyRequest().authenticated())
               .sessionManagement(session -> session
                       .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider)
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
       return http.build();
   }

}
/*define qué rutas son accesibles para todos y cuáles requieren autenticación.*/