package com.anagarcia.JuegoDeDadosAPI.controllers;

import com.anagarcia.JuegoDeDadosAPI.model.domain.Usuario;
import com.anagarcia.JuegoDeDadosAPI.model.dto.PlayerDTO;
import com.anagarcia.JuegoDeDadosAPI.model.services.AuthService;
import com.anagarcia.JuegoDeDadosAPI.model.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private AuthService authService;

    @PostMapping("/players")
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody PlayerDTO playerDTO, Authentication authentication) {
        // Obtener el usuario autenticado
        Usuario usuarioAutenticado = authService.getUsuarioFromAuthentication(authentication);

        // Llamar a createPlayer con ambos argumentos
        PlayerDTO createdPlayer = playerService.createPlayer(playerDTO, usuarioAutenticado);

        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable String id, @RequestBody PlayerDTO updatePlayerDTO) {
        PlayerDTO updatedPlayer = playerService.updatePlayer(id, updatePlayerDTO);
        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayersWithSuccessPercentage() {
        List<PlayerDTO> players = playerService.getAllPlayersWithSuccessPercentage();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/players/ranking")
    public ResponseEntity<Double> getPlayersRanking() {
        double ranking = playerService.getPlayersRanking();
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }

    @GetMapping("/players/ranking/loser")
    public ResponseEntity<PlayerDTO> getLoserPlayer() {
        PlayerDTO loserPlayer = playerService.getLoserPlayer();
        return new ResponseEntity<>(loserPlayer, HttpStatus.OK);
    }

    @GetMapping("/players/ranking/winner")
    public ResponseEntity<PlayerDTO> getWinnerPlayer() {
        PlayerDTO winnerPlayer = playerService.getWinnerPlayer();
        return new ResponseEntity<>(winnerPlayer, HttpStatus.OK);
    }
}
