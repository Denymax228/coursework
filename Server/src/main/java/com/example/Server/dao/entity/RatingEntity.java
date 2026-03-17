package com.example.Server.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ratings",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"from_user_id", "to_user_id", "ride_id"})},
        indexes = {@Index(columnList = "to_user_id")})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id", nullable = false)
    private UserEntity fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id", nullable = false)
    private UserEntity toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id")
    private RideEntity rideEntity;

    @Column(nullable = false)
    private Integer score;

    @Column(length = 2000)
    private String comment;

    private LocalDateTime createdAt;
}