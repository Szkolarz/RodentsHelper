package com.android.rodentshelper.Notifications;

public class FlagSetupFeeding {

    private static Boolean flagIsNotificationFirst;


    public FlagSetupFeeding() {
    }

    public static Boolean getFlagIsNotificationFirst() {
        return flagIsNotificationFirst;
    }

    public static void setFlagIsNotificationFirst(Boolean flagIsNotificationFirst) {
        FlagSetupFeeding.flagIsNotificationFirst = flagIsNotificationFirst;
    }
}
