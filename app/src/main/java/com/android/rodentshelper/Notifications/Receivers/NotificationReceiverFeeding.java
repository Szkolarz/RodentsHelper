package com.android.rodentshelper.Notifications.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.android.rodentshelper.Notifications.Workers.BackupWorkerFeeding;

public class NotificationReceiverFeeding extends BroadcastReceiver {
    public NotificationReceiverFeeding() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //Intent intent1 = new Intent(context, Notifications1.class);


        //Intent serviceIntent = new Intent ( context, BackupService.class );
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BackupWorkerFeeding.class).addTag("BACKUP_WORKER_FEEDING").build();
        WorkManager.getInstance(context.getApplicationContext()).enqueue(request);


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.startService ( intent1 );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, intent1);
        } else {
            context.startService(intent1);
        }*/


    }
}