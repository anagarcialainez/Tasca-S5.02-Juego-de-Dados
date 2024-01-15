package com.anagarcia.JuegoDeDadosAPI.model.repository;

import com.anagarcia.JuegoDeDadosAPI.model.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findUsuarioByUsername(String username);
}
