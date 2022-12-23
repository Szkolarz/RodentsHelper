package com.example.rodentshelper.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class NotificationReceiverVisit extends BroadcastReceiver {
    public NotificationReceiverVisit() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BackupWorkerVisit.class).addTag("BACKUP_WORKER_VISIT").build();
        WorkManager.getInstance(context).enqueue(request);
    }
}