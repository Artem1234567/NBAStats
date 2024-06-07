package com.example.nbastatistics.repository;

import com.example.nbastatistics.model.Player;
import com.example.nbastatistics.model.output.PlayerStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT NEW com.example.nbastatistics.model.output.PlayerStatsDTO( " +
            "p.id, " +
            "AVG(ps.averagePoints), " +
            "AVG(ps.averageRebounds), " +
            "AVG(ps.averageAssists), " +
            "AVG(ps.averageSteals), " +
            "AVG(ps.averageBlocks), " +
            "AVG(ps.averageTurnovers), " +
            "AVG(ps.averageFouls), " +
            "AVG(ps.averageMinutesPlayed)) " +
            "FROM Player p " +
            "JOIN p.playerStatistics ps " +
            "JOIN p.games g " +
            "JOIN g.season s " +
            "WHERE p.id = :playerId " +
            "AND s.startDate >= :startDate " +
            "AND s.endDate <= :endDate")
    PlayerStatsDTO calculateAveragePlayerStatsForSeason(@Param("playerId") Long playerId,
                                                        @Param("startDate") LocalDate startDate,
                                                        @Param("endDate") LocalDate endDate);
}
