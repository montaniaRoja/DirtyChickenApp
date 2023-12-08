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

        if (remoteMessage.getData().size() > 0) {

            handleDataMessage(remoteMessage.getData());
        }


        if (remoteMessage.getNotification() != null) {

            handleNotification(remoteMessage.getNotification());
        }
    }

    private void handleDataMessage(Map<String, String> data) {

        String mensaje = data.get("mensaje");
        String otroDato = data.get("otro_dato");


    }

    private void handleNotification(RemoteMessage.Notification notification) {

        String title = notification.getTitle();
        String body = notification.getBody();


        showNotification(title, body);
    }

    private void showNotification(String title, String body) {


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
