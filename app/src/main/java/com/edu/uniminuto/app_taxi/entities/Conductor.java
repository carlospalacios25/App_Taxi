package com.edu.uniminuto.app_taxi.entities;

public class Conductor {
    private long cedula_con;
    private String nombre_con;
    private String apellido_con;
    private long telefono_con;
    private String direccion_con;

    public Conductor() {
    }

    public Conductor(long cedula_con, String nombre_con, String apellido_con, long telefono_con, String direccion_con) {
        this.cedula_con = cedula_con;
        this.nombre_con = nombre_con;
        this.apellido_con = apellido_con;
        this.telefono_con = telefono_con;
        this.direccion_con = direccion_con;
    }

    public long getCedula_con() {
        return cedula_con;
    }

    public void setCedula_con(long cedula_con) {
        this.cedula_con = cedula_con;
    }

    public String getNombre_con() {
        return nombre_con;
    }

    public void setNombre_con(String nombre_con) {
        this.nombre_con = nombre_con;
    }

    public String getApellido_con() {
        return apellido_con;
    }

    public void setApellido_con(String apellido_con) {
        this.apellido_con = apellido_con;
    }

    public long getTelefono_con() {
        return telefono_con;
    }

    public void setTelefono_con(long telefono_con) {
        this.telefono_con = telefono_con;
    }

    public String getDireccion_con() {
        return direccion_con;
    }

    public void setDireccion_con(String direccion_con) {
        this.direccion_con = direccion_con;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("cedula :").append(cedula_con).append("\n");
        sb.append("nombre_con: ").append(nombre_con).append("\n");
        sb.append("telefono: ").append(telefono_con);
        return sb.toString();
    }
}
