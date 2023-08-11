package com.ajit.developerbeetask;

import android.accessibilityservice.AccessibilityService;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.accessibility.AccessibilityEvent;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyAccessibilityService extends AccessibilityService {

    private boolean isTargetWordDetected = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        String targetWord = prefs.getString(Constants.PREF_SEARCH_WORD, "hi");

        if ("com.android.chrome".contentEquals(event.getPackageName())) {
            if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
                AccessibilityNodeInfo source = event.getSource();
                if (source != null) {
                    CharSequence searchText = source.getText();
                    if (searchText != null) {
                        String searchQuery = searchText.toString().toLowerCase();

                        if (isWholeWordInText(searchQuery, targetWord)) {
                            if (!isTargetWordDetected) {
                                isTargetWordDetected = true;
                                showNotification();

                                Log.e("AccessibilityDemo", "Target word detected, showing notification");
                            }
                        } else {
                            isTargetWordDetected = false;
                        }
                    }
                    source.recycle();
                }
            }
        }
    }

    private boolean isWholeWordInText(String text, String targetWord) {
        String pattern = "\\b" + targetWord + "\\b";
        return text.matches(".*" + pattern + ".*");
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
