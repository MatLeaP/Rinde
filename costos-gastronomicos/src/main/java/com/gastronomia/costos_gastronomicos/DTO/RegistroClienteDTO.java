package com.gastronomia.costos_gastronomicos.DTO;

public class RegistroClienteDTO {
    // Datos de la Empresa (Cliente)
    private String nombreEmpresa;
    private String cuit;
    private String direccion;
    
    // Datos del Usuario Administrador
    private String adminUsername;
    private String adminEmail;
    private String adminPassword;

    // Constructores
    public RegistroClienteDTO() {}

    // Getters y Setters
    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }

    public String getCuit() { return cuit; }
    public void setNit(String cuit) { this.cuit = cuit; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getAdminUsername() { return adminUsername; }
    public void setAdminUsername(String adminUsername) { this.adminUsername = adminUsername; }

    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }
}