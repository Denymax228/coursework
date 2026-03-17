package com.example.Server.dao.entity;

import com.example.Server.dao.entity.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEntity {
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(length = 1000)
    private String bio;

    @Enumerated(EnumType.STRING)
    private GenderEnum genderEnum;

    private LocalDate birthDate;

    private String city;
    private String country;

    private LocalDateTime updatedAt;
}