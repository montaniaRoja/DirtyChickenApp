package com.example.dirtychickenapp;

import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Verificar si el mensaje tiene datos
        if (remoteMessage.getData().size() > 0) {
            // Manejar los datos del mensaje
            handleDataMessage(remoteMessage.getData());
        }

        // Verificar si el mensaje tiene una notificación
        if (remoteMessage.getNotification() != null) {
            // Manejar la notificación
            handleNotification(remoteMessage.getNotification());
        }
    }

    private void handleDataMessage(Map<String, String> data) {
        // Aquí puedes procesar los datos del mensaje, por ejemplo, extraer información específica
        // que el servidor haya incluido en el mensaje.
        String mensaje = data.get("mensaje");
        String otroDato = data.get("otro_dato");

        // Puedes mostrar una notificación, actualizar la interfaz de usuario, etc., según tus necesidades.
    }

    private void handleNotification(RemoteMessage.Notification notification) {
        // Aquí puedes manejar la parte visual de la notificación si es necesario.
        String title = notification.getTitle();
        String body = notification.getBody();

        // Mostrar la notificación en la barra de notificaciones, por ejemplo
        showNotification(title, body);
    }

    private void showNotification(String title, String body) {
        // Aquí puedes utilizar la clase NotificationManager para mostrar una notificación.
        // Esto dependerá de cómo quieras diseñar y mostrar tus notificaciones.

        // Ejemplo básico:
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.comprobado)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(uniqueId, builder.build());
    }

    int uniqueId = (int) System.currentTimeMillis(); // Usar la marca de tiempo actual como ID único



}
