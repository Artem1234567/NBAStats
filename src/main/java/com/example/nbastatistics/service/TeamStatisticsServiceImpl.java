package com.example.nbastatistics.service;

import com.example.nbastatistics.exception.FetchTeamStatsException;
import com.example.nbastatistics.model.output.TeamStatsDTO;
import com.example.nbastatistics.repository.TeamRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@AllArgsConstructor
public class TeamStatisticsServiceImpl implements TeamStatisticsService {

    private final TeamRepository teamRepository;
    private ExecutorService executorService;

    @Value("${executor.thread.pool.size}")
    private int threadPoolSize;

    @PostConstruct
    public void init() {
        this.executorService = Executors.newFixedThreadPool(
                threadPoolSize > 1 ? threadPoolSize : Runtime.getRuntime().availableProcessors());
    }

    /**
     * Calculate aggregate statistics - Season Average per team
     * @param teamId
     * @param startDate start date of season
     * @param endDate end date of season
     * @return
     */
    @Async
    @Cacheable(value = "teamStatsCache", key = "#teamId")
    @Transactional(readOnly = true)
    public CompletableFuture<TeamStatsDTO> getTeamStatsForSeason(Long teamId,
                                                                 LocalDate startDate, LocalDate endDate) {
        log.info("TeamStatisticsService::getTeamStatsForSeason by team.Id={}", teamId);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return fetchTeamStatistics(teamId, startDate, endDate);
            } catch (Exception e) {
                log.error("Error fetching team season stats: {}", e.getMessage());
                throw new FetchTeamStatsException("Error fetching team season stats");
            }
        }, executorService);
    }

    private TeamStatsDTO fetchTeamStatistics(Long teamId,
                                             LocalDate startDate, LocalDate endDate) {
        return teamRepository.calculateAverageTeamStatsForSeasonAndTeam(teamId, startDate, endDate);
    }
}
