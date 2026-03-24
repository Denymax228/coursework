package com.example.Server.api.controller;

import com.example.Server.api.dto.*;
import com.example.Server.api.dto.request.BioUpdateRequest;
import com.example.Server.api.dto.request.LocationUpdateRequest;
import com.example.Server.api.dto.request.ProfileRequest;
import com.example.Server.api.dto.response.ProfileResponse;
import com.example.Server.dao.mapper.ProfileMapper;
import com.example.Server.domain.model.ProfileModel;
import com.example.Server.domain.service.DomainProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final DomainProfileService service;
    private final ProfileMapper mapper;


    @PostMapping
    public ResponseEntity<ProfileResponse> create(@Valid @RequestBody ProfileRequest dto) {
        ProfileModel model = mapper.toDomain(dto);
        try {
            ProfileModel created = service.create(model);
            ProfileResponse resp = mapper.toDto(created);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(location).body(resp);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> get(@PathVariable UUID id) {
        return service.get(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}/bio")
    public ResponseEntity<ProfileResponse> updateBio(@PathVariable UUID id,
                                                        @Valid @RequestBody BioUpdateRequest dto) {
        try {
            ProfileModel updated = service.updateBio(id, dto.getBio());
            return ResponseEntity.ok(mapper.toDto(updated));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PatchMapping("/{id}/location")
    public ResponseEntity<ProfileResponse> updateLocation(@PathVariable UUID id,
                                                             @Valid @RequestBody LocationUpdateRequest dto) {
        try {
            ProfileModel updated = service.updateLocation(id, dto.getCity(), dto.getCountry());
            return ResponseEntity.ok(mapper.toDto(updated));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}