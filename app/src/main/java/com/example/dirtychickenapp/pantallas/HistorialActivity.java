package com.example.dirtychickenapp.pantallas;

import androidx.appcompat.app.AppCompatActivity;
import com.example.dirtychickenapp.objetos.DetallePedido;
import com.example.dirtychickenapp.objetos.PedidosEstado;
import com.example.dirtychickenapp.objetos.TotalPedido;
import com.example.dirtychickenapp.pantallas.OnItemClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.objetos.Producto;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;

import com.example.dirtychickenapp.R;

public class HistorialActivity extends AppCompatActivity {
    private static String URL_estados;

    List<PedidosEstado>  pedidosEstadoList;
    RecyclerView historialView;
    String correoCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        historialView=(RecyclerView)findViewById(R.id.historialView);
        historialView.setHasFixedSize(true);
        historialView.setLayoutManager(new LinearLayoutManager(this));
        pedidosEstadoList=new ArrayList<>();
        correoCliente=MainActivity.clienteMail;
        Log.d("correo","correo para filtrar"+correoCliente);
        try {
            cargarPedidos();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


    }

    private void cargarPedidos() throws UnsupportedEncodingException {
        //String correoClienteEncoded = URLEncoder.encode(correoCliente, "UTF8");

        URL_estados="https://adolfocarranzauth.pw/pmovilfinal/proyectofinalmovil01/listPedidos.php?correo="+correoCliente;
        Log.d("URL", "URL solicitada: " + URL_estados);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_estados,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {


                                JSONObject pedido = array.getJSONObject(i);


                                pedidosEstadoList.add(new PedidosEstado(
                                        pedido.getInt("id_pedido"),
                                        pedido.getString("correo"),
                                        pedido.getDouble("total"),
                                        pedido.getString("estado"),
                                        pedido.getDouble("latitud"),
                                        pedido.getDouble("longitud")

                                ));
                            }

                            HistorialAdapter adapter = new HistorialAdapter(HistorialActivity.this, pedidosEstadoList);
                            historialView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HistorialActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistorialActivity.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(HistorialActivity.this);
        requestQueue.add(stringRequest);

    }
}