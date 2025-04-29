package com.edu.uniminuto.app_taxi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.uniminuto.app_taxi.entities.Taxi;
import com.edu.uniminuto.app_taxi.entities.Usuario;
import com.edu.uniminuto.app_taxi.repository.TaxiRepository;
import com.edu.uniminuto.app_taxi.repository.UsuarioRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ModuloUsuario extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etClave;
    private String usuarios;
    private String clave;
    private Button btnRegistroUs;
    private Button btnBuscarUs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.usuarios);

        this.reference();
        this.btnRegistroUs.setOnClickListener(this::crearUsuario);
        this.btnBuscarUs.setOnClickListener(this::buscarUsuario);
    }
    private void crearUsuario(View view) {
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
            String claveHash = claveEncriptada(clave);
            Usuario usuario = new Usuario(usuarios, claveHash);
            UsuarioRepository usuarioRepository = new UsuarioRepository(this, view);
            usuarioRepository.insertarUsuario(usuario);
            limpiarCampos();

        } catch (Exception e) {
            Log.e("CrearUsuario", "Error al crear el usuario: " + e.getMessage());
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Error al crear el usuario: " + e.getMessage())
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }
    public static String claveEncriptada(String clave) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(clave.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la clave", e);
        }
    }
    private void buscarUsuario(View view) {
        String usuarioBus = etUsuario.getText().toString().trim();

        if (usuarioBus.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Campo vacío")
                    .setMessage("Por favor ingresa el usuario.")
                    .setPositiveButton("Aceptar", null)
                    .show();
            return;
        }

        UsuarioRepository usuarioRepository = new UsuarioRepository(this, view);
        Usuario usuario = usuarioRepository.getUsuarioByU(usuarioBus);

        if (usuario != null) {
            etUsuario.setText(usuario.getUsuario());
            etClave.setText(usuario.getClave());
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Usuario no encontrado")
                    .setMessage("No se encontró el usuario ingresado.")
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }
    private void limpiarCampos() {
        etUsuario.setText("");
        etClave.setText("");
    }
    private void capData() {

        this.usuarios = etUsuario.getText().toString().trim();
        this.clave = etClave.getText().toString().trim();

    }
    private void reference(){
        this.etUsuario = findViewById(R.id.etUsuario);
        this.etClave = findViewById(R.id.etClave);
        this.btnRegistroUs = findViewById(R.id.btnRegistroUs);
        this.btnBuscarUs = findViewById(R.id.btnBuscarUs);
    }

}
