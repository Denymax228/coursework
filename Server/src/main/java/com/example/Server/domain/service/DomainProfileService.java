package com.example.Server.domain.service;

import com.example.Server.dao.service.ProfileDaoService;
import com.example.Server.domain.model.ProfileModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DomainProfileService {

    private final ProfileDaoService daoService;

    public Optional<ProfileModel> get(UUID id) {
        return daoService.findById(id);
    }

    public ProfileModel create(ProfileModel profile) {
        return daoService.save(profile);
    }

    public ProfileModel updateBio(UUID id, String bio) {
        ProfileModel p = daoService.findById(id).orElseThrow(() -> new IllegalArgumentException("not found"));
        p.updateBio(bio);
        return daoService.save(p);
    }

    public ProfileModel updateLocation(UUID id, String city, String country) {
        ProfileModel p = daoService.findById(id).orElseThrow(() -> new IllegalArgumentException("not found"));
        p.updateLocation(city, country);
        return daoService.save(p);
    }

    public void delete(UUID id) {
        daoService.deleteById(id);
    }
}