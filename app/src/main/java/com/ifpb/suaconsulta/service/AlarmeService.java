package com.ifpb.suaconsulta.service;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.activity.AdicionarAlarmeActivity;
import com.ifpb.suaconsulta.activity.MedicamentosActivity;

public class AlarmeService extends IntentService {

    private NotificationManager alarmNotificationManager;
    private String CHANNEL_ID = "SUA_CONSULTA_CHANNEL";
    private String CHANNEL_NAME = "SUA_CONSULTA";

    public AlarmeService() {
        super("AlarmeService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        Log.d("ALARME", "Preparando notificação do alarme");
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MedicamentosActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this, CHANNEL_ID).setContentTitle("Alarm").setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Hora do remedio"))
                .setContentText("testando alarme");

        alamNotificationBuilder.setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

            alarmNotificationManager.createNotificationChannel(mChannel);
        }

        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("ALARME", "Notificação enviada");
    }

}
