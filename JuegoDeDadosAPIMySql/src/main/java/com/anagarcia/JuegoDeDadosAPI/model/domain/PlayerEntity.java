package com.anagarcia.JuegoDeDadosAPI.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    private String name;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    // Relación uno a muchos con GameEntity
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<GameEntity> games;

    //Relación uno a uno entre PlayerEntity y Usuario
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
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
//@Column(unique = true, nullable = true) indica que el campo nombre debe ser único si no es nulo. Esto garantiza que no haya jugadores con el mismo nombre (excepto "ANÓNIMO" que puede repetirse).
//El atributo mappedBy = "jugador" indica que la relación está mapeada por el campo player en la entidad Tirada.