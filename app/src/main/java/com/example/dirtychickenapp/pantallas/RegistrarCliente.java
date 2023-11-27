package com.example.dirtychickenapp.pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.dirtychickenapp.R;
import com.example.dirtychickenapp.database.SQLiteConexion;
import com.example.dirtychickenapp.database.Transacciones;

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
        getLocation();


        btnGuardarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCliente();
            }
        });

    }

    private void guardarCliente() {
        guardarClienteMysql();
        guardarClienteLocal();
        txtNombre.setText("");
        txtPhone.setText("");
        txtDireccion.setText("");
        txtCorreo.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");

        Intent intent=new Intent(RegistrarCliente.this, MainActivity.class);
        startActivity(intent);

    }



    private void guardarClienteLocal() {
        String nombre = txtNombre.getText().toString();
        String telefono=txtPhone.getText().toString();
        String direccion = txtDireccion.getText().toString();
        String latitud = txtLatitud.getText().toString();
        String longitud = txtLongitud.getText().toString();
        String correo = txtCorreo.getText().toString();


        try {

            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.nameDB, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put(Transacciones.nombre_cliente, nombre);
            valores.put(Transacciones.tel_cliente, telefono);
            valores.put(Transacciones.dir_cliente, direccion);
            valores.put(Transacciones.lat_cliente, latitud);
            valores.put(Transacciones.long_cliente, longitud);
            valores.put(Transacciones.correo_cliente, correo);

            Long Result = db.insert(Transacciones.Tabla1, Transacciones.id_cliente, valores);

            Toast.makeText(this, getString(R.string.Respuesta), Toast.LENGTH_SHORT).show();
            db.close();


        } catch (SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.ErrorDB), Toast.LENGTH_SHORT).show();
        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(this, getString(R.string.ErrorResp), Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarClienteMysql() {
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

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    txtLatitud.setText(String.valueOf(latitude));
                    txtLongitud.setText(String.valueOf(longitude));
                    locationManager.removeUpdates(this);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102 && resultCode == RESULT_OK) {

            checkLocationPermission();

        }
    }

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission has already been granted.

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with getting the location.
                //getLocation();
            } else {
                // Permission denied, handle accordingly.
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}