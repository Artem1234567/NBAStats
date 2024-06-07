package com.example.nbastatistics.repository;

import com.example.nbastatistics.model.Team;
import com.example.nbastatistics.model.output.TeamStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT NEW com.example.nbastatistics.model.output.TeamStatsDTO( " +
            "t.id, " +
            "AVG(ps.averagePoints), " +
            "AVG(ps.averageRebounds), " +
            "AVG(ps.averageAssists), " +
            "AVG(ps.averageSteals), " +
            "AVG(ps.averageBlocks), " +
            "AVG(ps.averageTurnovers), " +
            "AVG(ps.averageFouls), " +
            "AVG(ps.averageMinutesPlayed)) " +
            "FROM Team t " +
            "JOIN t.players p " +
            "JOIN p.playerStatistics ps " +
            "JOIN p.games g " +
            "JOIN g.season s " +
            "WHERE t.id = :teamId " +
            "AND s.startDate >= :startDate " +
            "AND s.endDate <= :endDate")
    TeamStatsDTO calculateAverageTeamStatsForSeasonAndTeam(@Param("teamId") Long teamId,
                                                                 @Param("startDate") LocalDate startDate,
                                                                 @Param("endDate") LocalDate endDate);
}
