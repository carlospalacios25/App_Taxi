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

public class ModuloRecuperarU extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etClave;
    private String usuarios;
    private String clave;
    private Button btnModificarUsu;
    private String usuarioBus;
    private String usuarioConBus;
    private EditText etConfClave;
    private boolean validacionClave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.olvidoclave);

        this.reference();

        this.btnModificarUsu.setOnClickListener(this::modificarUsuario);
    }

    private void modificarUsuario(View view) {
        if (etUsuario.getText().toString().trim().isEmpty() ||
                etClave.getText().toString().trim().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Campos vacíos")
                    .setMessage("No puedes dejar campos vacíos")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }
        usuarioBus = etClave.getText().toString().trim();
        usuarioConBus = etConfClave.getText().toString().trim();
        validacionClave = usuarioBus.equals(usuarioConBus);
        if (validacionClave != true) {
            new AlertDialog.Builder(this)
                    .setTitle("La Contraseña")
                    .setMessage("Las contraseñas no coinciden")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }
        capData();
        if (usuarios == "") {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("La placa ingresada no es válida.")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }
        UsuarioRepository usuarioRepository = new UsuarioRepository(this, view);
        String claveHash = usuarioRepository.claveEncriptada(clave);
        Usuario usuario = new Usuario(usuarios,claveHash);

        boolean result = usuarioRepository.updateUsuario(usuario);
        if (result) {
            Toast.makeText(this, "Usuario Actualizado: ", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("No se encontró Usuario: " + usuarios)
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
        this.etUsuario = findViewById(R.id.etUsuarioRecup);
        this.etClave = findViewById(R.id.etClaveRecup);
        this.etConfClave = findViewById(R.id.etConfClaveRecup);
        this.btnModificarUsu = findViewById(R.id.btnRecuperar);

    }
}
