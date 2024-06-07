package com.example.nbastatistics.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatsDTO {
    private Long playerId;
    private Double averagePoints;
    private Double averageRebounds;
    private Double averageAssists;
    private Double averageSteals;
    private Double averageBlocks;
    private Double averageTurnovers;
    private Double averageFouls;
    private Double averageMinutesPlayed;
}
// record ?