package com.edu.uniminuto.app_taxi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.edu.uniminuto.app_taxi.entities.Usuario;
import com.edu.uniminuto.app_taxi.moduls.ModuloMenu;
import com.edu.uniminuto.app_taxi.repository.UsuarioRepository;

public class MainActivity extends AppCompatActivity {
    private Button prueba;
    private Intent intent;
    private EditText etUsuariInciar;
    private EditText etClaveIniciar;
    private String usuarioIniciar;
    private String claveIniciar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.Referencia();
        this.prueba.setOnClickListener(this::iniciarSesion);

    }

    private void OpenMenu(View view) {
        this.intent = new Intent(MainActivity.this, ModuloMenu.class);
        startActivityForResult(intent, 1);
    }
    private void iniciarSesion(View view) {

        String usuario = etUsuariInciar.getText().toString().trim();
        String clave = etClaveIniciar.getText().toString().trim();

        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        this.capData();
        UsuarioRepository usuarioRepository = new UsuarioRepository(this, view);
        boolean result = usuarioRepository.loginUsuario(usuarioIniciar, claveIniciar);
        if (result) {
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            this.intent = new Intent(MainActivity.this, ModuloMenu.class);
            startActivity(intent);
            finish();
            //startActivityForResult(intent, 1);
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
    private void capData() {
        this.usuarioIniciar = etUsuariInciar.getText().toString().trim();
        this.claveIniciar = etClaveIniciar.getText().toString().trim();
    }

    private void Referencia(){
        this.prueba = findViewById(R.id.btnMconductor);
        this.etUsuariInciar= findViewById(R.id.etUsuariInciar);
        this.etClaveIniciar= findViewById(R.id.etClaveIniciar);
    }
}