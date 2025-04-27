package com.edu.uniminuto.app_taxi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.uniminuto.app_taxi.entities.Utilidad;
import com.edu.uniminuto.app_taxi.repository.UtilidadRepository;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModuloUtilidad extends AppCompatActivity {
    private EditText etgastosCom;
    private EditText etigresoscom;
    private EditText etutilidadTaxi;  // Agregado de nuevo
    private EditText etfechaCom;
    private EditText etdescripcionCom;
    private EditText etPlacaTaxi;
    private EditText etcedulaCon;
    private Button btnRegistrarGi;
    private double gastos_com;
    private double igresos_com;
    private Date fecha_com;
    private String descripcion_com;
    private String placa_taxi;
    private long cedula_con;
    private View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.compra);

        this.reference();
        this.setupListeners();  // Configurar los listeners
        this.btnRegistrarGi.setOnClickListener(this::crearUtilidad);
    }

    private void crearUtilidad(View view) {
        if (etgastosCom.getText().toString().trim().isEmpty() ||
                etigresoscom.getText().toString().trim().isEmpty() ||
                etfechaCom.getText().toString().trim().isEmpty() ||
                etdescripcionCom.getText().toString().trim().isEmpty() ||
                etPlacaTaxi.getText().toString().trim().isEmpty() ||
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
            UtilidadRepository utilidadRepository = new UtilidadRepository(this, view);
            if (!utilidadRepository.placaTaxiExists(placa_taxi)) {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("La placa del taxi no está registrada.")
                        .setPositiveButton("Aceptar", null)
                        .show();
                return;
            }

            if (!utilidadRepository.conductorExists(cedula_con)) {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("La cédula del conductor no está registrada.")
                        .setPositiveButton("Aceptar", null)
                        .show();
                return;
            }
            Utilidad utilidad = new Utilidad(gastos_com, igresos_com, fecha_com, descripcion_com, placa_taxi, cedula_con);
            utilidadRepository.insertUtilidad(utilidad);
            limpiarCampos();
        } catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Error al registrar la utilidad: " + e.getMessage())
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }

    private void setupListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                calculateUtilidad();
            }
        };

        etgastosCom.addTextChangedListener(textWatcher);
        etigresoscom.addTextChangedListener(textWatcher);
    }

    private void calculateUtilidad() {
        try {
            double gastos = etgastosCom.getText().toString().trim().isEmpty() ? 0 : Double.parseDouble(etgastosCom.getText().toString().trim());
            double ingresos = etigresoscom.getText().toString().trim().isEmpty() ? 0 : Double.parseDouble(etigresoscom.getText().toString().trim());
            double utilidad = ingresos - gastos;
            etutilidadTaxi.setText(String.valueOf(utilidad));
        } catch (NumberFormatException e) {
            etutilidadTaxi.setText("0");
        }
    }

    private void limpiarCampos() {
        this.etgastosCom.setText("");
        this.etigresoscom.setText("");
        this.etutilidadTaxi.setText("");
        this.etfechaCom.setText("");
        this.etdescripcionCom.setText("");
        this.etPlacaTaxi.setText("");
        this.etcedulaCon.setText("");
    }

    private void capData() throws ParseException {
        this.gastos_com = Double.parseDouble(this.etgastosCom.getText().toString().trim());
        this.igresos_com = Double.parseDouble(this.etigresoscom.getText().toString().trim());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.fecha_com = sdf.parse(this.etfechaCom.getText().toString().trim());
        this.descripcion_com = etdescripcionCom.getText().toString().trim();
        this.placa_taxi = etPlacaTaxi.getText().toString().trim();
        this.cedula_con = Long.parseLong(this.etcedulaCon.getText().toString().trim());
    }

    private void reference() {
        this.etgastosCom = findViewById(R.id.etgastosCom);
        this.etigresoscom = findViewById(R.id.etigresoscom);
        this.etutilidadTaxi = findViewById(R.id.etutilidadTaxi);
        this.etfechaCom = findViewById(R.id.etfechaCom);
        this.etdescripcionCom = findViewById(R.id.etdescripcionCom);
        this.etPlacaTaxi = findViewById(R.id.etPlacaTaxi);
        this.etcedulaCon = findViewById(R.id.etcedulaCon);
        this.btnRegistrarGi = findViewById(R.id.btnRegistrarGi);
    }
}