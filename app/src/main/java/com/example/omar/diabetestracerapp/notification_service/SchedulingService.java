package com.example.omar.diabetestracerapp.notification_service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.omar.diabetestracerapp.ActivitySendInsulinDose;
import com.example.omar.diabetestracerapp.R;
import com.example.omar.diabetestracerapp.data_model.InsulinDose;
import com.example.omar.diabetestracerapp.database.DataSource;

import java.util.Date;

/**
 * Created by OMAR on 12/29/2016.
 */

public class SchedulingService extends IntentService{

    DataSource dataSource;
    public SchedulingService() {
        super("SchedulingService");

    }

    public static final String TAG = "Scheduling Demo";
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    @Override
    protected void onHandleIntent(Intent intent) {
        dataSource = new DataSource(this);
       InsulinDose currentInsulinDose = dataSource.retrieveCurrentInsulinDose(new Date());
            Long[] timeLeft = InsulinDose.getTimeLeft(currentInsulinDose.getDate_time(),new Date());
            Long hour= timeLeft[0];
            Long minute= timeLeft[1];

            if(hour==0 && minute ==0){
                sendNotification("Dose Time");
                Log.i(TAG, "Found doodle!!");
                // Release the wake lock provided by the BroadcastReceiver.
                DoseAlarmReceiver.completeWakefulIntent(intent);
            }

    }



    // Post a notification indicating whether event is found.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, ActivitySendInsulinDose.class), 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_item_dose)
                        .setContentTitle("Dose Time")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("New Time"))
                        .setContentText(msg)
                        .setAutoCancel(true)
                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000})
                        .setLights(Color.RED, 3000, 3000)
                        .setSound(alarmSound);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
    //
}
