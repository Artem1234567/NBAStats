package com.example.nbastatistics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenStatPl")
        @SequenceGenerator(name = "seqGenStatPl", sequenceName = "seqPl", initialValue = 1)
        private Long id;

        @NotEmpty
        @Column(nullable = false)
        private String name;

        @ManyToMany(mappedBy = "players")
        private Set<Game> games = new HashSet<>();

        @ManyToMany(mappedBy = "players")
        private Set<Team> teams = new HashSet<>();

        @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<PlayerStatistics> playerStatistics;
}
