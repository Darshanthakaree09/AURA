package com.example.aura;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextInputLayout promptLayout = findViewById(R.id.promptLayout);
        TextInputEditText promptET = findViewById(R.id.promptET);

        Button generate = findViewById(R.id.generate);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LottieAnimationView animationView = findViewById(R.id.animation_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Generating...");

        generate.setOnClickListener(view -> {
            if (Objects.requireNonNull(promptET.getText()).toString().isEmpty()) {
                promptLayout.setError("This Field is Required");
            } else {
                progressDialog.show();
                animationView.setVisibility(View.VISIBLE);
                animationView.playAnimation();

                // Fixed dimensions for width and height (e.g., 256)
                int fixedWidth = 256;
                int fixedHeight = 256;
                int fixedImageCount = 1; // Always generate 1 image

                new ImageGenerator(MainActivity.this).generate(
                        promptET.getText().toString(),
                        fixedWidth,
                        fixedHeight,
                        fixedImageCount,
                        new OnLoaded() {
                            @Override
                            public void loaded(ArrayList<String> arrayList) {
                                progressDialog.dismiss();
                                animationView.cancelAnimation();
                                animationView.setVisibility(View.GONE);

                                if (arrayList == null || arrayList.isEmpty()) {
                                    Toast.makeText(MainActivity.this, "No images generated. Try again!", Toast.LENGTH_SHORT).show();
                                } else {
                                    ImageAdapter adapter = new ImageAdapter(MainActivity.this, arrayList);
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                        }
                );
            }
        });
    }
}
