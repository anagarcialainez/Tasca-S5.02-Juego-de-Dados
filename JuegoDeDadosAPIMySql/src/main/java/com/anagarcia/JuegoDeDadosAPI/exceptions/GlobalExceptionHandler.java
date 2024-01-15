package com.anagarcia.JuegoDeDadosAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NombreDeJugadorRepetidoException.class)
    public ResponseEntity<String> handleNombreDeJugadorDuplicadoException(NombreDeJugadorRepetidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(JugadorNoEncontradoException.class)
    public ResponseEntity<String> handleJugadorNoEncontradoException(JugadorNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}

/* El método handleNombreDeJugadorDuplicadoException maneja específicamente la excepción NombreDeJugadorDuplicadoException y
devuelve una respuesta con un código de estado 400 y el mensaje de la excepción como cuerpo.*/