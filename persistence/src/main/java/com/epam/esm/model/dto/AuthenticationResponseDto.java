package com.epam.esm.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponseDto {

    private String token;
    private String tokenHeader = "Bearer";

    public AuthenticationResponseDto(String token) {
        this.token = token;
    }
}
