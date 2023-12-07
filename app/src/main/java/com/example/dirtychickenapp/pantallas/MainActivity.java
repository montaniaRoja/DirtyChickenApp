package com.example.dirtychickenapp.pantallas;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import android.os.Build;
import android.util.Log;
import android.widget.Button;
import com.example.dirtychickenapp.database.SQLiteConexion;
import com.example.dirtychickenapp.database.Transacciones;



import android.os.Bundle;
import android.widget.Toast;

import com.example.dirtychickenapp.R;

import com.example.dirtychickenapp.objetos.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Button btnOrdenar;
    Button btnCerrar;
    
    Button btnHistorial;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String LAST_TOKEN_KEY = "lastToken";
    SQLiteConexion conexion;
    ArrayList<Cliente> listClientes;
    public static String clienteMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOrdenar=(Button)findViewById(R.id.btnOrdenar);
        btnCerrar=(Button)findViewById(R.id.btnCerrar);
        btnHistorial=(Button)findViewById(R.id.btnHistorial);
        //btnRegistro.setOnClickListener(e->permisos());
        conexion = new SQLiteConexion(this, Transacciones.nameDB, null, 1);


        ejecutarActualizarPedido();
        btnOrdenar.setOnClickListener(e->ordenarProductos());
        btnCerrar.setOnClickListener(e->cerrarSesion());
        
        btnHistorial.setOnClickListener(e->verHistorial());
        permisos();
        String lastToken = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(LAST_TOKEN_KEY, "");
       // getCliente();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Obtener nuevo FCM registration token
                        String newToken = task.getResult();

                        // Log y toast

                        String msg = getString(R.string.msg_token_fmt, newToken);
                        Log.d(TAG, msg);

// Agregar un Toast con el mismo mensaje
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();


                        // Comparar con el último token almacenado
                        if (!lastToken.equals(newToken)) {
                            // Si el token ha cambiado, enviar al servidor
                           // sendRegistrationToServer(newToken);

                            // Guardar el nuevo token en preferencias compartidas
                            getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putString(LAST_TOKEN_KEY, newToken).apply();


                        }

                    }

                });

        //getCliente();
    }



    private void verHistorial() {
        Intent intent = new Intent(MainActivity.this, HistorialActivity.class);
        startActivity(intent);
    }

    private void ordenarProductos() {
        Intent intent = new Intent(MainActivity.this, ListaProductos.class);
        startActivity(intent);
    }

    private void cerrarSesion() {
        try {
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.nameDB, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();
            String nombreCliente = listClientes.get(0).getNombre();
            //String nombreUsuario = obtenerNombreUsuarioActual();

            String whereClause = Transacciones.nombre + "=?";
            String[] whereArgs = {nombreCliente};

            int result = db.delete(Transacciones.Tabla1, whereClause, whereArgs);

            if (result > 0) {
                Toast.makeText(this, "Cierre de sesión exitoso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al cerrar sesión", Toast.LENGTH_SHORT).show();
            }

            db.close();


            Intent intent = new Intent(MainActivity.this, RegistrarCliente.class);
            startActivity(intent);
            finish();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al acceder a la base de datos local", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCliente() {
        Log.d("cleinte","entrando a la funcion obtener cliente");
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cliente cliente = null;
        listClientes = new ArrayList<Cliente>();

        Cursor cursor = db.rawQuery(Transacciones.SelectTableClientes, null);
        while (cursor.moveToNext()) {
            cliente = new Cliente();

            cliente.setId(cursor.getInt(0));
            cliente.setNombre(cursor.getString(1));
            cliente.setTelefono(cursor.getString(2));
            cliente.setDireccion(cursor.getString(3));
            cliente.setCorreo(cursor.getString(4));
            cliente.setLatitud(cursor.getDouble(5));
            cliente.setLongitud(cursor.getDouble(6));
            cliente.setToken(cursor.getString(7));

            listClientes.add(cliente);

            Log.d("Cliente", "ID: " + cliente.getId() +
                    ", Nombre: " + cliente.getNombre() +
                    ", Teléfono: " + cliente.getTelefono() +
                    ", Dirección: " + cliente.getDireccion() +
                    ", Correo: " + cliente.getCorreo() +
                    ", Latitud: " + cliente.getLatitud() +
                    ", Longitud: " + cliente.getLongitud() +
                    ", Token: " + cliente.getToken());
        }

        cursor.close();
        if (listClientes.isEmpty()) {
            registrarCliente();
        }  else {
            // Mostrar el nombre del cliente en el Toast
            String nombreCliente = listClientes.get(0).getNombre();
            String correoCliente=listClientes.get(0).getCorreo();
            clienteMail=correoCliente;
            String mensaje = getString(R.string.RespuestaTemp) + " " + nombreCliente;

            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }

    }

    private static final int MY_PERMISSIONS_REQUEST = 101;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST) {
            boolean allPermissionsGranted = true;

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                Log.d("checar","TODOS LOS PEDIDOS ESTAN CONCEDIDOS");
                getCliente();
            } else {
                Toast.makeText(this, "Al menos un permiso fue denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void permisos() {
        boolean cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean notificationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;

        if (!cameraPermission || !locationPermission || !notificationPermission) {
            // Al menos un permiso falta, solicitarlos
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS
            }, MY_PERMISSIONS_REQUEST);
        } else {
            // Todos los permisos están concedidos
            Log.d("checar","TODOS LOS PERMISOS ESTAN CONCEDIDOS");
            getCliente();
        }
    }





    private void registrarCliente() {
        Intent intent=new Intent(MainActivity.this, RegistrarCliente.class);
        startActivity(intent);

    }

    public void ejecutarActualizarPedido() {
        new Thread(() -> {
            try {
                URL url = new URL("https://adolfocarranzauth.pw/pmovilfinal/proyectofinalmovil01/ActualizarPedido.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                // Obtener la respuesta del servidor
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Éxito

                } else {
                    // Error
                }

                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
   // @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        String msg = getString(R.string.msg_token_fmt, token);
        Log.d("token","Token: "+msg);
    }


}