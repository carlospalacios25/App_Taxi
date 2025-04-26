package com.edu.uniminuto.app_taxi;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.uniminuto.app_taxi.dataaccess.DataBaseTaxi;
import com.edu.uniminuto.app_taxi.entities.Taxi;
import com.edu.uniminuto.app_taxi.repository.TaxiRepository;

public class ModuloTaxi extends AppCompatActivity {
    private EditText etmarcaTaxi;
    private EditText etplacaTaxi;
    private EditText etcedulaCon;
    private String marca_taxi;
    private String placa_taxi;
    private long cedula_con;
    private Button btnCrearTaxi;
    private Button btnBuscarConductor;
    private Context context;
    private DataBaseTaxi dataBaseTaxi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.taxi);

        this.dataBaseTaxi = new DataBaseTaxi(this);
        this.referencia();
        this.btnCrearTaxi.setOnClickListener(this::crearTaxi);
        this.btnBuscarConductor.setOnClickListener(v -> {
            Intent intent = new Intent(ModuloTaxi.this, ModuloConductor.class);
            startActivity(intent);
        });
    }

    private void crearTaxi(View view) {
        if (etmarcaTaxi.getText().toString().trim().isEmpty() ||
                etplacaTaxi.getText().toString().trim().isEmpty() ||
                etcedulaCon.getText().toString().trim().isEmpty()) {

            new AlertDialog.Builder(this)
                    .setTitle("Campos vacíos")
                    .setMessage("Por favor, completa todos los campos.")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }

        try {
            capData();
            TaxiRepository taxiRepository = new TaxiRepository(this, view);
            if (!taxiRepository.conductorExists(cedula_con)) {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("La cédula del conductor no está registrada.")
                        .setPositiveButton("Aceptar", null)
                        .show();
                return;
            }

            Taxi taxi = new Taxi(marca_taxi, placa_taxi, cedula_con);
            taxiRepository.insertTaxi(taxi);
            limpiarCampos();
        } catch (NumberFormatException e) {
            new AlertDialog.Builder(this)
                    .setTitle("Error de formato")
                    .setMessage("La cédula debe ser un número válido.")
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }

    private void capData() {
        this.marca_taxi = etmarcaTaxi.getText().toString().trim();
        this.placa_taxi = etplacaTaxi.getText().toString().trim();
        this.cedula_con = Long.parseLong(etcedulaCon.getText().toString().trim()); // Cambiado a Long
    }

    private void limpiarCampos() {
        this.etmarcaTaxi.setText("");
        this.etplacaTaxi.setText("");
        this.etcedulaCon.setText("");
    }

    private void referencia() {
        this.etmarcaTaxi = findViewById(R.id.etmarcaTaxi);
        this.btnCrearTaxi = findViewById(R.id.btnCrearTaxi);
        this.etplacaTaxi = findViewById(R.id.etplacaTaxi);
        this.etcedulaCon = findViewById(R.id.etcedulaCon);
        this.btnBuscarConductor = findViewById(R.id.btnBuscarConductor);
        this.context = this;
    }


}