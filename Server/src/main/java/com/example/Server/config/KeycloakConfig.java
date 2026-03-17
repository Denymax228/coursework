package com.example.Server.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.server-url:http://localhost:8081}")
    private String serverUrl;

    @Value("${KEYCLOAK_ADMIN:admin}")
    private String adminUsername;

    @Value("${KEYCLOAK_ADMIN_PASSWORD:admin}")
    private String adminPassword;

    @Bean
    public Keycloak keycloakAdmin() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master") // Админ всегда через master
                .grantType(OAuth2Constants.PASSWORD)
                .username(adminUsername)
                .password(adminPassword)
                .clientId("admin-cli")
                .resteasyClient(ResteasyClientBuilder.newClient())
                .build();
    }
}