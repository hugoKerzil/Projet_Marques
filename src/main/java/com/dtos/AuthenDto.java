package com.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenDto {

    private Long id;

    @NotBlank(message = "Le pseudo est obligatoire")
    private String pseudo;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    private String role;
}
