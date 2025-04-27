package com.edu.uniminuto.app_taxi.entities;

import java.util.Date;

public class Utilidad{
     private int id_compra;
     private double gastos_com;
     private double igresos_com;
     private double utilidad_com;
     private Date fecha_com;
     private String descripcion_com;
     private String placa_taxi;
     private long cedula_con;

    public Utilidad() {
    }
    public Utilidad(double gastos_com, double igresos_com, double utilidad_com, Date fecha_com, String descripcion_com, String placa_taxi, long cedula_con) {
        this.gastos_com = gastos_com;
        this.igresos_com = igresos_com;
        this.utilidad_com = utilidad_com;
        this.fecha_com = fecha_com;
        this.descripcion_com = descripcion_com;
        this.placa_taxi = placa_taxi;
        this.cedula_con = cedula_con;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public double getGastos_com() {
        return gastos_com;
    }

    public void setGastos_com(double gastos_com) {
        this.gastos_com = gastos_com;
    }

    public double getIgresos_com() {
        return igresos_com;
    }

    public void setIgresos_com(double igresos_com) {
        this.igresos_com = igresos_com;
    }

    public double getUtilidad_com() {
        return utilidad_com;
    }

    public void setUtilidad_com(double utilidad_com) {
        this.utilidad_com = utilidad_com;
    }

    public Date getFecha_com() {
        return fecha_com;
    }

    public void setFecha_com(Date fecha_com) {
        this.fecha_com = fecha_com;
    }

    public String getDescripcion_com() {
        return descripcion_com;
    }

    public void setDescripcion_com(String descripcion_com) {
        this.descripcion_com = descripcion_com;
    }

    public String getPlaca_taxi() {
        return placa_taxi;
    }

    public void setPlaca_taxi(String placa_taxi) {
        this.placa_taxi = placa_taxi;
    }

    public long getCedula_con() {
        return cedula_con;
    }

    public void setCedula_con(long cedula_con) {
        this.cedula_con = cedula_con;
    }
}
