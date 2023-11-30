package com.example.dirtychickenapp.pantallas;
import com.example.dirtychickenapp.objetos.Producto;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import android.os.Bundle;
import android.widget.Toast;
import org.json.JSONObject;


import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.database.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListaProductos extends AppCompatActivity {

    List<Producto> producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        init();
        fetchDataFromServer();
    }

    private void fetchDataFromServer() {
        String url = "https://adolfocarranzauth.pw/pmovilfinal/proyectofinalmovil01/listProductos.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Procesar la respuesta JSON y actualizar los datos en el adaptador
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud HTTP
                        Toast.makeText(ListaProductos.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                // Obtener el objeto JSON directamente desde el array
                JSONObject productJson = response.getJSONObject(i);

                // Parsear los datos del producto
                int id = productJson.getInt("id_producto");
                String productName = productJson.getString("nombre_producto");
                double productPrice = productJson.getDouble("precio");
                String fileName = productJson.getString("nombre_archivo");
                String fileType = productJson.getString("tipo_archivo");
                String ruta = productJson.getString("ruta_almacenamiento");

                producto.add(new Producto(id, productName, productPrice, fileName, fileType, ruta));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    public void init(){
        producto=new ArrayList<>();
        producto.add(new Producto());

        ListAdapter listAdapter=new ListAdapter(producto,this);
        RecyclerView recyclerView=findViewById(R.id.listRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

    }
}