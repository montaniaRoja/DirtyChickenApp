package com.example.dirtychickenapp.pantallas;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dirtychickenapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity {

    private GoogleMap mMap;
    Button btnInicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        btnInicio=findViewById(R.id.btnInicio);
        btnInicio.setOnClickListener(e->irInicio());

        // Obtener el fragmento de mapa
        MapView mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mapView.getMapAsync(googleMap -> {
            // El mapa está listo
            mMap = googleMap;
            //COMENTARIO PARA ACTUALIZAR ARCHIVO PARA HACER COMMIT
            // Obtener la latitud y longitud de los extras
           // Intent intent = getIntent();
            double latitud = getIntent().getDoubleExtra("LATITUD", 0.0);
            double longitud = getIntent().getDoubleExtra("LONGITUD", 0.0);

            LatLng ubicacion = new LatLng(latitud, longitud);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
            mMap.addMarker(new MarkerOptions().position(ubicacion).title("Delivery DirtyChicken"));
        });

        mapView.setOnClickListener(v -> {
            // Obtener la latitud y longitud de los extras
            Intent intent = getIntent();
            double latitud = Double.parseDouble(intent.getStringExtra("latitud"));
            double longitud = Double.parseDouble(intent.getStringExtra("longitud"));

            // Crear una Uri con la ubicación
            Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud + "(Ubicación)");

            // Crear un Intent para abrir Google Maps
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");


            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });
    }

    private void irInicio() {
        Intent intent=new Intent(MapActivity.this, MainActivity.class);
        startActivity(intent);

    }
}