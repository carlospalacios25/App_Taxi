package com.edu.uniminuto.app_taxi.moduls;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.uniminuto.app_taxi.R;
import com.edu.uniminuto.app_taxi.entities.Conductor;
import com.edu.uniminuto.app_taxi.repository.ConductorRepository;

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
    private Button btnModificarCon;
    private Button btnEliminarCon;
    private ListView lvListar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.conductor);

        this.referencia();
        this.btnCrearCon.setOnClickListener(this::crearConductor);
        this.btnBuscar.setOnClickListener(this::buscarConductor);
        this.btnModificarCon.setOnClickListener(this::modificarConductor);
        this.btnEliminarCon.setOnClickListener(this::eliminarConductor);
    }

    private void eliminarConductor(View view) {
        capData();
        ConductorRepository conductorRepository = new ConductorRepository(this, view);
        boolean result = conductorRepository.deleteConductor(cedulaCon);
        if (result) {
            new AlertDialog.Builder(this)
                    .setTitle("Conductor eliminado")
                    .setMessage("El conductor con cédula " + cedulaCon + " ha sido eliminado exitosamente.")
                    .setPositiveButton("Aceptar", null)
                    .show();
            limpiarCampos();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("No se encontró un conductor con cédula: " + cedulaCon)
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }

    private void modificarConductor(View view) {
        if (etCedulaCon.getText().toString().trim().isEmpty() ||
           etNombreCon.getText().toString().trim().isEmpty() ||
           etApellidoCon.getText().toString().trim().isEmpty() ||
           etDireccionCon.getText().toString().trim().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Campos vacíos")
                    .setMessage("No puedes dejar campos vacíos")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }
            capData();
            if (cedulaCon == 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("La cédula ingresada no es válida.")
                        .setPositiveButton("Aceptar", null)
                        .show();
                return;
            }

            Conductor conductor = new Conductor(cedulaCon, nombreCon, apellidoCon, telefonoCon, direccionCon);
            ConductorRepository conductorRepository = new ConductorRepository(this, view);
            boolean result = conductorRepository.updateConductor(conductor);
            if (result) {
                new AlertDialog.Builder(this)
                        .setTitle("Conductor actualizado")
                        .setMessage("El conductor con cédula " + cedulaCon + " ha sido actualizado exitosamente.")
                        .setPositiveButton("Aceptar", null)
                        .show();
                limpiarCampos();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("No se encontró un conductor con cédula: " + cedulaCon)
                        .setPositiveButton("Aceptar", null)
                        .show();
            }
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
        limpiarCampos();
    }

    private void capData(){
        try {
            cedulaCon = Long.parseLong(etCedulaCon.getText().toString().trim());
            nombreCon = etNombreCon.getText().toString().trim();
            apellidoCon = etApellidoCon.getText().toString().trim();
            telefonoCon = Long.parseLong(etTelefonoCon.getText().toString().trim());
            direccionCon = etDireccionCon.getText().toString().trim();
        } catch (NumberFormatException e) {
            cedulaCon = 0;
            telefonoCon =0;
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Cédula o teléfono no válidos. Ingrese solo números.")
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
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

    private void referencia(){
        this.etCedulaCon = findViewById(R.id.etCedulaCon);
        this.etNombreCon = findViewById(R.id.etNombreCon);
        this.etApellidoCon = findViewById(R.id.etApellidoCon);
        this.etTelefonoCon = findViewById(R.id.etTelefonoCon);
        this.etDireccionCon = findViewById(R.id.etDireccionCon);
        this.btnCrearCon = findViewById(R.id.btnCrearTaxi);
        this.btnBuscar = findViewById(R.id.btnBuscarU);
        this.btnModificarCon = findViewById(R.id.btnModificarCon);
        this.btnEliminarCon = findViewById(R.id.btnEliminarCon);
        this.context = this;
        this.direccionCon = etDireccionCon.getText().toString();

    }
}
