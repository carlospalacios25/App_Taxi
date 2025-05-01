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
    public boolean updateUsuario(Usuario usuario) {
        SQLiteDatabase dataBaseSql  = dataBaseUsuario.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("usuario", usuario.getUsuario());
            values.put("clave", usuario.getClave());

            int rowsUpdated = dataBaseSql.update("usuarios", values, "nombreUsuario = ?", new String[]{String.valueOf(usuario.getUsuario())});

            return rowsUpdated > 0;
        } catch (SQLException e) {
            Log.e("ConductorRepository", "Error al actualizar usuario: " + e.getMessage(), e);
            Snackbar.make(view, "Error al actualizar el usuario: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
            return false;
        }
    }
    public boolean loginUsuario(String usuarioU,String clave){
        SQLiteDatabase dataBaseSql = dataBaseUsuario.getReadableDatabase();
        try {
            String query = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND claveUsuario = ?";
            Cursor cursor= dataBaseSql.rawQuery(query, new String[]{usuarioU, clave});
            boolean isAuthenticated = cursor.getCount() > 0;
            //Log.d("UsuarioRepository", "Autenticación: " + isAuthenticated + " para usuario: " + usuarioU);
            return isAuthenticated;
        } catch (SQLException e) {
            Log.e("UsuarioRepository", "Error al autenticar usuario: " + e.getMessage(), e);
            Snackbar.make(view, "Error al autenticar: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
            return false;
        }
    }
}
