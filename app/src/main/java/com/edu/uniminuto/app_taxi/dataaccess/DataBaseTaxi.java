package com.edu.uniminuto.app_taxi.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseTaxi extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "gestionTaxis";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_CONDUCTOR = "conductor";
    private static final String TABLE_TAXI = "taxi";
    private static final String  TABLE_COMPRA ="compra";
    private static final String  TABLE_VENTA = "venta";

    public DataBaseTaxi(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            final String CREATE_TABLE_CONDUCTOR = "CREATE TABLE " + TABLE_CONDUCTOR + " (" +
                    "cedula_con BIGINT PRIMARY KEY NOT NULL, " +
                    "nombre_con VARCHAR(80) NOT NULL, " +
                    "apellido_con VARCHAR(80) NOT NULL, " +
                    "telefono_con BIGINT NOT NULL, " +
                    "direccion_con VARCHAR(200) NOT NULL)";
            db.execSQL(CREATE_TABLE_CONDUCTOR);

            final String CREATE_TABLE_TAXI = "CREATE TABLE " + TABLE_TAXI + " (" +
                    "marca_taxi VARCHAR(80) NOT NULL PRIMARY KEY, " +
                    "placa_taxi VARCHAR(15) NOT NULL, " +
                    "cedula_con BIGINT NOT NULL, " +
                    "FOREIGN KEY(cedula_con) REFERENCES " + TABLE_CONDUCTOR + "(cedula_con))";
            db.execSQL(CREATE_TABLE_TAXI);

            final String CREATE_TABLE_COMPRA = "CREATE TABLE " + TABLE_COMPRA + " (" +
                    "id_compra INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "producto_com VARCHAR(100) NOT NULL, " +
                    "precioUni_com REAL NOT NULL, " +
                    "cantidad_com INTEGER NOT NULL, " +
                    "fecha_com TEXT NOT NULL, " +
                    "descripcion_com VARCHAR(256) NOT NULL, " +
                    "marca_taxi INTEGER NOT NULL, " +
                    "FOREIGN KEY(marca_taxi) REFERENCES " + TABLE_TAXI + "(marca_taxi))";
            db.execSQL(CREATE_TABLE_COMPRA);

            final String CREATE_TABLE_VENTA = "CREATE TABLE " + TABLE_VENTA + " (" +
                    "id_venta INTEGER NOT NULL UNIQUE PRIMARY KEY, " +
                    "producto_ven VARCHAR(100) NOT NULL, " +
                    "precioUni_ven REAL NOT NULL, " +
                    "cantidad_ven INTEGER NOT NULL, " +
                    "fecha_ven TEXT NOT NULL, " +
                    "descripcion_ven VARCHAR(256) NOT NULL, " +
                    "placa_taxi VARCHAR(20) NOT NULL, " +
                    "FOREIGN KEY(placa_taxi) REFERENCES " + TABLE_TAXI + "(placa_taxi))";
            db.execSQL(CREATE_TABLE_VENTA);

        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONDUCTOR);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAXI);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPRA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENTA);
            onCreate(db);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error upgrading database: " + e.getMessage());
        }
    }
}
