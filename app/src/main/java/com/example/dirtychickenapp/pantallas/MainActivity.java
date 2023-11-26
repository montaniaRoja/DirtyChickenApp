package com.example.dirtychickenapp.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;



import android.os.Bundle;

import com.example.dirtychickenapp.R;

public class MainActivity extends AppCompatActivity {

    Button btnRegistro;
    Button btnIngreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistro=(Button)findViewById(R.id.btnRegistro);
        btnIngreso=(Button)findViewById(R.id.btnIngreso);

        btnRegistro.setOnClickListener(e->registrarCliente());

    }

    private void registrarCliente() {
        Intent intent=new Intent(MainActivity.this, RegistrarCliente.class);
        startActivity(intent);

    }
}