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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private GestureDetector gestureDetector;
    LottieAnimationView animationView;
    LottieAnimationView progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set status bar color if API level is Lollipop or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.buttontext));
        }

        // Initialize UI components
        TextInputLayout promptLayout = findViewById(R.id.promptLayout);
        TextInputEditText promptET = findViewById(R.id.promptET);
        Button generate = findViewById(R.id.generate);
        LinearLayout more = findViewById(R.id.more);
        animationView = findViewById(R.id.animation_view);
        progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        RelativeLayout progressLayout = findViewById(R.id.progressLayout);
        TextView textheading = findViewById(R.id.textheading);

        // GestureDetector for detecting swipe gestures
        gestureDetector = new GestureDetector(this, new GestureListener());

        // Set RecyclerView LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Detect swipe gestures on the main layout
        findViewById(R.id.mainlayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event); // Pass touch event to GestureDetector
            }
        });

        generate.setOnClickListener(view -> {
            if (Objects.requireNonNull(promptET.getText()).toString().isEmpty()) {
                promptLayout.setError("This Field is Required");
            } else {
                animationView.setVisibility(View.VISIBLE);
                animationView.playAnimation();
                textheading.setVisibility(View.GONE); // Hide the heading
                progressLayout.setVisibility(View.VISIBLE); // Show the progress bar and text

                int fixedWidth = 256;
                int fixedHeight = 256;
                int fixedImageCount = 1;

                new ImageGenerator(MainActivity.this).generate(
                        promptET.getText().toString(),
                        fixedWidth,
                        fixedHeight,
                        fixedImageCount,
                        new OnLoaded() {
                            @Override
                            public void loaded(ArrayList<String> arrayList) {
                                animationView.cancelAnimation();
                                animationView.setVisibility(View.GONE);
                                progressLayout.setVisibility(View.GONE); // Hide the progress bar
                                textheading.setVisibility(View.VISIBLE); // Show the heading back

                                if (arrayList == null || arrayList.isEmpty()) {
                                    Toast.makeText(MainActivity.this, "No images generated. Try again!", Toast.LENGTH_SHORT).show();
                                } else {
                                    ImageAdapter adapter = new ImageAdapter(MainActivity.this, arrayList);
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                        }
                );

                // Add a timeout handler to ensure progress stops on failure
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (progressLayout.getVisibility() == View.VISIBLE) {
                        animationView.cancelAnimation();
                        animationView.setVisibility(View.GONE);
                        progressLayout.setVisibility(View.GONE);
                        textheading.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Image generation timed out. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }, 10000); // Set timeout duration (e.g., 10 seconds)
            }
        });


        more.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MoreFeatures.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        });
    }@Override
    protected void onPause() {
        super.onPause();
        if (progressBar.isAnimating()) {
            progressBar.cancelAnimation(); // Stop animation when pausing the activity
        }if (animationView.isAnimating()) {
            animationView.cancelAnimation(); // Stop animation when pausing the activity
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!progressBar.isAnimating()) {
            progressBar.playAnimation(); // Restart animation when resuming
        }  if (!animationView.isAnimating()) {
            animationView.playAnimation(); // Restart animation when resuming
        }
    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true; // Ensures other gestures like onFling are detected
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceY = e1.getY() - e2.getY();
            Log.d("Gesture", "distanceY: " + distanceY + ", velocityY: " + velocityY);

            if (distanceY > 30 && Math.abs(velocityY) > 50) {
                Log.i("Gesture", "Swipe-up gesture detected");
                Intent intent = new Intent(MainActivity.this, MoreFeatures.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
                finish();
                return true;
            }

            Log.d("Gesture", "Gesture did not meet thresholds");
            return false;
        }
    }
}
