package com.anagarcia.JuegoDeDadosAPI.model.repository;

import com.anagarcia.JuegoDeDadosAPI.model.domain.GameEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<GameEntity, String> {
    // Obtener todas las tiradas de un jugador
    List<GameEntity> findByPlayer_PlayerId(String playerId);

    // Eliminar todas las tiradas de un jugador
    void deleteByPlayer_PlayerId(String playerId);

}
