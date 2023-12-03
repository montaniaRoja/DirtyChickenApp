// RevisarPedido.java
package com.example.dirtychickenapp.pantallas;


import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.objetos.DetallePedido;
import com.example.dirtychickenapp.objetos.TotalPedido;

import java.util.Arrays;
import java.util.Objects;

public class RevisarPedido extends AppCompatActivity {
    ListView orderList;
    EditText txtTotal;
    Button btnCancelar, btnConfirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisar_pedido);
        orderList = findViewById(R.id.orderList);
        txtTotal=findViewById(R.id.txtTotal);
        btnCancelar=findViewById(R.id.btnCancelar);
        btnConfirmar=findViewById(R.id.btnConfirmar);

        DetallePedido[] pedidoArray = (DetallePedido[]) Objects.requireNonNull(getIntent().getSerializableExtra("pedidoArray"));
        txtTotal.setText(TotalPedido.getTotal());

        btnCancelar.setOnClickListener(e->cancelar());

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

    private void cancelar() {

        DetallePedido[] pedidoArray = new DetallePedido[0];

        Adapter.clearDetallesPedido();

        Intent intent = new Intent(RevisarPedido.this, MainActivity.class);

        intent.putExtra("pedidoArray", pedidoArray);

        startActivity(intent);

        finish();
    }

}
