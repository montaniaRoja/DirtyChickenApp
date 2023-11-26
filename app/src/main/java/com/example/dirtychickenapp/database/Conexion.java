package com.example.dirtychickenapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private final static String ruta = "jdbc:mysql://34.71.178.20/bdrestaurantepm";
    private final static String usuario = "prmovil2023";
    private final static String contrasenia = "uthmovil2023";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection conectarse() throws SQLException {
        Connection conexion = DriverManager.getConnection(ruta, usuario, contrasenia);
        return conexion;
    }

    public static boolean probarConexion() {
        try {
            Connection conn = conectarse();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
