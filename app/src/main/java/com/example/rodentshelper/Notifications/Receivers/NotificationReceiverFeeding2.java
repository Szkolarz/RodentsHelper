package com.example.rodentshelper.Notifications.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.rodentshelper.Notifications.Workers.BackupWorkerFeeding;
import com.example.rodentshelper.Notifications.Workers.BackupWorkerFeeding2;

public class NotificationReceiverFeeding2 extends BroadcastReceiver {
    public NotificationReceiverFeeding2() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BackupWorkerFeeding2.class).addTag("BACKUP_WORKER_FEEDING2").build();
        WorkManager.getInstance(context.getApplicationContext()).enqueue(request);

    }
}