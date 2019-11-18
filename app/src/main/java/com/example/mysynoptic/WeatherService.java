package com.example.mysynoptic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;



public class WeatherService extends Service {

    public static final String EXTRA_COUNT_TO = "count_to";

    private int countTo;
    private int currentNumber = 0;

    String TAG = "WEATHER";

    @Override
    public void onCreate() {
        super.onCreate();

        Notification notification = createNotification();
        startForeground(1, notification);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        countTo = intent.getIntExtra(EXTRA_COUNT_TO, 0);

        createNotification();

        startCount();

        return START_REDELIVER_INTENT;
    }

    private void startCount() {
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {

                currentNumber++;

                Log.i(TAG, "Current number: " + currentNumber);

                Notification notification = createNotification();
                startForeground(1, notification);

                if (currentNumber < countTo) {
                    handler.postDelayed(this, 1000);
                } else {
                    stopForeground(true);
                }

            }
        });
    }

    //private void createNotification() {
        private Notification createNotification() {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");

            builder.setSmallIcon(R.drawable.ic_notification);
            builder.setContentTitle("Counter is running");
            builder.setContentText("Current number: " + currentNumber);
            builder.setOngoing(true);

            Intent mainIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    mainIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            builder.setContentIntent(pendingIntent);

            return builder.build();
        }
    //}
}
