package com.example.dirtychickenapp.pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import com.example.dirtychickenapp.database.SQLiteConexion;
import com.example.dirtychickenapp.database.Transacciones;



import android.os.Bundle;
import android.widget.Toast;

import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.database.Transacciones;
import com.example.dirtychickenapp.objetos.Cliente;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnRegistro;
    Button btnIngreso;
    SQLiteConexion conexion;
    ArrayList<Cliente> listClientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegistro=(Button)findViewById(R.id.btnRegistro);
        btnIngreso=(Button)findViewById(R.id.btnIngreso);
        btnRegistro.setOnClickListener(e->permisos());
        conexion = new SQLiteConexion(this, Transacciones.nameDB, null, 1);
        getCliente();


    }
    private void getCliente() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cliente cliente = null;
        listClientes = new ArrayList<Cliente>();

        Cursor cursor = db.rawQuery(Transacciones.SelectTableClientes, null);
        while (cursor.moveToNext()) {
            cliente = new Cliente();

            cliente.setId(cursor.getInt(0));
            cliente.setNombre(cursor.getString(1));
            cliente.setDireccion(cursor.getString(3));
            cliente.setLatitud(cursor.getDouble(4));
            cliente.setLongitud(cursor.getDouble(5));
            cliente.setCorreo(cursor.getString(2));

            listClientes.add(cliente);
        }

        cursor.close();
        if (listClientes != null && !listClientes.isEmpty()) {
            btnRegistro.setEnabled(false);
        } else {
            btnRegistro.setEnabled(true);
        }

    }


    private void permisos() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        } else {
            registrarCliente();
        }
    }

    private void registrarCliente() {
        Intent intent=new Intent(MainActivity.this, RegistrarCliente.class);
        startActivity(intent);

    }

}