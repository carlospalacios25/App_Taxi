package com.edu.uniminuto.app_taxi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.uniminuto.app_taxi.entities.Conductor;
import com.edu.uniminuto.app_taxi.repository.ConductorRepository;

import java.util.ArrayList;

public class ModuloConductor extends AppCompatActivity {
    private Context context;

    private EditText etCedulaCon;
    private EditText etNombreCon;
    private EditText etApellidoCon;
    private EditText etTelefonoCon;
    private EditText etDireccionCon;
    private Button btnCrearCon;
    SQLiteDatabase sqLiteDatabase;
    private long cedulaCon;
    private String nombreCon;
    private String apellidoCon;
    private long telefonoCon;
    private String direccionCon;
    private Button btnBuscar;
    private ListView lvListar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.conductor);

        this.referencia();
        this.btnCrearCon.setOnClickListener(this::crearConductor);
        this.btnBuscar.setOnClickListener(this::buscarConductor);

    }

    private void crearConductor(View view) {
        if (etCedulaCon.getText().toString().trim().isEmpty() ||
                etNombreCon.getText().toString().trim().isEmpty() ||
                etApellidoCon.getText().toString().trim().isEmpty() ||
                etTelefonoCon.getText().toString().trim().isEmpty() ||
                etDireccionCon.getText().toString().trim().isEmpty()) {

            new AlertDialog.Builder(this)
                    .setTitle("Campos vacíos")
                    .setMessage("Por favor, completa todos los campos.")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }

        capData();
        Conductor conductor = new Conductor(cedulaCon, nombreCon, apellidoCon, telefonoCon, direccionCon);
        ConductorRepository conductorRepository = new ConductorRepository(this, view);
        conductorRepository.insertConductor(conductor);
        listarConductores(view);
        limpiarCampos();
    }

    private void capData(){
        this.cedulaCon = Long.parseLong(this.etCedulaCon.getText().toString());
        this.nombreCon = etNombreCon.getText().toString();
        this.apellidoCon = etApellidoCon.getText().toString();
        this.telefonoCon = Long.parseLong(this.etTelefonoCon.getText().toString());
        this.direccionCon = etDireccionCon.getText().toString();
    }
    private void limpiarCampos() {
        this.etCedulaCon.setText("");
        this.etNombreCon.setText("");
        this.etApellidoCon.setText("");
        this.etTelefonoCon.setText("");
        this.etDireccionCon.setText("");

        this.etCedulaCon.requestFocus();
    }
    public void buscarConductor(View view) {
        String cedulaStr = etCedulaCon.getText().toString().trim();

        if (cedulaStr.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Campo vacío")
                    .setMessage("Por favor ingresa la cédula para buscar.")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }

        try {
            cedulaCon = Integer.parseInt(cedulaStr);
        } catch (NumberFormatException e) {
            new AlertDialog.Builder(this)
                    .setTitle("Cédula inválida")
                    .setMessage("La cédula debe ser un número válido.")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }

        ConductorRepository conductorRepository = new ConductorRepository(this, view);
        Conductor conductor = conductorRepository.getConductorByCedula(cedulaCon);

        if (conductor != null) {
            etNombreCon.setText(conductor.getNombre_con());
            etApellidoCon.setText(conductor.getApellido_con());
            etTelefonoCon.setText(String.valueOf(conductor.getTelefono_con()));
            etDireccionCon.setText(conductor.getDireccion_con());
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Usuario no encontrado")
                    .setMessage("No se encontró un conductor con esa cédula.")
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }

    private void listarConductores(View view) {
        ConductorRepository conductorRepository = new ConductorRepository(this, view);
        ArrayList<Conductor> list= conductorRepository.getConductorList();
        ArrayAdapter<Conductor> arrayAdapter = new ArrayAdapter<>
                (context, android.R.layout.simple_list_item_1, list);

        this.lvListar.setAdapter(arrayAdapter);

    }
    private void referencia(){
        this.etCedulaCon = findViewById(R.id.etProductocom);
        this.etNombreCon = findViewById(R.id.etprecioUnicom);
        this.etApellidoCon = findViewById(R.id.etCantidadcom);
        this.etTelefonoCon = findViewById(R.id.etfechaCom);
        this.etDireccionCon = findViewById(R.id.etdescripcionCom);
        this.btnCrearCon = findViewById(R.id.btnCrearTaxi);
        this.btnBuscar = findViewById(R.id.btnBuscarConductor);
        this.lvListar = findViewById(R.id.lvListar);
        this.context = this;
        this.direccionCon = etDireccionCon.getText().toString();

    }
}
