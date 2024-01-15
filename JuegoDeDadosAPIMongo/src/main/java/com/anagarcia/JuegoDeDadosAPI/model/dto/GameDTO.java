package com.anagarcia.JuegoDeDadosAPI.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    private String gameId;
    private int dice1Value;
    private int dice2Value;
    private boolean isWinner;
    private LocalDateTime gameDate;

}
