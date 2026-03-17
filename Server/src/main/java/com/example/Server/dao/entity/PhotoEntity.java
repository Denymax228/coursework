package com.example.Server.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "photos",
        indexes = {@Index(columnList = "user_id")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String url;

    private String caption;

    private boolean isProfilePhoto;

    private LocalDateTime uploadedAt;
}