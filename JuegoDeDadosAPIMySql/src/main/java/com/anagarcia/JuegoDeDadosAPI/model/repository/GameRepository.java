package com.anagarcia.JuegoDeDadosAPI.model.repository;

import com.anagarcia.JuegoDeDadosAPI.model.domain.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    // Obtener todas las tiradas de un jugador
    List<GameEntity> findByPlayer_PlayerId(Long playerId);

    // Eliminar todas las tiradas de un jugador
    void deleteByPlayer_PlayerId(Long playerId);

}
