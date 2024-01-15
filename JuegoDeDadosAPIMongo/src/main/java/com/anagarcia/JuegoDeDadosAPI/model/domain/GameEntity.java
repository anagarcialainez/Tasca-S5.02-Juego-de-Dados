package com.anagarcia.JuegoDeDadosAPI.model.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "game")
public class GameEntity {

    @Id
    private String gameId;

    @DBRef
    private PlayerEntity player;

    private int dice1Value;
    private int dice2Value;
    private boolean isWinner;
    private LocalDateTime gameDate;


    // Método para determinar si la tirada es ganadora
    public boolean calculateWinner() {
        return (dice1Value + dice2Value) == 7;
    }

}

// En MongoDB, las relaciones se manejan diferente para mantener la relación se utiliza @DBRef
// El ID en MongoDB suele ser un String
// Usamos @Document para MongoDB
