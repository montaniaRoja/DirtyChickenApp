// RevisarPedido.java
package com.example.dirtychickenapp.pantallas;


import static com.example.dirtychickenapp.pantallas.MainActivity.LAST_TOKEN_KEY;
import static com.example.dirtychickenapp.pantallas.MainActivity.PREFS_NAME;

import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.util.Log;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.List;
import java.util.Objects;

public class RevisarPedido extends AppCompatActivity {
    ListView orderList;
    EditText txtTotal;
    Button btnCancelar, btnConfirmar;
    double totalPedido=0;
    String estado="pendiente";
    String correo;
   // String token;

    int numeroNeg=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisar_pedido);
        orderList = findViewById(R.id.orderList);
        txtTotal=findViewById(R.id.txtTotal);
        btnCancelar=findViewById(R.id.btnCancelar);
        btnConfirmar=findViewById(R.id.btnConfirmar);
       // token = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(LAST_TOKEN_KEY, "");

        DetallePedido[] pedidoArray = (DetallePedido[]) Objects.requireNonNull(getIntent().getSerializableExtra("pedidoArray"));
        txtTotal.setText(TotalPedido.getTotal());

        btnConfirmar.setOnClickListener(e-> {
            try {
                enviarPedido();
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnCancelar.setOnClickListener(e->cancelar());


        if (pedidoArray != null) {

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

    private void enviarPedido() throws JSONException {
        enviarDetalle();

        enviarencabezado();

        Toast.makeText(this, "Su pedido ha sido enviado", Toast.LENGTH_SHORT).show();
       // ejecutarActualizarPedido();
        cancelar();

    }

    private void enviarDetalle() {
        List<DetallePedido> detallesPedido = Adapter.getDetallesPedido(); // Obtén la lista de detalles

        for (DetallePedido detalle : detallesPedido) {
            // Crea un nuevo objeto jsonDetalle en cada iteración
            JSONObject jsonDetalle = new JSONObject();

            try {
                jsonDetalle.put("id_pedido", numeroNeg);
                jsonDetalle.put("producto", detalle.getNombreProducto());
                jsonDetalle.put("cantidad", detalle.getCantidad());
                jsonDetalle.put("precio", detalle.getPrecioProducto());
                jsonDetalle.put("correo", MainActivity.clienteMail);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            new Thread(() -> enviarDetalleAlServidor(jsonDetalle)).start();
        }
    }

    private void enviarDetalleAlServidor(JSONObject jsonDetalle) {
        try {
            URL url = new URL("https://adolfocarranzauth.pw/pmovilfinal/proyectofinalmovil01/CrearDetalle.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);

            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jsonDetalle.toString());
            writer.flush();
            writer.close();
            outputStream.close();

            // Obtener la respuesta del servidor
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {



            } else {
                Log.d("error", "no se envió el json detalle");
            }

            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void enviarencabezado() {
        Log.d("","enviando encabezado");

        totalPedido=Double.parseDouble(TotalPedido.getTotal());
        correo=MainActivity.clienteMail;
        String lastToken = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(LAST_TOKEN_KEY, "");
        Log.d("el token a enviar es ",lastToken);

        JSONObject jsonCliente = new JSONObject();
        try {

            jsonCliente.put("correo", correo);
            jsonCliente.put("total", totalPedido);
            jsonCliente.put("estado",estado);
            jsonCliente.put("token",lastToken);

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