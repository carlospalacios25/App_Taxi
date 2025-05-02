package com.edu.uniminuto.app_taxi.moduls;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.uniminuto.app_taxi.R;
import com.edu.uniminuto.app_taxi.entities.Usuario;
import com.edu.uniminuto.app_taxi.repository.UsuarioRepository;

public class ModuloRegistroUs extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etClave;
    private String usuarios;
    private String clave;
    private Button btnRegistroNewUsu;
    private String password;
    private String confirmPassword;
    private EditText etConfClave;
    private boolean validacionClave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro);

        this.reference();
        this.btnRegistroNewUsu.setOnClickListener(this::crearUsuario);
    }
    private void crearUsuario(View view) {
        if (etClave == null || etConfClave == null || etUsuario == null) {
            Toast.makeText(this, "Error: Uno o más campos no están inicializados.", Toast.LENGTH_SHORT).show();
            return;
        }

        password = etClave.getText().toString().trim();
        confirmPassword = etConfClave.getText().toString().trim();
        validacionClave = password.equals(confirmPassword);
        if (!validacionClave) {
            new AlertDialog.Builder(this)
                    .setTitle("La Contraseña")
                    .setMessage("Las contraseñas no coinciden")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }
        if (etUsuario.getText().toString().trim().isEmpty() ||
                etClave.getText().toString().trim().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Campos vacíos")
                    .setMessage("Por favor, completa todos los campos.")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }

        try {
            capData();
            UsuarioRepository usuarioRepository = new UsuarioRepository(this, view);
            String claveHash = usuarioRepository.claveEncriptada(clave);
            Usuario usuario = new Usuario(usuarios, claveHash);
            usuarioRepository.insertarUsuario(usuario);
            limpiarCampos();

        } catch (Exception e) {
            Toast.makeText(this, "Error al crear el usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Error al crear el usuario: " + e.getMessage())
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }

    private void limpiarCampos() {
        this.etUsuario.setText("");
        this.etClave.setText("");
        this.etConfClave.setText("");
    }

    private void capData() {
        this.usuarios = etUsuario.getText().toString().trim();
        this.clave = etClave.getText().toString().trim();
    }

    private void reference() {
        this.etUsuario = findViewById(R.id.etUsuarioReg);
        this.etClave = findViewById(R.id.etClaveReg);
        this.btnRegistroNewUsu = findViewById(R.id.btnRegistroNewUsu);
        this.etConfClave = findViewById(R.id.etConfClaveReg);
    }
}
