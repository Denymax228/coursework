package com.example.Server.api.dto.response;

import com.example.Server.dao.entity.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private UUID id;
    private UUID userId;
    private String bio;
    private GenderEnum genderEnum;
    private LocalDate birthDate;
    private String city;
    private String country;
    private LocalDateTime updatedAt;
}