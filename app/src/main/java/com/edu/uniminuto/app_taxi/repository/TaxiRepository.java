package com.edu.uniminuto.app_taxi.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.edu.uniminuto.app_taxi.dataaccess.DataBaseTaxi;
import com.edu.uniminuto.app_taxi.entities.Taxi;
import com.google.android.material.snackbar.Snackbar;

public class TaxiRepository {
    private DataBaseTaxi dataBaseTaxi;
    private Context context;
    private View view;

    public TaxiRepository(Context context, View view) {
        this.context = context;
        this.view = view;
        this.dataBaseTaxi = new DataBaseTaxi(context);
    }

    public void insertTaxi(Taxi taxi) {
        SQLiteDatabase databaseSql = null;
        try {
            databaseSql = dataBaseTaxi.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("marca_taxi", taxi.getMarca_taxi());
            values.put("placa_taxi", taxi.getPlaca_taxi());
            values.put("cedula_con", taxi.getCedula_con());

            long response = databaseSql.insertOrThrow("taxi", null, values);

            String message = (response >= 1) ? "Se creó el taxi" : "No se registró";
            Snackbar.make(this.view, message, Snackbar.LENGTH_LONG).show();
        } catch (SQLException e) {
            Log.e("TaxiRepository", "insertTaxi: " + e.getMessage());
            Snackbar.make(this.view, "Error al registrar el taxi: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
}