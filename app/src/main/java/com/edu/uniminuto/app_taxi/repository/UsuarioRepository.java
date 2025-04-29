package com.edu.uniminuto.app_taxi.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.edu.uniminuto.app_taxi.dataaccess.DataBaseTaxi;
import com.edu.uniminuto.app_taxi.entities.Taxi;
import com.edu.uniminuto.app_taxi.entities.Usuario;
import com.google.android.material.snackbar.Snackbar;


public class UsuarioRepository {
    private DataBaseTaxi dataBaseUsuario;
    private Context context;
    private View view;

    public UsuarioRepository(Context context, View view) {
        this.context = context;
        this.view = view;
        this.dataBaseUsuario = new DataBaseTaxi(context);
    }

    public void insertarUsuario(Usuario usuario) {
        SQLiteDatabase databaseSql = null;
        try {
            databaseSql = dataBaseUsuario.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombreUsuario", usuario.getUsuario());
            values.put("claveUsuario", usuario.getClave());

            long response = databaseSql.insertOrThrow("usuarios", null, values);

            String message = (response >= 1) ? "Se creó el usuario" : "No se registró";
            Snackbar.make(this.view, message, Snackbar.LENGTH_LONG).show();
        } catch (SQLException e) {
            Log.e("TaxiRepository", "insertTaxi: " + e.getMessage());
            Snackbar.make(this.view, "Error al registrar el taxi: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
    public Usuario getUsuarioByU(String usuarioU){
        try {
            SQLiteDatabase dataBaseSql = dataBaseUsuario.getReadableDatabase();
            String query = "SELECT * FROM usuarios WHERE nombreUsuario = ?" ;
            Cursor cursor = dataBaseSql.rawQuery(query, new String[]{String.valueOf(usuarioU)});
            Usuario usuarioCo = new Usuario();
            if (cursor.moveToFirst()){
                usuarioCo.setUsuario(cursor.getString(1));
                usuarioCo.setClave(cursor.getString(2));
            }
            cursor.close();
            dataBaseSql.close();
            return usuarioCo;
        } catch (SQLException e) {
            Log.i("Campo vacio", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
