package com.example.dirtychickenapp.objetos;

public class PedidosEstado {
    private int id;
    private String estado;
    private double total;

    public PedidosEstado(int id, String estado, double total) {
        this.id = id;
        this.estado = estado;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public double getTotal() {
        return total;
    }
}
