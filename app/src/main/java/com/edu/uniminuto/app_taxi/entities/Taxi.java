package com.edu.uniminuto.app_taxi.entities;

public class Taxi {
    private String marca_taxi;
    private String placa_taxi;
    public Taxi() {
    }

    public Taxi(String marca_taxi, String placa_taxi) {
        this.marca_taxi = marca_taxi;
        this.placa_taxi = placa_taxi;
    }

    public String getMarca_taxi() {
        return marca_taxi;
    }

    public void setMarca_taxi(String marca_taxi) {
        this.marca_taxi = marca_taxi;
    }

    public String getPlaca_taxi() {
        return placa_taxi;
    }

    public void setPlaca_taxi(String placa_taxi) {
        this.placa_taxi = placa_taxi;
    }

}