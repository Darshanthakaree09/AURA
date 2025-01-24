package com.example.aura;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MoreFeatures extends AppCompatActivity {
    private GestureDetector gestureDetector;

    @Override
    public void onBackPressed() {
        // Use Intent to navigate back to MainActivity with animation
        Intent intent = new Intent(MoreFeatures.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_down, R.anim.no_animation); // Apply transition animation
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_features);

        // Set status bar color and style
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.buttontext));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(0);
        }

        // Initialize GestureDetector for swipe gestures
        gestureDetector = new GestureDetector(this, new GestureListener());

        // Set onTouchListener to detect gestures on the root layout
        findViewById(R.id.more_features_layout).setOnTouchListener((v, event) ->
                gestureDetector.onTouchEvent(event)
        );

        // Initialize Back Button
        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> onBackPressed());

        // Initialize feature cards
        CardView cardLanguage = findViewById(R.id.card_language_translator);
        CardView cardImageToText = findViewById(R.id.card_image_to_text);

        // Uncomment these when target activities are implemented
        cardLanguage.setOnClickListener(v -> {
            Intent intent = new Intent(MoreFeatures.this, LanguageTranslatorActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        });
//
//        cardImageToText.setOnClickListener(v -> {
//            Intent intent = new Intent(MoreFeatures.this, ImageToTextActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
//        });
    }

    // GestureListener class to handle swipe gestures
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true; // Ensures other gestures like onFling are detected
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceY = e2.getY() - e1.getY(); // Detect vertical movement
            Log.d("Gesture", "distanceY: " + distanceY + ", velocityY: " + velocityY);

            if (distanceY > 50 && Math.abs(velocityY) > 50) { // Swipe-down threshold
                Log.i("Gesture", "Swipe-down gesture detected");
                onBackPressed(); // Navigate back to MainActivity
                return true;
            }

            Log.d("Gesture", "Gesture did not meet thresholds");
            return false;
        }
    }
}
