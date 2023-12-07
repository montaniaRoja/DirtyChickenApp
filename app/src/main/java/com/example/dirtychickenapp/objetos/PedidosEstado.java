package com.example.dirtychickenapp.objetos;

public class PedidosEstado {
    private int id;
    private String correo;
    private String estado;
    private double total;
    private double latitud;
    private double longitud;

    public PedidosEstado(int id, String correo, double total, String estado, double latitud, double longitud) {
        this.id = id;
        this.estado = estado;
        this.total = total;
        this.correo=correo;
        this.latitud=latitud;
        this.longitud=longitud;
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


    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
