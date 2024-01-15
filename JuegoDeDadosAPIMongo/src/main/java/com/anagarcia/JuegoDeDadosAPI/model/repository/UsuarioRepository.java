package com.anagarcia.JuegoDeDadosAPI.model.repository;

import com.anagarcia.JuegoDeDadosAPI.model.domain.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findUsuarioByUsername(String username);
}
