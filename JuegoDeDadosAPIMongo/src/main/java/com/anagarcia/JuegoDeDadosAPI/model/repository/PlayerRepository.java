package com.anagarcia.JuegoDeDadosAPI.model.repository;

import com.anagarcia.JuegoDeDadosAPI.model.domain.PlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends MongoRepository<PlayerEntity, String> {
    boolean existsByNameIgnoreCase(String name);
    Optional<PlayerEntity> findByUsuarioId(String usuarioId);
}

