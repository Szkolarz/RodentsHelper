package com.example.rodentshelper.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //Intent intent1 = new Intent(context, Notifications1.class);


        //Intent serviceIntent = new Intent ( context, BackupService.class );
        System.out.println("not boot");
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BackupWorker.class).addTag("BACKUP_WORKER_TAG").build();
        WorkManager.getInstance(context).enqueue(request);


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.startService ( intent1 );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, intent1);
        } else {
            context.startService(intent1);
        }*/


    }
}