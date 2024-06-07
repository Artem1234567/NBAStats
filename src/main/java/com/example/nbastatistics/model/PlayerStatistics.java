package com.example.nbastatistics.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "player_statistics")
public class PlayerStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

//    @Column(nullable = false)
//    private Long playerId;

    @Column(nullable = false)
//    private Game game;
    private Long gameId;

    @Column(nullable = false)
    private Integer averagePoints;
    @Column(nullable = false)
    private Integer averageRebounds;
    @Column(nullable = false)
    private Integer averageAssists;
    @Column(nullable = false)
    private Integer averageSteals;
    @Column(nullable = false)
    private Integer averageBlocks;
    @Column(nullable = false)
    private Integer averageTurnovers;
    @Column(nullable = false)
    private Integer averageFouls;
    @Column(nullable = false)
    private Float averageMinutesPlayed;
}