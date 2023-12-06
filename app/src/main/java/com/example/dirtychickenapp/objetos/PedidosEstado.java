package com.example.dirtychickenapp.objetos;

public class PedidosEstado {
    private int id;
    private String correo;
    private String estado;
    private double total;

    public PedidosEstado(int id, String correo, double total, String estado) {
        this.id = id;
        this.estado = estado;
        this.total = total;
        this.correo=correo;
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

    public String getCorreo() {
        return correo;
    }
}
