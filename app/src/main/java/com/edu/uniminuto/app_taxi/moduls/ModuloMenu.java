package com.edu.uniminuto.app_taxi.moduls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.uniminuto.app_taxi.R;

public class ModuloMenu extends AppCompatActivity {
    private Button btnMtaxi;
    private Intent intent;
    private Button btnMconductor;
    private Button btnGastos;
    private Button btnUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu);


        this.Referencia();
        this.btnMtaxi.setOnClickListener(this::OpenModTaxi);
        this.btnMconductor.setOnClickListener(this::OpenModConductor);
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
        this.btnMtaxi = findViewById(R.id.btnMtaxi);
        this.btnMconductor =findViewById(R.id.btnMconductor);
        this.btnGastos =findViewById(R.id.btnGastos);
    }
}
