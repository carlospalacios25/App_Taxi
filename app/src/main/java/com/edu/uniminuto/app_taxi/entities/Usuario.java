package com.edu.uniminuto.app_taxi.entities;

public class Usuario {
    private String nombreUsuario;
    private String claveUsuario;

    public Usuario() {
    }

    public Usuario(String usuario, String clave) {
        this.nombreUsuario = usuario;
        this.claveUsuario = clave;
    }
    public Usuario(String clave) {
        this.claveUsuario = clave;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }
}
