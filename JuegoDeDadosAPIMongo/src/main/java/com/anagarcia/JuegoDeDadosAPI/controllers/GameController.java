package com.anagarcia.JuegoDeDadosAPI.controllers;

import com.anagarcia.JuegoDeDadosAPI.model.dto.GameDTO;
import com.anagarcia.JuegoDeDadosAPI.model.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping("/players/{id}/games")
    public ResponseEntity<GameDTO> makeGameMove(@PathVariable String id) {
        GameDTO gameDTO = gameService.makeGameMove(id);
        return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<Void> deletePlayerGames(@PathVariable String id) {
        gameService.deletePlayerGames(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/players/{id}/games")
    public ResponseEntity<List<GameDTO>> getPlayerGames(@PathVariable String id) {
        List<GameDTO> playerGames = gameService.getPlayerGames(id);
        return new ResponseEntity<>(playerGames, HttpStatus.OK);
    }
}
