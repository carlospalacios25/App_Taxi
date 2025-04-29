package com.edu.uniminuto.app_taxi;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ModuloUsuario extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etClave;
    private String usuario;
    private String clave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.usuarios);

        this.reference();

    }
    private void reference(){
        this.etUsuario =findViewById(R.id.etUsuario);
        this.etClave =findViewById(R.id.etClave);
    }

}
