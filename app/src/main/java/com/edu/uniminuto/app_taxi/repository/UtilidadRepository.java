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
import java.util.Date;

public class UtilidadRepository {
    private DataBaseTaxi dataBaseUtilidad;
    private Context context;
    private View view;

    public UtilidadRepository(Context context, View view) {
        this.context = context;
        this.view = view;
        this.dataBaseUtilidad = new DataBaseTaxi(context);
    }

    public void insertUtilidad(Utilidad utilidad) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormatted = sdf.format(utilidad.getFecha_com());
        try {
            SQLiteDatabase databaseSql = dataBaseUtilidad.getWritableDatabase();
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
        }
    }

    public boolean placaTaxiExists(String placa_taxi) {
        try {
            SQLiteDatabase db = dataBaseUtilidad.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT placa_taxi FROM taxi WHERE placa_taxi = ?",
                    new String[]{placa_taxi});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("UtilidadRepository", "placaTaxiExists: " + e.getMessage());
            return false;
        }
    }

    public boolean conductorExists(long cedula_con) {
        try {
            SQLiteDatabase db = dataBaseUtilidad.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT cedula_con FROM conductor WHERE cedula_con = ?",
                    new String[]{String.valueOf(cedula_con)});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("UtilidadRepository", "conductorExists: " + e.getMessage());
            return false;
        }
    }
    public Utilidad getUtilidadByParams(Date fecha, String placa, long cedula) {
        try {
            SQLiteDatabase dataBaseSql = dataBaseUtilidad.getReadableDatabase();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormatted = sdf.format(fecha);
            String query = "SELECT * FROM registroDiario WHERE fecha_com = ? AND placa_taxi = ? AND cedula_con = ?";
            Cursor cursor = dataBaseSql.rawQuery(query, new String[]{fechaFormatted, placa, String.valueOf(cedula)});

            if (cursor.moveToFirst()) {
                Utilidad utilidad = new Utilidad();
                utilidad.setGastos_com(cursor.getDouble(1));
                utilidad.setIgresos_com(cursor.getDouble(2));
                utilidad.setFecha_com(sdf.parse(cursor.getString(4)));
                utilidad.setDescripcion_com(cursor.getString(5));
                utilidad.setPlaca_taxi(cursor.getString(6));
                utilidad.setCedula_con(cursor.getLong(7));
                return utilidad;
            }
            return null;
        } catch (Exception e) {
            Log.e("UtilidadRepository", "getUtilidadByParams: " + e.getMessage());
            return null;
        }
    }
}
