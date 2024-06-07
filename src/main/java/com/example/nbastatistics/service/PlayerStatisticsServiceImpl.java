package com.example.nbastatistics.service;

import com.example.nbastatistics.exception.FetchPlayerStatsException;
import com.example.nbastatistics.exception.SaveStatsException;
import com.example.nbastatistics.model.Player;
import com.example.nbastatistics.model.PlayerStatistics;
import com.example.nbastatistics.model.input.PlayerStatsRequest;
import com.example.nbastatistics.model.mapper.Mapper;
import com.example.nbastatistics.model.output.PlayerStatsDTO;
import com.example.nbastatistics.repository.PlayerRepository;
import com.example.nbastatistics.repository.PlayerStatisticsRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@AllArgsConstructor
public class PlayerStatisticsServiceImpl implements PlayerStatisticsService {

    private  PlayerRepository playerRepository;
    private  PlayerStatisticsRepository playerStatisticsRepository;
    private  Mapper<PlayerStatistics, PlayerStatsRequest> playerStatisticsMapper;

    @Value("${executor.thread.pool.size}")
    private int threadPoolSize;

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        this.executorService = Executors.newFixedThreadPool(
                threadPoolSize > 1 ? threadPoolSize : Runtime.getRuntime().availableProcessors());
    }

    /**
     * Save Player statistics
     * @param playerStatsRequest
     */
    @Transactional
    @CacheEvict(value = "teamStatsCache", allEntries = true)
    @CachePut(value = "playerStatsCache")
    public CompletableFuture<Void> savePlayerStatistics(Long playerId,
                                                        PlayerStatsRequest playerStatsRequest) {
        log.info("PlayerStatisticsService::savePlayerStatistics. PlayerId={}", playerId);
        return CompletableFuture.runAsync(() -> {
            try {
                Player player = playerRepository.findById(playerId)
                        .orElseThrow(() -> new RuntimeException("Player not found"));

                Optional<PlayerStatistics> existingPlayerStatsOpt = playerStatisticsRepository
                        .findPlayerStatisticsByPlayerAndPlayerGamesDate(playerId,
                                playerStatsRequest.getGameId(), playerStatsRequest.getGameDate());
                if (existingPlayerStatsOpt.isEmpty()) {
                    PlayerStatistics playerStatistics = playerStatisticsMapper
                            .map(new PlayerStatistics(), playerStatsRequest);
                    playerStatistics.setPlayer(player);

                    playerStatisticsRepository.save(playerStatistics);
                }
            } catch (Exception e) {
                log.error("Error saving player stats: {}", e.getMessage());
                throw new SaveStatsException("Error saving player stats");
            }
        }, executorService);
    }

    /**
     * Calculate aggregate statistics - Season Average per player
     * @param playerId
     * @param startDate start date of season
     * @param endDate end date of season
     * @return
     */
    @Async
    @Cacheable(value = "playerStatsCache", key = "#playerId")
    @Transactional(readOnly = true)
    public CompletableFuture<PlayerStatsDTO> getPlayerStatsForSeason(Long playerId,
                                                                     LocalDate startDate, LocalDate endDate) {
        log.info("PlayerStatisticsService::getPlayerStatsForSeason by player.Id={}", playerId);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return fetchPlayerStatistics(playerId, startDate, endDate);
            } catch (Exception e) {
                log.error("Error fetching player season stats: {}", e.getMessage());
                throw new FetchPlayerStatsException("Error fetching player season stats");
            }
        }, executorService);
    }

    @Transactional(readOnly = true)
    public PlayerStatsDTO getPlayerStatsForSeason2(Long playerId,
                                                                     LocalDate startDate, LocalDate endDate) {
        return fetchPlayerStatistics(playerId, startDate, endDate);
    }

    private PlayerStatsDTO fetchPlayerStatistics(Long playerId,
                                                 LocalDate startDate, LocalDate endDate) {
        return playerRepository.calculateAveragePlayerStatsForSeason(playerId, startDate, endDate);
    }
}
