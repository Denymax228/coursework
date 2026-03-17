package com.example.Server.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class UserModel {
    UUID id;
    String username;
    String email;
    String firstName;
    String lastName;
    String patronymic;
}