package com.gastronomia.costos_gastronomicos.DTO;

import lombok.Data;

@Data
public class TokenDTO {

    private String accesToken;
    private String tokenType = "Bearer";

    public TokenDTO(String accessToken){

        this.accesToken = accessToken;
    }
}
