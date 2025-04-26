package com.edu.uniminuto.app_taxi.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.edu.uniminuto.app_taxi.dataaccess.DataBaseTaxi;
import com.edu.uniminuto.app_taxi.entities.Conductor;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ConductorRepository {
    private DataBaseTaxi dataBaseTaxi;
    private Context context;
    private View view;
    private Conductor conductor;

    public ConductorRepository(Context context, View view) {
        this.context = context;
        this.view = view;
        this.dataBaseTaxi = new DataBaseTaxi(context);
    }
    public void insertConductor(Conductor conductor){
        try {
            SQLiteDatabase databaseSql = dataBaseTaxi.getWritableDatabase();
            if (databaseSql != null){
                ContentValues values = new ContentValues();
                values.put("cedula_con",conductor.getCedula_con());
                values.put("nombre_con",conductor.getNombre_con());
                values.put("apellido_con",conductor.getApellido_con());
                values.put("telefono_con",conductor.getTelefono_con());
                values.put("direccion_con",conductor.getDireccion_con());

                long reponse = databaseSql.insert("Conductor",null ,values);

                String message = (reponse>=1)?"Se registro Correctamente":"no se registro";
                Snackbar.make(this.view,message, Snackbar.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            Log.i("Error en base de datos","insetUser: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public Conductor getConductorByCedula(long document){
        try {
            Conductor conductor = null;
            SQLiteDatabase dataBaseSql = dataBaseTaxi.getReadableDatabase();
            String query = "SELECT * FROM conductor WHERE cedula_con = ?" ;
            Cursor cursor = dataBaseSql.rawQuery(query, new String[]{String.valueOf(document)});
            if (cursor.moveToFirst()){
                conductor = new Conductor();
                conductor.setCedula_con(cursor.getInt(0));
                conductor.setNombre_con(cursor.getString(1));
                conductor.setApellido_con(cursor.getString(2));
                conductor.setTelefono_con(cursor.getInt(3));
                conductor.setDireccion_con(cursor.getString(4));

            }
            cursor.close();
            dataBaseSql.close();
            return conductor;
        } catch (SQLException e) {
            Log.i("Campo vacio", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Conductor> getConductorList() {
        SQLiteDatabase dataBaseSql = dataBaseTaxi.getReadableDatabase();
        String query = "SELECT * FROM conductor LIMIT 5";
        ArrayList<Conductor> users = new ArrayList<>();
        Cursor cursor = dataBaseSql.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Conductor conductor= new Conductor();
                conductor.setCedula_con(cursor.getInt(0));
                conductor.setNombre_con(cursor.getString(1));
                conductor.setApellido_con(cursor.getString(2));
                conductor.setTelefono_con(cursor.getInt(3));
                conductor.setDireccion_con(cursor.getString(4));
                users.add(conductor);

            } while (cursor.moveToNext());
        }
        cursor.close();
        dataBaseSql.close();
        return users;
    }

}
