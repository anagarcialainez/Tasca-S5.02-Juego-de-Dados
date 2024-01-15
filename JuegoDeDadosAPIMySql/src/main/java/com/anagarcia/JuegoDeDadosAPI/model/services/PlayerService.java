package com.anagarcia.JuegoDeDadosAPI.model.services;

import com.anagarcia.JuegoDeDadosAPI.exceptions.JugadorNoEncontradoException;
import com.anagarcia.JuegoDeDadosAPI.exceptions.NombreDeJugadorRepetidoException;
import com.anagarcia.JuegoDeDadosAPI.model.domain.PlayerEntity;
import com.anagarcia.JuegoDeDadosAPI.model.domain.Role;
import com.anagarcia.JuegoDeDadosAPI.model.domain.Usuario;
import com.anagarcia.JuegoDeDadosAPI.model.dto.PlayerDTO;
import com.anagarcia.JuegoDeDadosAPI.model.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public PlayerDTO createPlayer(PlayerDTO playerDTO, Usuario usuario) {
        String playerName = playerDTO.getName();

        // Validar y asignar un nombre predeterminado si es necesario
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "ANÓNIMO";
        }

        // Verificar si ya existen jugadores con el mismo nombre (excepto si el nombre es "ANÓNIMO")
        if (!playerName.equals("ANÓNIMO") && playerRepository.existsByNameIgnoreCase(playerName)) {
            throw new NombreDeJugadorRepetidoException("El nombre de jugador ya está en uso.");
        }

        // Crear una nueva entidad de jugador
        PlayerEntity player = new PlayerEntity();
        player.setName(playerName);
        player.setRegistrationDate(LocalDateTime.now());
        player.setUsuario(usuario); // Asignar el usuario al jugador


        // Guardar el jugador en la base de datos
        player = playerRepository.save(player);

        // Convertir la entidad a DTO
        PlayerDTO createdPlayerDTO = convertEntityToDTO(player);
        return createdPlayerDTO;
    }


    public PlayerDTO updatePlayer(Long playerId, PlayerDTO updatePlayerDTO) {
        // Verificar si el jugador con el ID proporcionado existe
        PlayerEntity existingPlayer = playerRepository.findById(playerId)
                .orElseThrow(() -> new JugadorNoEncontradoException("Jugador no encontrado con ID: " + playerId));

        // Actualizar el nombre del jugador si se proporciona un nuevo nombre
        String newPlayerName = updatePlayerDTO.getName();
        if (newPlayerName != null && !newPlayerName.trim().isEmpty()) {
            // Verificar si el nuevo nombre ya está en uso
            if (!existingPlayer.getName().equalsIgnoreCase(newPlayerName) &&
                    playerRepository.existsByNameIgnoreCase(newPlayerName)) {
                throw new NombreDeJugadorRepetidoException("El nuevo nombre de jugador ya está en uso.");
            }

            // Actualizar el nombre del jugador
            existingPlayer.setName(newPlayerName);
        }

        // Guardar la actualización en la base de datos
        existingPlayer = playerRepository.save(existingPlayer);

        // Convertir la entidad actualizada a DTO
        PlayerDTO updatedPlayerDTO = convertEntityToDTO(existingPlayer);
        return updatedPlayerDTO;
    }

    //listado de todos los jugadores en el sistema con su porcentaje promedio de éxitos.
    @Transactional
    public List<PlayerDTO> getAllPlayersWithSuccessPercentage() {
        // Obtener todos los jugadores del sistema
        List<PlayerEntity> players = playerRepository.findAll();

        // Convertir las entidades a DTO y calcular el porcentaje de éxito para cada jugador
        List<PlayerDTO> playerDTOs = players.stream()
                .map(player -> {
                    PlayerDTO playerDTO = convertEntityToDTO(player);
                    playerDTO.setSuccessPercentage(player.calculateSuccessPercentage());
                    return playerDTO;
                })
                .collect(Collectors.toList());

        return playerDTOs;
    }

    // Obtener el ranking promedio de todos los jugadores
    public double getPlayersRanking() {
        // Obtener todos los jugadores del sistema
        List<PlayerEntity> players = playerRepository.findAll();

        // Verificar si hay jugadores en el sistema
        if (players == null || players.isEmpty()) {
            return 0.0;
        }

        // Calcular el porcentaje promedio de éxitos para todos los jugadores
        double averageSuccessPercentage = players.stream()
                .mapToDouble(PlayerEntity::calculateSuccessPercentage)
                .average()
                .orElse(0.0);

        return averageSuccessPercentage;
    }

    // Obtener el jugador con peor porcentaje de éxito
    public PlayerDTO getLoserPlayer() {
        // Obtener todos los jugadores del sistema
        List<PlayerEntity> players = playerRepository.findAll();

        // Verificar si hay jugadores en el sistema
        if (players.isEmpty()) {
            throw new JugadorNoEncontradoException("No hay jugadores en el sistema.");
        }

        // Encontrar al jugador con el peor porcentaje de éxito
        PlayerEntity loserPlayer = players.stream()
                .min(Comparator.comparingDouble(PlayerEntity::calculateSuccessPercentage))
                .orElseThrow(() -> new JugadorNoEncontradoException("No se pudo encontrar al jugador con peor porcentaje de éxito."));

        // Convertir la entidad a DTO y devolverla
        return convertEntityToDTO(loserPlayer);
    }

    // Obtener el jugador con mejor porcentaje de éxito
    public PlayerDTO getWinnerPlayer() {
        // Obtener todos los jugadores del sistema
        List<PlayerEntity> players = playerRepository.findAll();

        // Verificar si hay jugadores en el sistema
        if (players.isEmpty()) {
            throw new JugadorNoEncontradoException("No hay jugadores en el sistema.");
        }

        // Encontrar al jugador con el mejor porcentaje de éxito
        PlayerEntity winnerPlayer = players.stream()
                .max(Comparator.comparingDouble(PlayerEntity::calculateSuccessPercentage))
                .orElseThrow(() -> new JugadorNoEncontradoException("No se pudo encontrar al jugador con mejor porcentaje de éxito."));

        // Convertir la entidad a DTO y devolverla
        return convertEntityToDTO(winnerPlayer);
    }

    public List<PlayerDTO> getAllPlayers(Usuario usuario) {
        if (usuario.getRole() == Role.ADMIN) {
            return getAllPlayersWithSuccessPercentage();
        } else if (usuario.getRole() == Role.PLAYER) {
            PlayerEntity player = findPlayerByUsuario(usuario);
            return player != null ? List.of(convertEntityToDTO(player)) : Collections.emptyList();
        } else {
            throw new AccessDeniedException("Rol no autorizado para esta operación");
        }
    }

    private PlayerEntity findPlayerByUsuario(Usuario usuario) {
        return playerRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new JugadorNoEncontradoException("Jugador no encontrado para el usuario: "
                        + usuario.getUsername()));
    }

    // Método de utilidad para convertir PlayerEntity a PlayerDTO
    private PlayerDTO convertEntityToDTO(PlayerEntity playerEntity) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlayerId(playerEntity.getPlayerId());
        playerDTO.setName(playerEntity.getName());
        playerDTO.setRegistrationDate(playerEntity.getRegistrationDate());
        playerDTO.setSuccessPercentage(playerEntity.calculateSuccessPercentage());
        // Puedes agregar más campos según sea necesario
        return playerDTO;
    }
}
/* La adición de la anotación @Transactional es adecuada, ya que protege la operación de lectura de jugadores dentro de una transacción.*/