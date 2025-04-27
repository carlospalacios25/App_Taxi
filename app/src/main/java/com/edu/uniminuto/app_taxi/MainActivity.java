package com.edu.uniminuto.app_taxi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button prueba;
    private Intent intent;
    private Button btnIngresar2;
    private Button btnGastos;


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
        this.prueba.setOnClickListener(this::OpenModConductor);
        this.btnIngresar2.setOnClickListener(this::OpenModTaxi);
        this.btnGastos.setOnClickListener(this::OpenModGasto);
    }

    private void OpenModGasto(View view) {
        this.intent = new Intent(MainActivity.this, ModuloUtilidad.class);
        startActivityForResult(intent, 1);
    }

    private void OpenModConductor(View view) {
        this.intent = new Intent(MainActivity.this, ModuloConductor.class);
        startActivityForResult(intent, 1);
    }
    private void OpenModTaxi(View view) {
        this.intent = new Intent(MainActivity.this, ModuloTaxi.class);
        startActivityForResult(intent, 1);
    }

    private void Referencia(){

        this.prueba = findViewById(R.id.btnIngresar);
        this.btnIngresar2 =findViewById(R.id.btnIngresar2);
        this.btnGastos =findViewById(R.id.btnGastos);
    }
}