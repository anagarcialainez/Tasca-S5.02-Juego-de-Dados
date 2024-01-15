package com.anagarcia.JuegoDeDadosAPI.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "player") // Usamos @Document para MongoDB
public class PlayerEntity {

    @Id
    private String playerId;

    private String name;
    private LocalDateTime registrationDate;

    // Aquí usamos @DBRef para referenciar los juegos asociados al jugador.
    @DBRef
    private List<GameEntity> games;

    // Para la relación uno a uno, también usamos @DBRef.
    @DBRef
    private Usuario usuario;


    // Método para agregar un juego al jugador
    public void addGame(GameEntity game) {
        if (games == null) {
            games = new ArrayList<>();
        }
        games.add(game);
        game.setPlayer(this); // Establecer la referencia inversa al jugador en la tirada
    }

    // Método para calcular el porcentaje de éxito del jugador
    public double calculateSuccessPercentage() {
        if (games == null || games.isEmpty()) {
            return 0.0;
        }

        // Uso de Stream API para contar cuántos juegos son ganadores
        long wins = games.stream().filter(GameEntity::isWinner).count();
        return Math.round(((double) wins / games.size()) * 100.0);
    }
}
