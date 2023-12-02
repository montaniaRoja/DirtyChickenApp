package com.example.dirtychickenapp.database;

public class Transacciones
{
    // Nombre de la base de datos
    public static final String nameDB = "dbrestaurantepm";


    //Tablas de la base de datos
    public static final String Tabla1  = "clientes";
    public static final String Tabla2="pedidos";

    // Campos de la tabla clientes
    public static final String id_cliente = "id_cliente";

    public static final String nombre_cliente = "nombre_cliente";
    public static final String tel_cliente = "tel_cliente";
    public static final String dir_cliente = "dir_cliente";
    public static final String lat_cliente = "lat_cliente";
    public static final String long_cliente = "long_cliente";
    public static final String correo_cliente = "correo_cliente";
    public static final String DeleteContact = "DELETE FROM " + Transacciones.Tabla1 + " WHERE " + Transacciones.nombre_cliente + " = ?";

    // campos de la tabla pedidos
    public static final String id_pedido = "id_pedido";

    public static final String nombre_producto = "nombre_producto";
    public static final String precio_producto = "precio_producto";
    public static final String cantidad_producto = "cantidad_producto";
    public static final String total_linea = "total_linea";
    public static final String status_pedido = "status_pedido";


    public static final String CreateTableClientes = "CREATE TABLE clientes " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT,nombre_cliente varchar(100),tel_cliente varchar(100),dir_cliente varchar(200),lat_cliente varchar(100), long_cliente varchar(100), correo_cliente varchar(100))";


    public static final String DropTableClientes  = "DROP TABLE IF EXISTS clientes";

    public static final String SelectTableClientes = "SELECT * FROM " + Transacciones.Tabla1;

    public static final String CreateTablePedidos = "CREATE TABLE pedidos " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT,nombre_producto varchar(100),precio_producto FLOAT,cantidad_producto INTEGER,toal_linea_float,status_pedido VARCHAR(10))";


    public static final String DropTablePedidos  = "DROP TABLE IF EXISTS pedidos";

    public static final String SelectTablePedidos = "SELECT * FROM " + Transacciones.Tabla2;


}