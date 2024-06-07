package com.example.nbastatistics.model.mapper;

import com.example.nbastatistics.model.PlayerStatistics;
import com.example.nbastatistics.model.input.PlayerStatsRequest;
import org.springframework.stereotype.Component;

@Component
public class PlayerStatisticsMapper implements Mapper<PlayerStatistics, PlayerStatsRequest> {

    @Override
    public PlayerStatistics map(PlayerStatistics playerStatistics, PlayerStatsRequest playerStats) {
//        playerStatistics.setPlayerId(playerStats.getPlayerId());
        playerStatistics.setGameId(playerStats.getGameId());

        playerStatistics.setAveragePoints(playerStats.getPoints());
        playerStatistics.setAverageRebounds(playerStats.getRebounds());
        playerStatistics.setAverageAssists(playerStats.getAssists());
        playerStatistics.setAverageSteals(playerStats.getSteals());
        playerStatistics.setAverageBlocks(playerStats.getBlocks());
        playerStatistics.setAverageTurnovers(playerStats.getTurnovers());
        playerStatistics.setAverageFouls(playerStats.getFouls());
        playerStatistics.setAverageMinutesPlayed(playerStats.getMinutesPlayed());
        return playerStatistics;
    }
}
