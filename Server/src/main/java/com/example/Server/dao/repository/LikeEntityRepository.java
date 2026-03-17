package com.example.Server.dao.repository;

import com.example.Server.dao.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeEntityRepository extends JpaRepository<LikeEntity, UUID> {
}
