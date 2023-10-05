package com.example.auth.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record ProductRequestDTO(@NotBlank String name, @NotNull Integer price) {

}
