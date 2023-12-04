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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

public class RevisarPedido extends AppCompatActivity {
    ListView orderList;
    EditText txtTotal;
    Button btnCancelar, btnConfirmar;
    double totalPedido=0;
    String correo;
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

        btnConfirmar.setOnClickListener(e->enviarPedido());
        btnCancelar.setOnClickListener(e->cancelar());





        if (pedidoArray != null) {

            //datos a enviar al servidor
            totalPedido=Double.parseDouble(TotalPedido.getTotal());
            correo=MainActivity.clienteMail;

            Log.d("correo del cliente"," datos a enviar al servidor "+MainActivity.clienteMail + " total pedido "+totalPedido);
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

    private void enviarPedido() {
        enviarencabezado();

    }

    private void enviarencabezado() {
        Log.d("","enviando encabezado");

        totalPedido=Double.parseDouble(TotalPedido.getTotal());
        correo=MainActivity.clienteMail;
        JSONObject jsonCliente = new JSONObject();
        try {

            jsonCliente.put("correo", correo);
            jsonCliente.put("total", totalPedido);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                URL url = new URL("https://adolfocarranzauth.pw/pmovilfinal/proyectofinalmovil01/CrearPedido.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(jsonCliente.toString());
                writer.flush();
                writer.close();
                outputStream.close();

                // Obtener la respuesta del servidor
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                } else {
                        Log.d("error","no se envio el json");
                }

                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
