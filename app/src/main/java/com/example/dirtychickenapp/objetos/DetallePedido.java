package com.example.dirtychickenapp.objetos;

import java.io.Serializable;

public class DetallePedido implements Serializable {
    private int posicion;
    private String nombreProducto;
    private Double precioProducto;
    private int cantidad;

    public DetallePedido(int posicion, String nombreProducto, Double precioProducto, int cantidad) {
        this.posicion = posicion;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.cantidad = cantidad;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "posicion=" + posicion +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precioProducto=" + precioProducto +
                ", cantidad=" + cantidad +
                '}';
    }

}
