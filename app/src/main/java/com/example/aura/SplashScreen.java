package com.example.aura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, GetStarted.class);
                startActivity(intent);

                // Apply fade-in and fade-out animations
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // Fade-in for next activity, fade-out for the current one

                // Delay finish to give time for the animation to complete
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish(); // Finish the SplashScreen activity after the animation delay
                    }
                }, 500); // Adjust delay (in milliseconds) to match the duration of your fade-out animation
            }

        }, 3000);

    }
}