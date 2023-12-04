package com.example.dirtychickenapp.pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.example.dirtychickenapp.database.SQLiteConexion;
import com.example.dirtychickenapp.database.Transacciones;
import android.util.Log;


import android.os.Bundle;
import android.widget.Toast;

import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.database.Transacciones;
import com.example.dirtychickenapp.objetos.Cliente;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnOrdenar;
    Button btnCerrar;
    SQLiteConexion conexion;
    ArrayList<Cliente> listClientes;
    public static String clienteMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOrdenar=(Button)findViewById(R.id.btnOrdenar);
        btnCerrar=(Button)findViewById(R.id.btnCerrar);
        //btnRegistro.setOnClickListener(e->permisos());
        conexion = new SQLiteConexion(this, Transacciones.nameDB, null, 1);
        permisos();
        btnOrdenar.setOnClickListener(e->ordenarProductos());
        btnCerrar.setOnClickListener(e->cerrarSesion());

    }

    private void ordenarProductos() {
        Intent intent = new Intent(MainActivity.this, ListaProductos.class);
        startActivity(intent);
    }

    private void cerrarSesion() {
        try {
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.nameDB, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();
            String nombreCliente = listClientes.get(0).getNombre();
            //String nombreUsuario = obtenerNombreUsuarioActual();

            String whereClause = Transacciones.nombre + "=?";
            String[] whereArgs = {nombreCliente};

            int result = db.delete(Transacciones.Tabla1, whereClause, whereArgs);

            if (result > 0) {
                Toast.makeText(this, "Cierre de sesión exitoso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al cerrar sesión", Toast.LENGTH_SHORT).show();
            }

            db.close();


            Intent intent = new Intent(MainActivity.this, RegistrarCliente.class);
            startActivity(intent);
            finish();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al acceder a la base de datos local", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    private String obtenerNombreUsuarioActual() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cliente cliente = null;
        String nombreUsuario=null;
        String correoUsuario=null;

        Cursor cursor = db.rawQuery(Transacciones.SelectTableClientes, null);
        while (cursor.moveToNext()) {
            cliente = new Cliente();
            nombreUsuario=cliente.getNombre();
            correoUsuario=cliente.getCorreo();

        }

        cursor.close();
        return nombreUsuario;

    }
    */

    private void getCliente() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cliente cliente = null;
        listClientes = new ArrayList<Cliente>();

        Cursor cursor = db.rawQuery(Transacciones.SelectTableClientes, null);
        while (cursor.moveToNext()) {
            cliente = new Cliente();

            cliente.setId(cursor.getInt(0));
            cliente.setNombre(cursor.getString(1));
            cliente.setTelefono(cursor.getString(2));
            cliente.setDireccion(cursor.getString(3));
            cliente.setCorreo(cursor.getString(4));
            cliente.setLatitud(cursor.getDouble(5));
            cliente.setLongitud(cursor.getDouble(6));

            listClientes.add(cliente);
        }

        cursor.close();
        if (listClientes.isEmpty()) {
            registrarCliente();
        }  else {
            // Mostrar el nombre del cliente en el Toast
            String nombreCliente = listClientes.get(0).getNombre();
            String correoCliente=listClientes.get(0).getCorreo();
            clienteMail=correoCliente;
            String mensaje = getString(R.string.RespuestaTemp) + " " + nombreCliente;

            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }

    }

    private static final int MY_PERMISSIONS_REQUEST = 101;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                getCliente();
            } else {

                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void permisos() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST);
        } else {
            getCliente();
        }
    }



    private void registrarCliente() {
        Intent intent=new Intent(MainActivity.this, RegistrarCliente.class);
        startActivity(intent);

    }



}