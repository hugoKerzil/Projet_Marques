package com.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenDto {

    private Long id;

    @NotBlank(message = "The username is required.")
    private String pseudo;

    @NotBlank(message = "A password is required.")
    private String password;

    private String role;


}
