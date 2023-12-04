package com.example.dirtychickenapp.database;

public class Transacciones
{
    // Nombre de la base de datos
    public static final String nameDB = "dbrestaurantepm";


    //Tablas de la base de datos
    public static final String Tabla1  = "clientes";
    public static final String Tabla2="pedidos";

    // Campos de la tabla clientes
    public static final String id = "id";

    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String direccion = "direccion";
    public static final String latitud = "latitud";
    public static final String longitud = "longitud";
    public static final String correo = "correo";
    public static final String DeleteContact = "DELETE FROM " + Transacciones.Tabla1 + " WHERE " + Transacciones.nombre + " = ?";

    // campos de la tabla pedidos
    public static final String id_pedido = "id_pedido";

    public static final String nombre_producto = "nombre_producto";
    public static final String precio_producto = "precio_producto";
    public static final String cantidad_producto = "cantidad_producto";
    public static final String total_linea = "total_linea";
    public static final String status_pedido = "status_pedido";


    public static final String CreateTableClientes = "CREATE TABLE clientes " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT,nombre varchar(100),telefono varchar(100),direccion varchar(200), correo varchar(100),latitud varchar(100), longitud varchar(100))";


    public static final String DropTableClientes  = "DROP TABLE IF EXISTS clientes";

    public static final String SelectTableClientes = "SELECT * FROM " + Transacciones.Tabla1;

    public static final String CreateTablePedidos = "CREATE TABLE pedidos " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT,nombre_producto varchar(100),precio_producto FLOAT,cantidad_producto INTEGER,toal_linea_float,status_pedido VARCHAR(10))";


    public static final String DropTablePedidos  = "DROP TABLE IF EXISTS pedidos";

    public static final String SelectTablePedidos = "SELECT * FROM " + Transacciones.Tabla2;


}