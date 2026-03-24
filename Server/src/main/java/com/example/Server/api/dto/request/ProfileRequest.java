package com.example.Server.api.dto.request;

import com.example.Server.dao.entity.enums.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {
    @NotNull
    private UUID userId;

    @Size(max = 1000)
    private String bio;

    private GenderEnum genderEnum;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotBlank
    private String city;

    @NotBlank
    private String country;
}