package com.example.auth.domain.dto;

import com.example.auth.domain.entity.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
