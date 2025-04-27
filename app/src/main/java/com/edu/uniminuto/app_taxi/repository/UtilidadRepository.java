package com.edu.uniminuto.app_taxi.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.edu.uniminuto.app_taxi.dataaccess.DataBaseTaxi;
import com.edu.uniminuto.app_taxi.entities.Utilidad;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;

public class UtilidadRepository {
    private DataBaseTaxi dataBaseTaxi;
    private Context context;
    private View view;

    public UtilidadRepository(Context context, View view) {
        this.context = context;
        this.view = view;
        this.dataBaseTaxi = new DataBaseTaxi(context);
    }

    public void insertUtilidad(Utilidad utilidad) {
        SQLiteDatabase databaseSql = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormatted = sdf.format(utilidad.getFecha_com());
        try {

            databaseSql = dataBaseTaxi.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("gastos_com", utilidad.getGastos_com());
            values.put("igresos_com", utilidad.getIgresos_com());
            values.put("utilidad_com", utilidad.getUtilidad_com());
            values.put("fecha_com", fechaFormatted);
            values.put("descripcion_com", utilidad.getDescripcion_com());
            values.put("placa_taxi", utilidad.getPlaca_taxi());
            values.put("cedula_con", utilidad.getCedula_con());

            long response = databaseSql.insertOrThrow("registroDiario", null, values);

            String message = (response >= 1) ? "Se registró la utilidad" : "No se registró";
            Snackbar.make(this.view, message, Snackbar.LENGTH_LONG).show();
        } catch (SQLException e) {
            Log.e("UtilidadRepository", "insertUtilidad: " + e.getMessage());
            Snackbar.make(this.view, "Error al registrar la utilidad: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
        } finally {
            if (databaseSql != null && databaseSql.isOpen()) {
                databaseSql.close();
            }
        }
    }

    public boolean placaTaxiExists(String placa_taxi) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dataBaseTaxi.getReadableDatabase();
            cursor = db.rawQuery("SELECT placa_taxi FROM taxi WHERE placa_taxi = ?",
                    new String[]{placa_taxi});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("UtilidadRepository", "placaTaxiExists: " + e.getMessage());
            return false;
        }
    }

    public boolean conductorExists(long cedula_con) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dataBaseTaxi.getReadableDatabase();
            cursor = db.rawQuery("SELECT cedula_con FROM conductor WHERE cedula_con = ?",
                    new String[]{String.valueOf(cedula_con)});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("UtilidadRepository", "conductorExists: " + e.getMessage());
            return false;
        }
    }
}
