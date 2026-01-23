package com.gastronomia.costos_gastronomicos.DTO;



import lombok.Data;

@Data
public class UserRegisterDTO {

    private String userName;

    private String password;

    private Long clienteId;

    private String rol;

}
