package com.example.nbastatistics.service;

import com.example.nbastatistics.model.input.PlayerStatsRequest;
import com.example.nbastatistics.model.output.PlayerStatsDTO;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

public interface PlayerStatisticsService {

    CompletableFuture<Void> savePlayerStatistics(Long playerId,
                                                 PlayerStatsRequest playerStatsRequest);
    CompletableFuture<PlayerStatsDTO> getPlayerStatsForSeason(Long playerId,
                                                                     LocalDate startDate, LocalDate endDate);
}
