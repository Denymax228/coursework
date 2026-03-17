package com.example.Server.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "matches",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_a_id", "user_b_id"})},
        indexes = {@Index(columnList = "user_a_id"), @Index(columnList = "user_b_id")})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_a_id", nullable = false)
    private UserEntity userA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_b_id", nullable = false)
    private UserEntity userB;

    private LocalDateTime matchedAt;

    private boolean revealedEmail;
}