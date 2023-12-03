// RevisarPedido.java
package com.example.dirtychickenapp.pantallas;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.objetos.DetallePedido;

import java.util.Arrays;
import java.util.Objects;

public class RevisarPedido extends AppCompatActivity {
    ListView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisar_pedido);
        orderList = findViewById(R.id.orderList);

        // Obtén el array de DetallePedido desde el Intent
        DetallePedido[] pedidoArray = (DetallePedido[]) Objects.requireNonNull(getIntent().getSerializableExtra("pedidoArray"));

        // Verifica si el array no es nulo antes de imprimirlo
        // RevisarPedido.java
// ...

        if (pedidoArray != null) {
            Log.d("array", "Detalle del pedido en revisar pedido: ");
            for (DetallePedido detalle : pedidoArray) {
                Log.d("array", detalle.toString());
            }


            PedidoAdapter adapter = new PedidoAdapter(this, Arrays.asList(pedidoArray));
            orderList.setAdapter(adapter);
        } else {
            Log.d("array", "El array de DetallePedido es nulo.");
        }

    }
}
