package com.example.Server.dao.entity;

import com.example.Server.dao.entity.enums.VerificationStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "verifications",
        indexes = {@Index(columnList = "user_id"), @Index(columnList = "status")})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private VerificationStatusEnum status;

    private LocalDateTime submittedAt;

    private LocalDateTime verifiedAt;

    private String notes;
}