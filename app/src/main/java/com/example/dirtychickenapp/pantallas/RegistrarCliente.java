package com.example.dirtychickenapp.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dirtychickenapp.database.Transacciones;
import com.example.dirtychickenapp.objetos.Cliente;
import com.example.dirtychickenapp.database.Transacciones;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.dirtychickenapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrarCliente extends AppCompatActivity {


    EditText txtNombre, txtDireccion, txtPhone, txtCorreo, txtLatitud, txtLongitud;
    Button btnGuardarCliente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);

        txtNombre=findViewById(R.id.txtNombre);
        txtDireccion=findViewById(R.id.txtDireccion);
        txtPhone=findViewById(R.id.txtPhone);
        txtCorreo=findViewById(R.id.txtCorreo);
        txtLatitud=findViewById(R.id.txtLatitud);
        txtLongitud=findViewById(R.id.txtLongitud);
        btnGuardarCliente=(Button)findViewById(R.id.btnGuardarCliente);
        btnGuardarCliente.setOnClickListener(e->guardarCliente());

    }

    private void guardarCliente() {
        // Obtener los datos de los EditText
        String nombre = txtNombre.getText().toString();
        String direccion = txtDireccion.getText().toString();
        String telefono = txtPhone.getText().toString();
        String correo = txtCorreo.getText().toString();
        double latitud = Double.parseDouble(txtLatitud.getText().toString());
        double longitud = Double.parseDouble(txtLongitud.getText().toString());

        // Crear un objeto JSON con los datos
        JSONObject jsonCliente = new JSONObject();
        try {
            jsonCliente.put("nombre_cliente", nombre);
            jsonCliente.put("tel_cliente", telefono);
            jsonCliente.put("dir_cliente", direccion);
            jsonCliente.put("lat_cliente", latitud);
            jsonCliente.put("long_cliente", longitud);
            jsonCliente.put("correo_cliente", correo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Realizar la solicitud HTTP POST
        new Thread(() -> {
            try {
                URL url = new URL("https://adolfocarranzauth.pw/pmovilfinal/proyectofinalmovil01/CrearCliente.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                // Escribir el objeto JSON en el cuerpo de la solicitud
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

                }

                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}