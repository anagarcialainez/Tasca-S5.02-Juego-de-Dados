package com.anagarcia.JuegoDeDadosAPI.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Long playerId;
    private String name;
    private LocalDateTime registrationDate;
    private double successPercentage;
}

//@AllArgsConstructor genera automáticamente un constructor que acepta todos los campos.