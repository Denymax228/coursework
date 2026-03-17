package com.example.Server.dao.repository;

import com.example.Server.dao.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProfileEntityRepository extends JpaRepository<ProfileEntity, UUID> {
    Optional<ProfileEntity> findByUser_Id(UUID userId);
}