package com.ajit.developerbeetask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            // Notification permission not granted, show dialog to request permission
            showPermissionDialog();
        }

        findViewById(R.id.openSettingsButton).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });
        findViewById(R.id.setTargetWord).setOnClickListener(v -> {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        });


    }

    private void showPermissionDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Permission Required");
        alertDialogBuilder.setMessage("Please grant notification permission to show notifications.");
        alertDialogBuilder.setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Open app settings to allow notification permission
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", null);
        alertDialogBuilder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            // Check again if notification permission was granted after returning from settings
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                showPermissionDialog();
            }
        }
    }
}


