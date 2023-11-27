package com.example.dirtychickenapp.database;

public class Transacciones
{
    // Nombre de la base de datos
    public static final String nameDB = "dbrestaurantepm";

    //Tablas de la base de datos
    public static final String Tabla1  = "clientes";


    // Campos de la tabla
    public static final String id_cliente = "id_cliente";

    public static final String nombre_cliente = "nombre_cliente";
    public static final String tel_cliente = "tel_cliente";
    public static final String dir_cliente = "dir_cliente";
    public static final String lat_cliente = "lat_cliente";
    public static final String long_cliente = "long_cliente";
    public static final String correo_cliente = "correo_cliente";
    public static final String DeleteContact = "DELETE FROM " + Transacciones.Tabla1 + " WHERE " + Transacciones.id_cliente + " = ?";


    // Consultas de Base de datos
    //ddl


    public static final String CreateTableClientes = "CREATE TABLE clientes " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT,nombre_cliente varchar(100),tel_cliente varchar(100),dir_cliente varchar(200),lat_cliente varchar(100), long_cliente varchar(100), correo_cliente varchar(100))";


    public static final String DropTableClientes  = "DROP TABLE IF EXISTS clientes";

    public static final String SelectTableClientes = "SELECT * FROM " + Transacciones.Tabla1;




}