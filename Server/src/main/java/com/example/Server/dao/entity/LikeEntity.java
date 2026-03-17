package com.example.Server.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "likes",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"liker_id", "liked_id"})},
        indexes = {@Index(columnList = "liker_id"), @Index(columnList = "liked_id")})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liker_id", nullable = false)
    private UserEntity liker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liked_id", nullable = false)
    private UserEntity liked;

    private LocalDateTime createdAt;
}