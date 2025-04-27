package com.edu.uniminuto.app_taxi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ModuloUtilidad  extends AppCompatActivity {
    private EditText etgastosCom;
    private EditText etigresoscom;
    private EditText etutilidadTaxi;
    private EditText etfechaCom;
    private EditText etdescripcionCom;
    private EditText etPlacaTaxi;
    private EditText etcedulaCon;
    private Button btnRegistrarGi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.compra);

        this.reference();
    }

    private void reference(){
        this.etgastosCom = findViewById(R.id.etgastosCom);
        this.etigresoscom = findViewById(R.id.etigresoscom);
        this.etutilidadTaxi = findViewById(R.id.etutilidadTaxi);
        this.etfechaCom = findViewById(R.id.etfechaCom);
        this.etdescripcionCom = findViewById(R.id.etdescripcionCom);
        this.etPlacaTaxi = findViewById(R.id.etPlacaTaxi);
        this.etcedulaCon = findViewById(R.id.etcedulaCon);
        this.btnRegistrarGi = findViewById(R.id.btnRegistrarGi);
    }
}
