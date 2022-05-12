package com.epam.esm.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
