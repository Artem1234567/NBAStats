package com.example.nbastatistics.model.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerStatsRequest {

    @NotNull
    private Long playerId;

    @NotNull
    private Long gameId;

    @NotNull
    private LocalDate gameDate;

    @NotNull
    @Min(0)
    private Integer points;

    @NotNull
    @Min(0)
    private Integer rebounds;

    @NotNull
    @Min(0)
    private Integer assists;

    @NotNull
    @Min(0)
    private Integer steals;

    @NotNull
    @Min(0)
    private Integer blocks;

    @NotNull
    @Min(0)
    @Max(6)
    private Integer fouls;

    @NotNull
    @Min(0)
    private Integer turnovers;

    @NotNull
    @Min(0)
    @Max(48)
    private Float minutesPlayed;
}
