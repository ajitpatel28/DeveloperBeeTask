package com.ajit.developerbeetask;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyAccessibilityService extends AccessibilityService {

    private String searchQuery = "";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        String targetWord = prefs.getString(Constants.PREF_SEARCH_WORD, "hi");
        String packageName = event.getPackageName().toString();

        if ("com.google.android.youtube".equals(packageName)) {
            int eventType = event.getEventType();
            AccessibilityNodeInfo source = event.getSource();
            Log.d("Demo", "Event Type: " + eventType);

            if (source != null && source.getClassName() != null) {
                String className = source.getClassName().toString();

                if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
                    if (className.contains("EditText") && source.isFocused()) {

                        CharSequence newText = source.getText();
                        String newSearchQuery = newText != null ? newText.toString().toLowerCase() : "";

                        if (!searchQuery.equals(newSearchQuery)) {
                            searchQuery = newSearchQuery; // Store the new search query
                            Log.d("Demo", "search: " + searchQuery);
                        }


                        if (isWholeWordInText(searchQuery, targetWord)) {
                            // Show pop-up alert
                            showSystemWideAlert();
                        }
                    }
                }



                source.recycle();
            }
        }
    }


    private boolean isWholeWordInText(String text, String targetWord) {
        String pattern = "\\b" + targetWord + "\\b";
        return text.matches(".*" + pattern + ".*");
    }



    private void showSystemWideAlert() {
        if (!Settings.canDrawOverlays(this)) {
            // Ask the user to grant the SYSTEM_ALERT_WINDOW permission
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } else {
            // The permission is granted, start the SystemOverlayService
            Intent intent = new Intent(this, SystemOverlayService.class);
            startService(intent);
        }
    }


    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "running_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Close Tab")
                .setContentText("A tab containing the target word was detected. Please close the tab.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
        notificationManager.notify(1, builder.build());
    }


    @Override
    public void onInterrupt() {
    }


}