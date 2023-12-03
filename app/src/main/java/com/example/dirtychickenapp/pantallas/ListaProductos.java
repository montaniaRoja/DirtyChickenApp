package com.example.dirtychickenapp.pantallas;
import com.example.dirtychickenapp.objetos.DetallePedido;
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

import java.util.ArrayList;
import java.util.List;

public class ListaProductos extends AppCompatActivity implements OnItemClickListener {
    ImageButton btnProcesar;
    private int contadorClicks = 0;
    List<Producto> productoList;
    RecyclerView recyclerView;
    EditText txtQty;
    private final String urlProductos = "https://adolfocarranzauth.pw/pmovilfinal/proyectofinalmovil01/listProductos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        recyclerView = findViewById(R.id.listRV);  // Use the class-level variable here
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtQty=findViewById(R.id.txtQty);
        txtQty.setText(String.valueOf(0));
       // txtQty.setEnabled(false);
       txtQty.setKeyListener(null);
       txtQty.setFocusable(false);
        txtQty.setClickable(false);
        txtQty.setLongClickable(false);
        productoList = new ArrayList<>();
        btnProcesar=findViewById(R.id.btnProcesar);

        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<DetallePedido> detallesPedido = Adapter.getDetallesPedido();

                Intent intent = new Intent(ListaProductos.this, RevisarPedido.class);
                intent.putExtra("pedidoArray", detallesPedido.toArray(new DetallePedido[detallesPedido.size()]));
                startActivity(intent);
            }
        });



        btnProcesar.setEnabled(false);

        cargarProductos();
    }



    private void cargarProductos() {
        // Reemplaza ".." con la ruta correcta
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlProductos,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject producto = array.getJSONObject(i);
                                String ruta = producto.getString("ruta_almacenamiento");
                                ruta = ruta.replace("..", "adolfocarranzauth.pw/pmovilfinal/proyectofinalmovil01");
                                ruta = "https://" + ruta;  // Add the protocol to form a valid URL
                                Log.d("Ruta recuperada",ruta);
                                productoList.add(new Producto(
                                        producto.getInt("id_producto"),
                                        producto.getString("nombre_producto"),
                                        producto.getDouble("precio"),
                                        ruta
                                ));
                            }
                            TotalPedido totalPedido = new TotalPedido();
                            Adapter adapter = new Adapter(productoList, ListaProductos.this,ListaProductos.this, totalPedido );
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListaProductos.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListaProductos.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ListaProductos.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(int position, int clickCount) {
        contadorClicks = clickCount;
        txtQty.setText(String.valueOf(contadorClicks));
        Log.d("conteo de articulos"," cantidad clicks  "+contadorClicks);
        if(contadorClicks>0){
            btnProcesar.setEnabled(true);
        }

    }


}
