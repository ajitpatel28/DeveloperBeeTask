package com.ajit.developerbeetask;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class SystemOverlayService extends Service {

    private WindowManager windowManager;
    private View overlayView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showSystemAlert();
    }

    private void showSystemAlert() {
        if (windowManager == null) {
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            LayoutInflater inflater = LayoutInflater.from(this);
            overlayView = inflater.inflate(R.layout.overlay_layout, null);

            windowManager.addView(overlayView, params);
        }


        Button closeButton = overlayView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissSystemAlert();
                redirectToYouTubeHome();
            }
        });
    }

    private void redirectToYouTubeHome() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
        intent.setPackage("com.google.android.youtube");
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // Add this flag
        startActivity(intent);

        Handler handler = new Handler();
        handler.postDelayed(() -> playYouTubeVideoById("jDn2bn7_YSM"), 2000);
    }


    private void playYouTubeVideoById(String videoId) {
        // Create an intent to open the YouTube app's video player for the specific video ID
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId));
        intent.setPackage("com.google.android.youtube");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissSystemAlert();
    }

    private void dismissSystemAlert() {
        if (windowManager != null && overlayView != null) {
            windowManager.removeView(overlayView);
            windowManager = null;
            overlayView = null;
        }
    }
}
