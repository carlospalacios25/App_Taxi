package com.edu.uniminuto.app_taxi.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.edu.uniminuto.app_taxi.dataaccess.DataBaseTaxi;
import com.edu.uniminuto.app_taxi.entities.Usuario;
import com.google.android.material.snackbar.Snackbar;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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
        try {
            SQLiteDatabase databaseSql = dataBaseUsuario.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombreUsuario", usuario.getUsuario());
            values.put("claveUsuario", usuario.getClave());

            long response = databaseSql.insertOrThrow("usuarios", null, values);

            String message = (response >= 1) ? "Se creó el usuario" : "No se registró";
            Toast.makeText(this.view.getContext(), message, Toast.LENGTH_SHORT).show();
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
    public static String claveEncriptada(String clave) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(clave.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la clave", e);
        }
    }
    public boolean loginUsuario(String usuarioU, String clave) {
        SQLiteDatabase dataBaseSql = dataBaseUsuario.getReadableDatabase();
        try {
            String claveEncriptada = claveEncriptada(clave);

            String query = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND claveUsuario = ?";
            Cursor cursor = dataBaseSql.rawQuery(query, new String[]{usuarioU, claveEncriptada});

            boolean isAuthenticated = cursor.getCount() > 0;
            cursor.close();
            return isAuthenticated;
        } catch (SQLException e) {
            Log.e("UsuarioRepository", "Error al autenticar usuario: " + e.getMessage(), e);
            Snackbar.make(view, "Error al autenticar: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
            return false;
        } finally {
            dataBaseSql.close();
        }
    }
}
