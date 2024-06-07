package com.example.nbastatistics.config;

import com.example.nbastatistics.model.output.PlayerStatsDTO;
import com.example.nbastatistics.model.output.TeamStatsDTO;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
public class CacheConfig {

    public CacheManager ehCacheManager() {
        CacheConfiguration<Long, PlayerStatsDTO> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Long.class,
                        PlayerStatsDTO.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(10, MemoryUnit.MB).build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(1)))
                .build();

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        javax.cache.configuration.Configuration<Long, PlayerStatsDTO> configuration =
                Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig);
        cacheManager.createCache("playerStatsCache", configuration);

        CacheConfiguration<Long, TeamStatsDTO> cacheConfigTeam = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Long.class,
                        TeamStatsDTO.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(10, MemoryUnit.MB).build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(1)))
                .build();
        javax.cache.configuration.Configuration<Long, TeamStatsDTO> configurationTeam =
                Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfigTeam);
        cacheManager.createCache("teamStatsCache", configurationTeam);

        return cacheManager;
    }
}
