package com.example.rodentshelper.Notifications.Workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.rodentshelper.Notifications.SettingUpAlarms.NotificationFeeding;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotifications;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

public class BackupWorkerFeeding extends Worker {

    private static final String TAG = "BackupWorker";


    public BackupWorkerFeeding(@NonNull Context context, @NonNull WorkerParameters workerParams ) {
        super ( context, workerParams );
    }

    @NonNull
    @Override
    public Result doWork () {
        //call methods to perform background task

        System.out.println("Backup Worker Feeding start");
        SharedPreferences prefsNotificationFeeding = getApplicationContext().getSharedPreferences("prefsNotificationFeeding", Context.MODE_PRIVATE);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotifications daoNotifications = db.daoNotifications();




        if (prefsNotificationFeeding.getBoolean("prefsNotificationFeeding", false)) {

            SharedPreferences prefsRequestCodeFeeding = getApplicationContext().getSharedPreferences("prefsRequestCodeFeeding", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditorRequestCodeFeeding = prefsRequestCodeFeeding.edit();

            Integer requestCode;

            if (!prefsRequestCodeFeeding.getBoolean("prefsRequestCodeFeeding", true)) {
                prefsEditorRequestCodeFeeding.putBoolean("prefsRequestCodeFeeding", true);
                prefsEditorRequestCodeFeeding.apply();
                requestCode = daoNotifications.getFirstIdFromNotificationFeeding();
                System.out.println(requestCode + "req1 worker");
            } else {
                prefsEditorRequestCodeFeeding.putBoolean("prefsRequestCodeFeeding", false);
                prefsEditorRequestCodeFeeding.apply();
                requestCode = daoNotifications.getLastIdFromNotificationFeeding();
                System.out.println(requestCode + "req2 worker");
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(requestCode.toString(), requestCode.toString(), NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), requestCode.toString());
            builder.setContentTitle("Czas karmienia!");
            builder.setContentText("Twój pupil domaga się jedzenia! Pamiętaj również, aby codziennie miał dostęp do świeżej wody!");
            builder.setSmallIcon(R.drawable.rodent_notification);
            builder.setAutoCancel(true);

            Intent notifyIntent = new Intent(getApplicationContext(), ViewRodents.class);
            PendingIntent pendingIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestCode, notifyIntent, PendingIntent.FLAG_MUTABLE);
            else
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestCode, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);



            builder.setContentIntent(pendingIntent);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());

            System.out.println(requestCode + " WORKER REQUEST KODE");

            managerCompat.notify(requestCode, builder.build());


            daoNotifications.updateUnixTimestampFeeding(System.currentTimeMillis(), requestCode);


            NotificationFeeding notificationFeeding = new NotificationFeeding();
            notificationFeeding.setUpNotificationFeeding(getApplicationContext());

            Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }

        db.close();

        return Result.success ();
    }



}
