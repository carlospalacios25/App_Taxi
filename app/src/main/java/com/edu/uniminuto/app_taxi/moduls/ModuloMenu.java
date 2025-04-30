package com.edu.uniminuto.app_taxi.moduls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.edu.uniminuto.app_taxi.MainActivity;
import com.edu.uniminuto.app_taxi.R;

public class ModuloMenu extends AppCompatActivity {
    private Button prueba;
    private Intent intent;
    private Button btnIngresar2;
    private Button btnGastos;
    private Button btnUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu);


        this.Referencia();
        this.prueba.setOnClickListener(this::OpenModConductor);
        this.btnIngresar2.setOnClickListener(this::OpenModTaxi);
        this.btnGastos.setOnClickListener(this::OpenModGasto);
        this.btnUsuario.setOnClickListener(this::OpenUsuario);

    }

    private void OpenModGasto(View view) {
        this.intent = new Intent(ModuloMenu.this, ModuloUtilidad.class);
        startActivityForResult(intent, 1);
    }

    private void OpenModConductor(View view) {
        this.intent = new Intent(ModuloMenu.this, ModuloConductor.class);
        startActivityForResult(intent, 1);
    }
    private void OpenModTaxi(View view) {
        this.intent = new Intent(ModuloMenu.this, ModuloTaxi.class);
        startActivityForResult(intent, 1);
    }
    private void OpenUsuario(View view) {
        this.intent = new Intent(ModuloMenu.this, ModuloUsuario.class);
        startActivityForResult(intent, 1);
    }

    private void Referencia(){
        this.btnUsuario = findViewById(R.id.btnUsuario);
        this.prueba = findViewById(R.id.btnIngresar);
        this.btnIngresar2 =findViewById(R.id.btnIngresar2);
        this.btnGastos =findViewById(R.id.btnGastos);
    }
}
