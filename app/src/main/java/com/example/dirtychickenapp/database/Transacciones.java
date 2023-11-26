package com.example.dirtychickenapp.database;
import android.app.AlertDialog;
import android.widget.Toast;

import com.example.dirtychickenapp.objetos.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transacciones {

    public static void guardarCliente(Cliente cliente)  {
        try {
            Connection resultado = Conexion.conectarse();
            String nombre = cliente.getNombre();
            String tel = cliente.getTelefono();
            String direccion = cliente.getDireccion();
            String correo = cliente.getCorreo();
            Double lat = cliente.getLatitud();
            Double lon = cliente.getLongitud();


            String sqlString = "INSERT INTO clientes (nombre_cliente, tel_cliente, dir_cliente,lat_cliente,long_cliente, correo_cliente) VALUES (?, ?,?,?,?,?);";

            PreparedStatement stmt = resultado.prepareStatement(sqlString);

            stmt.setString(1, nombre);
            stmt.setString(2, tel);
            stmt.setString(3, direccion);
            stmt.setDouble(4, lat);
            stmt.setDouble(5, lon);
            stmt.setString(6, correo);
            int filas = stmt.executeUpdate();

            if (filas == 0) {


            }

        }
        catch (SQLException e) {
            e.printStackTrace();


        }


    }



}
