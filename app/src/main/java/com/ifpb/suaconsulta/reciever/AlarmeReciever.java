package com.ifpb.suaconsulta.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ifpb.suaconsulta.service.AlarmeService;

public class AlarmeReciever extends BroadcastReceiver {

    public static String ACTION_ALARM = "ACTION_ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ALARME", "to no reciever");
        Intent intent1 = new Intent(context, AlarmeService.class);
        context.startService(intent1);
        //Toast.makeText(context, "to no reciever", Toast.LENGTH_SHORT).show();
    }

}
