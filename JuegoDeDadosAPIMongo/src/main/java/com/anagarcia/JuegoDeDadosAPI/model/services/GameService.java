package com.anagarcia.JuegoDeDadosAPI.model.services;

import com.anagarcia.JuegoDeDadosAPI.exceptions.JugadorNoEncontradoException;
import com.anagarcia.JuegoDeDadosAPI.model.domain.GameEntity;
import com.anagarcia.JuegoDeDadosAPI.model.domain.PlayerEntity;
import com.anagarcia.JuegoDeDadosAPI.model.dto.GameDTO;
import com.anagarcia.JuegoDeDadosAPI.model.repository.GameRepository;
import com.anagarcia.JuegoDeDadosAPI.model.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    //Tirada de dados por jugador especifico
    public GameDTO makeGameMove(String playerId) {
        // Verificar si el jugador con el ID proporcionado existe
        PlayerEntity player = playerRepository.findById(playerId)
                .orElseThrow(() -> new JugadorNoEncontradoException("Jugador no encontrado con ID: " + playerId));

        // Realizar la tirada de los dados y determinar si la partida es ganadora
        int dice1Value = rollDice();
        int dice2Value = rollDice();

        // Crear una nueva entidad de juego y asociarla al jugador
        GameEntity game = new GameEntity();
        game.setPlayer(player);
        game.setDice1Value(dice1Value);
        game.setDice2Value(dice2Value);
        game.setWinner(game.calculateWinner());
        game.setGameDate(LocalDateTime.now());

        // Agregar la tirada a la lista de juegos del jugador
        player.addGame(game);

        // Guardar la tirada en la base de datos
        game = gameRepository.save(game);

        // Convertir la entidad a DTO y devolverla
        return convertEntityToDTO(game);
    }

    @Transactional
    // Eliminar todas las tiradas del jugador con el ID proporcionado
    public void deletePlayerGames(String playerId) {
        gameRepository.deleteByPlayer_PlayerId(playerId);
    }

    // Obtener todas las jugadas de un jugador por su ID
    public List<GameDTO> getPlayerGames(String playerId) {
        // Verificar si el jugador con el ID proporcionado existe
        PlayerEntity player = playerRepository.findById(playerId)
                .orElseThrow(() -> new JugadorNoEncontradoException("Jugador no encontrado con ID: " + playerId));

        // Obtener todas las jugadas del jugador
        List<GameEntity> playerGames = gameRepository.findByPlayer_PlayerId(playerId);

        // Convertir las entidades a DTO y devolver la lista
        return playerGames.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }


    // Método de utilidad para simular el lanzamiento de un dado
    private int rollDice() {
        Random random = new Random();
        // El número 6 representa la cantidad de caras en un dado
        return random.nextInt(6) + 1;
    }

    // Método de utilidad para convertir GameEntity a GameDTO
    private GameDTO convertEntityToDTO(GameEntity gameEntity) {
        GameDTO gameDTO = new GameDTO();

        // Setear el ID de la partida
        gameDTO.setGameId(gameEntity.getGameId());

        // Setear los valores de los dados
        gameDTO.setDice1Value(gameEntity.getDice1Value());
        gameDTO.setDice2Value(gameEntity.getDice2Value());

        // Setear el resultado de la partida (ganador o perdedor)
        gameDTO.setWinner(gameEntity.isWinner());

        // Setear la fecha de la partida
        gameDTO.setGameDate(gameEntity.getGameDate());

        return gameDTO;
    }


}
