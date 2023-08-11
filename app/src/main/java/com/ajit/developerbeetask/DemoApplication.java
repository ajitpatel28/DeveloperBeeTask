package com.ajit.developerbeetask;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                "running_channel", // Channel ID
                "Running Channel", // Channel name
                NotificationManager.IMPORTANCE_HIGH // Channel importance
        );


        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
