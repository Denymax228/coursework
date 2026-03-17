package com.example.Server.domain.model;

import com.example.Server.dao.entity.enums.GenderEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public final class ProfileModel {

    @EqualsAndHashCode.Include
    final UUID id;
    final UUID userId;
    String bio;
    GenderEnum genderEnum;
    LocalDate birthDate;
    String city;
    String country;
    LocalDateTime updatedAt;

    private ProfileModel(UUID id,
                         UUID userId,
                         String bio,
                         GenderEnum genderEnum,
                         LocalDate birthDate,
                         String city,
                         String country,
                         LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.bio = bio;
        this.genderEnum = genderEnum;
        this.birthDate = birthDate;
        this.city = city;
        this.country = country;
        this.updatedAt = updatedAt;
        validateInvariant();
    }

    public static ProfileModel create(UUID id,
                                      UUID userId,
                                      String bio,
                                      GenderEnum genderEnum,
                                      LocalDate birthDate,
                                      String city,
                                      String country) {
        LocalDateTime now = LocalDateTime.now();
        return new ProfileModel(id, userId, bio, genderEnum, birthDate, city, country, now);
    }

    private void validateInvariant() {
        if (userId == null) throw new IllegalArgumentException("userId required");
        if (birthDate == null) throw new IllegalArgumentException("birthDate required");
        int age = getAge();
        if (age < 13) throw new IllegalArgumentException("age must be >= 13");
        if (bio != null && bio.length() > 1000) throw new IllegalArgumentException("bio too long");
        if (city == null || city.isBlank()) throw new IllegalArgumentException("city required");
        if (country == null || country.isBlank()) throw new IllegalArgumentException("country required");
    }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public boolean isAdult() {
        return getAge() >= 18;
    }

    public void updateBio(String newBio) {
        if (newBio == null) newBio = "";
        if (newBio.length() > 1000) throw new IllegalArgumentException("bio too long");
        this.bio = newBio;
        touchUpdatedAt();
    }

    public void updateLocation(String city, String country) {
        if (city == null || city.isBlank()) throw new IllegalArgumentException("city required");
        if (country == null || country.isBlank()) throw new IllegalArgumentException("country required");
        this.city = city;
        this.country = country;
        touchUpdatedAt();
    }

    public void touchUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}