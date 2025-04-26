package com.edu.uniminuto.app_taxi.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseTaxi extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "gestionTaxis";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONDUCTOR = "conductor";
    private static final String TABLE_TAXI = "taxi";

    public DataBaseTaxi(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Crear la tabla conductor primero
            final String CREATE_TABLE_CONDUCTOR = "CREATE TABLE " + TABLE_CONDUCTOR + " (" +
                    "cedula_con BIGINT PRIMARY KEY NOT NULL, " +
                    "nombre_con VARCHAR(80) NOT NULL, " +
                    "apellido_con VARCHAR(80) NOT NULL, " +
                    "telefono_con BIGINT NOT NULL, " +
                    "direccion_con VARCHAR(200) NOT NULL)";
            db.execSQL(CREATE_TABLE_CONDUCTOR);

            final String CREATE_TABLE_TAXI = "CREATE TABLE " + TABLE_TAXI + " (" +
                    "id_taxi INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "marca_taxi VARCHAR(80) NOT NULL, " +
                    "placa_taxi VARCHAR(15) NOT NULL, " +
                    "cedula_con BIGINT NOT NULL, " +
                    "FOREIGN KEY(cedula_con) REFERENCES " + TABLE_CONDUCTOR + "(cedula_con))";
            db.execSQL(CREATE_TABLE_TAXI);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAXI);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONDUCTOR);
            onCreate(db);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error upgrading database: " + e.getMessage());
        }
    }
}
