package com.ms.Ecomm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }
}
