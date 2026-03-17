package com.example.Server.dao.service;

import com.example.Server.dao.mapper.ProfileMapper;
import com.example.Server.dao.repository.ProfileEntityRepository;
import com.example.Server.domain.model.ProfileModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileDaoService {

    private final ProfileEntityRepository repository;
    private final ProfileMapper mapper;

    @Transactional
    public ProfileModel save(ProfileModel profile) {
        return mapper.toDomain(repository.save(mapper.toEntity(profile)));
    }

    public Optional<ProfileModel> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}