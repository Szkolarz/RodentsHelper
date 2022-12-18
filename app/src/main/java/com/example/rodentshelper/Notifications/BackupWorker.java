package com.example.rodentshelper.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.rodentshelper.R;

public class BackupWorker extends Worker {

    private static final String TAG = "BackupWorker";

    private Context context;

    public BackupWorker (@NonNull Context context, @NonNull WorkerParameters workerParams ) {
        super ( context, workerParams );
    }

    @NonNull
    @Override
    public Result doWork () {
        //call methods to perform background task

        System.out.println("SA");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("info", "info", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "info");
        builder.setContentTitle("111Testowy tytuł");
        builder.setContentText("tekstu abc abc Lorem Ipsum szynszyl z gipsu");
        builder.setSmallIcon(R.drawable.rodent_notification);
        builder.setAutoCancel(true);

        Intent notifyIntent = new Intent(getApplicationContext(), NotificationsActivity.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 2, notifyIntent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());


        managerCompat.notify(3, builder.build());

        return Result.success ();
    }
}
