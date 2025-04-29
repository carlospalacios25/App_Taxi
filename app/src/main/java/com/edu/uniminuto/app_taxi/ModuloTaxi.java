package com.edu.uniminuto.app_taxi;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.uniminuto.app_taxi.entities.Taxi;
import com.edu.uniminuto.app_taxi.repository.TaxiRepository;

public class ModuloTaxi extends AppCompatActivity {
    private EditText etmarcaTaxi;
    private EditText etplacaTaxi;
    private String marca_taxi;
    private String placa_taxi;
    private Button btnCrearTaxi;
    private Button btnBuscarTaxi;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.taxi);

        this.referencia();
        this.btnCrearTaxi.setOnClickListener(this::crearTaxi);
        this.btnBuscarTaxi.setOnClickListener(this::buscarTaxi);

    }

    private void buscarTaxi(View view) {
        String placa_taxi = etplacaTaxi.getText().toString().trim();

        if (placa_taxi.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Campo vacío")
                    .setMessage("Por favor ingresa la placa.")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }

        TaxiRepository taxiRepository = new TaxiRepository(this, view);
        Taxi taxi = taxiRepository.getTaxiByPLaca(placa_taxi);

        if (taxi != null) {
            etmarcaTaxi.setText(taxi.getMarca_taxi());
            etplacaTaxi.setText(taxi.getPlaca_taxi());
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Taxi no encontrado")
                    .setMessage("No se encontró un taxi con la placa ingresada.")
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }

    private void crearTaxi(View view) {
        if (etmarcaTaxi.getText().toString().trim().isEmpty() ||
                etplacaTaxi.getText().toString().trim().isEmpty()) {

            new AlertDialog.Builder(this)
                    .setTitle("Campos vacíos")
                    .setMessage("Por favor, completa todos los campos.")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }

        try {
            capData();
            Taxi taxi = new Taxi(marca_taxi, placa_taxi);
            TaxiRepository taxiRepository = new TaxiRepository(this, view);
            taxiRepository.insertTaxi(taxi);
            limpiarCampos();
        } catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Error al crear el taxi: " + e.getMessage())
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }

    private void capData() {
        this.marca_taxi = etmarcaTaxi.getText().toString().trim();
        this.placa_taxi = etplacaTaxi.getText().toString().trim();
    }

    private void limpiarCampos() {
        this.etmarcaTaxi.setText("");
        this.etplacaTaxi.setText("");
    }

    private void referencia() {
        this.etmarcaTaxi = findViewById(R.id.etUsuario);
        this.btnCrearTaxi = findViewById(R.id.btnRegistroUs);
        this.etplacaTaxi = findViewById(R.id.etClave);
        this.btnBuscarTaxi = findViewById(R.id.btnBuscarU);
        this.context = this;
    }
}