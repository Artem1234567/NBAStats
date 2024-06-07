package com.example.nbastatistics.repository;

import com.example.nbastatistics.model.PlayerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatistics, Long> {

    @Query(value = """
            SELECT * FROM PlayerStatistics ps JOIN Game g 
            ON ps.playerId = ?1 AND ps.gameId = ?2 AND g.date = ?3
            """, nativeQuery = true)
    Optional<PlayerStatistics> findPlayerStatisticsByPlayerAndPlayerGamesDate(Long playerId,
                                                                              Long gameId,
                                                                              LocalDate gameDate);
}
