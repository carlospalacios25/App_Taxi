package com.edu.uniminuto.app_taxi.repository;

import android.content.Context;
import android.view.View;

import com.edu.uniminuto.app_taxi.dataaccess.DataBaseTaxi;

public class UsuarioRepository {
    private DataBaseTaxi dataBaseUtilidad;
    private Context context;
    private View view;

    public UsuarioRepository(Context context, View view) {
        this.context = context;
        this.view = view;
        this.dataBaseUtilidad = new DataBaseTaxi(context);
    }
}
