package com.android.rodentshelper.Notifications.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.rodentshelper.Notifications.UpdateNotification;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //Intent intent1 = new Intent(context, Notifications1.class);

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            UpdateNotification updateNotification = new UpdateNotification();
            updateNotification.checkNotificationPreferences(context);
        }
    }
}