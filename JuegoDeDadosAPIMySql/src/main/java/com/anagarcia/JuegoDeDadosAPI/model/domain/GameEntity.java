package com.anagarcia.JuegoDeDadosAPI.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    // Relación muchos a uno con PlayerEntity
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @Column(name = "dice1_value")
    private int dice1Value;

    @Column(name = "dice2_value")
    private int dice2Value;

    @Column(name = "is_winner")
    private boolean isWinner;

    @Column(name = "game_date")
    private LocalDateTime gameDate;


    // Método para determinar si la tirada es ganadora
    public boolean calculateWinner() {
        return (dice1Value + dice2Value) == 7;
    }

}
// @ManyToOne varias tiradas pueden pertenecer a un solo jugador.

