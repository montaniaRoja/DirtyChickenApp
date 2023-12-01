package com.example.dirtychickenapp.objetos;

public class Producto {
    private int id;

    private String nombre_producto;
    private double precio;

    private String ruta_imagen;

    public Producto(int id, String nombre_producto, double precio, String ruta_imagen) {

        this.id=id;
        this.nombre_producto = nombre_producto;
        this.precio = precio;
        this.ruta_imagen=ruta_imagen;
    }

    public Producto() {
    }

    public String getNombre_producto() {
        return nombre_producto;
    }


    public double getPrecio() {
        return precio;
    }


    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public int getId() {
        return id;
    }

}
