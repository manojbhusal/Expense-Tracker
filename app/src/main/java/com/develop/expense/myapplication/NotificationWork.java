package com.develop.expense.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Shailendra on 12/11/2016.
 */
public class NotificationWork extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1=new Intent(context,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent).setContentTitle("Expense").setContentText("Reminder for transaction for today.")
                .setAutoCancel(true).setSmallIcon(R.mipmap.ic_launcher).setSound(alarmSound);
        builder.setLights(Color.RED, 3000, 3000);
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        notificationManager.notify(100,builder.build());
        Toast.makeText(context,"Time for filling transaction for today.",Toast.LENGTH_LONG).show();
    }
}
