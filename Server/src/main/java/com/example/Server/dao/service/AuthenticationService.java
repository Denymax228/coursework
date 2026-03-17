package com.example.Server.dao.service;

import com.example.Server.api.dto.request.LoginRequest;
import com.example.Server.api.dto.request.RegisterRequest;
import com.example.Server.api.dto.response.AuthResponse;
import com.example.Server.dao.entity.UserEntity;
import com.example.Server.dao.mapper.UserMapper;
import com.example.Server.dao.repository.UserEntityRepository;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final Keycloak keycloak;
    private final UserEntityRepository userRepository;
    private final UserMapper userMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${KEYCLOAK_RESOURCE_URL}")
    private String tokenUrl;

    @Value("${KEYCLOAK_CLIENT_ID}")
    private String clientId;

    @Value("${keycloak.server-url:http://localhost:8081}")
    private String keycloakServerUrl;

    @Value("${KEYCLOAK_REALM_NAME}")
    private String REALM_NAME;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(request.username());
        kcUser.setEmail(request.email());
        kcUser.setFirstName(request.firstName());
        kcUser.setLastName(request.lastName());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());
        credential.setTemporary(false);
        kcUser.setCredentials(List.of(credential));

        UsersResource usersResource = keycloak.realm(REALM_NAME).users();

        try (Response response = usersResource.create(kcUser)) {
            if (response.getStatus() == 201) {
                String userIdString = CreatedResponseUtil.getCreatedId(response);
                UUID uuid = UUID.fromString(userIdString);

                log.info("User created in Keycloak with ID: {}", uuid);

                UserEntity localUser = userMapper.toEntity(request);
                localUser.setId(uuid);

                userRepository.save(localUser);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Keycloak error: " + response.getStatusInfo());
            }
        }
    }

    public AuthResponse login(LoginRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("username", request.username());
        map.add("password", request.password());
        map.add("grant_type", "password");

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, new HttpEntity<>(map, headers), Map.class);
            Map<String, Object> body = response.getBody();

            if (body == null) throw new RuntimeException("Empty response from Keycloak");

            UserEntity user = userRepository.findByUsername(request.username())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User found in Keycloak but not in DB"));

            return AuthResponse.builder()
                    .accessToken((String) body.get("access_token"))
                    .refreshToken((String) body.get("refresh_token"))
                    .expiresIn((Integer) body.get("expires_in"))
                    .tokenType((String) body.get("token_type"))
                    .user(userMapper.toDto(user))
                    .build();

        } catch (Exception e) {
            log.error("Login failed", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }


    public String getGoogleLoginUrl() {
        return String.format(
                "%s/realms/%s/protocol/openid-connect/auth?client_id=%s&response_type=code&scope=openid&redirect_uri=%s&kc_idp_hint=google",
                keycloakServerUrl,
                REALM_NAME,
                clientId,
                "http://localhost:8080/swagger-ui/index.html" // Или URL вашего фронтенда
        );
    }

    @Transactional
    public void syncUserFromJwt(Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());

        if (!userRepository.existsById(keycloakId)) {
            UserEntity newUser = UserEntity.builder()
                    .id(keycloakId)
                    .email(jwt.getClaimAsString("email"))
                    .username(jwt.getClaimAsString("preferred_username"))
                    .firstName(jwt.getClaimAsString("given_name"))
                    .lastName(jwt.getClaimAsString("family_name"))
                    .build();
            userRepository.save(newUser);
            log.info("Synced new user from Google SSO: {}", keycloakId);
        }
    }
}