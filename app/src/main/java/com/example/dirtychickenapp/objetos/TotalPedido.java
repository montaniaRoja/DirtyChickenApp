package com.example.dirtychickenapp.objetos;

public class TotalPedido {
    private static String total;

    public TotalPedido(){

    }

    public static String getTotal() {
        return total;
    }

    public static void setTotal(String total) {
        TotalPedido.total = total;
    }
}
