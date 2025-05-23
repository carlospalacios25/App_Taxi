package com.edu.uniminuto.app_taxi.moduls;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.uniminuto.app_taxi.MainActivity;
import com.edu.uniminuto.app_taxi.R;

public class ModuloMenu extends AppCompatActivity {
    private Button btnMconductor;
    private Intent intent;
    private Button btnMtaxi;
    private Button btnGastos;
    private Button btnUsuario;
    private Button btnMApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu);


        this.Referencia();
        this.eventos();
        this.btnMconductor.setOnClickListener(this::OpenModConductor);
        this.btnMtaxi.setOnClickListener(this::OpenModTaxi);
        this.btnGastos.setOnClickListener(this::OpenModGasto);
        this.btnUsuario.setOnClickListener(this::OpenUsuario);
        this.btnMApi.setOnClickListener(this::ApiOpen);
    }

    private void ApiOpen(View view) {
        this.intent = new Intent(ModuloMenu.this, ModuloApi.class);
        startActivityForResult(intent, 1);
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
    private void eventos(){
        View[] buttons = {btnUsuario, btnMtaxi, btnMconductor, btnGastos};
        for (View button : buttons) {
            button.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                }
                return false;
            });
        }
    }
    private void Referencia(){
        this.btnUsuario = findViewById(R.id.btnUsuario);
        this.btnMtaxi = findViewById(R.id.btnMtaxi);
        this.btnMconductor =findViewById(R.id.btnMconductor);
        this.btnGastos =findViewById(R.id.btnGastos);
        this.btnMApi =findViewById(R.id.btnApi);
    }
}
