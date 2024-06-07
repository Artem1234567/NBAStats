package com.example.nbastatistics.service;

import com.example.nbastatistics.model.output.TeamStatsDTO;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

public interface TeamStatisticsService {

    CompletableFuture<TeamStatsDTO> getTeamStatsForSeason(Long teamId, LocalDate startDate, LocalDate endDate);
}
