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

import com.example.rodentshelper.Notifications.NotificationsModel;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotifications;
import com.example.rodentshelper.ROOM.Visits.ViewVisits;

import java.util.List;

public class BackupWorkerVisit extends Worker {

    private static final String TAG = "BackupWorker";


    public BackupWorkerVisit(@NonNull Context context, @NonNull WorkerParameters workerParams ) {
        super ( context, workerParams );
    }

    @NonNull
    @Override
    public Result doWork () {
        //call methods to perform background task

        System.out.println("Backup Worker Visit start");
        SharedPreferences prefsNotificationVisit = getApplicationContext().getSharedPreferences("prefsNotificationVisit", Context.MODE_PRIVATE);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotifications daoNotifications = db.daoNotifications();


        if (prefsNotificationVisit.getBoolean("prefsNotificationVisit", false)) {
            Integer requestCode = daoNotifications.getLastIdFromNotificationVisit();
            if (requestCode == null)
                requestCode = 0;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(requestCode.toString(), requestCode.toString(), NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), requestCode.toString());
            builder.setContentTitle("Wizyta u weterynarza!");
            builder.setContentText("Zbliża się wizyta! Kliknij w powiadomienie aby przejrzeć listę zapisanych wizyt.");
            builder.setSmallIcon(R.drawable.rodent_notification);
            builder.setAutoCancel(true);

            Intent notifyIntent = new Intent(getApplicationContext(), ViewVisits.class);
            PendingIntent pendingIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestCode, notifyIntent, PendingIntent.FLAG_MUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestCode, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            //to be able to launch your activity from the notification
            builder.setContentIntent(pendingIntent);


            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());

            managerCompat.notify(requestCode, builder.build());


            List<NotificationsModel> notificationsModel = daoNotifications.getAllNotificationsVisit();

            Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);

            Long nextNotification;
            Long currentTime = System.currentTimeMillis();
            for (int i = 0; i < notificationsModel.size(); i++) {
                nextNotification = notificationsModel.get(i).getNext_notification_time();
                if (nextNotification <= currentTime) {
                    daoNotifications.deleteNotificationVisit(notificationsModel.get(i).getId_notification());
                    if (daoNotifications.getCountNotificationVisit() == 0) {
                        SharedPreferences.Editor prefsEditorNotificationVisit = prefsNotificationVisit.edit();
                        prefsEditorNotificationVisit.putBoolean("prefsNotificationVisit", false);
                        prefsEditorNotificationVisit.apply();
                    }

                }
            }

        }


        db.close();

        return Result.success ();
    }



}
