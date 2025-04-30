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

import com.edu.uniminuto.app_taxi.moduls.ModuloConductor;
import com.edu.uniminuto.app_taxi.moduls.ModuloMenu;
import com.edu.uniminuto.app_taxi.moduls.ModuloTaxi;
import com.edu.uniminuto.app_taxi.moduls.ModuloUsuario;
import com.edu.uniminuto.app_taxi.moduls.ModuloUtilidad;

public class MainActivity extends AppCompatActivity {
    private Button prueba;
    private Intent intent;


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
        this.prueba.setOnClickListener(this::OpenMenu);

    }

    private void OpenMenu(View view) {
        this.intent = new Intent(MainActivity.this, ModuloMenu.class);
        startActivityForResult(intent, 1);
    }

    private void Referencia(){
        this.prueba = findViewById(R.id.btnIngresar);
    }
}