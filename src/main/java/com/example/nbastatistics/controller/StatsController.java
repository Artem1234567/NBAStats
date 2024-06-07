package com.example.nbastatistics.controller;

import com.example.nbastatistics.model.input.PlayerStatsRequest;
import com.example.nbastatistics.service.PlayerStatisticsService;
import com.example.nbastatistics.service.TeamStatisticsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/stats")
public class StatsController {
    private final PlayerStatisticsService playerStatisticsService;
    private final TeamStatisticsService teamStatisticsService;

    @PostMapping("/players/{playerId}")
    public ResponseEntity<String> savePlayerStatistics(@PathVariable Long playerId,
                                                  @Valid @RequestBody PlayerStatsRequest playerStatsRequest) {
        try {
            playerStatisticsService.savePlayerStatistics(playerId, playerStatsRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Statistics for player saved successfully");
        } catch (Exception ex) {
            log.error("Error saving player statistics: {}" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save player statistics");
        }
    }

    @GetMapping("/players/{playerId}")
    public CompletableFuture<ResponseEntity<?>> getAveragePlayerStatsForSeason(@PathVariable Long playerId,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return playerStatisticsService.getPlayerStatsForSeason(playerId, startDate, endDate)
                .thenApply(playerStatistics -> {
                    if (playerStatistics.getPlayerId() == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    } else {
                        return new ResponseEntity<>(playerStatistics, HttpStatus.OK);
                    }
                })
            .exceptionally(ex -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/teams/{teamId}")
    public CompletableFuture<ResponseEntity<?>> getAverageTeamStatsForSeason(@PathVariable Long teamId,
                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return teamStatisticsService.getTeamStatsForSeason(teamId, startDate, endDate)
                .thenApply(teamStatistics -> {
                    if (teamStatistics.getTeamId() == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    } else {
                        return new ResponseEntity<>(teamStatistics, HttpStatus.OK);
                    }
                })
                .exceptionally(ex -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
