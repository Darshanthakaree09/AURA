package com.example.aura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class GetStarted extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private Button backBtn, nextBtn, skipBtn;
    private LottieAnimationView ellipse1, ellipse2;
    private TextView[] dots;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        // Disable night mode and set fullscreen
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Set status bar color if API level is Lollipop or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.background));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(0);
        }


        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize views
        backBtn = findViewById(R.id.backbtn);
        nextBtn = findViewById(R.id.nextbtn);
        skipBtn = findViewById(R.id.skipButton);
        ellipse1 = findViewById(R.id.ellipse1);
        ellipse2 = findViewById(R.id.ellipse2);
        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.indicator_layout);

        // Setup ViewPager
        viewPagerAdapter = new ViewPagerAdapter(this);
        mSlideViewPager.setAdapter(viewPagerAdapter);

        // Dots indicator
        setUpIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        // Button listeners
        backBtn.setOnClickListener(v -> {
            if (getItem(0) > 0) {
                mSlideViewPager.setCurrentItem(getItem(-1), true);
            }
        });

        nextBtn.setOnClickListener(v -> {
            if (getItem(0) < viewPagerAdapter.getCount() - 1) {
                mSlideViewPager.setCurrentItem(getItem(1), true);
            } else {
                // Move to MainActivity on final page
                startActivity(new Intent(GetStarted.this, MainActivity.class));
                finish();
            }
        });

        skipBtn.setOnClickListener(v -> {
            // Skip to MainActivity
            startActivity(new Intent(GetStarted.this, MainActivity.class));
            finish();
        });
    }

    // Setup dots indicator
    private void setUpIndicator(int position) {
        dots = new TextView[viewPagerAdapter.getCount()];
        mDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive, getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.active, getApplicationContext().getTheme()));
        }
    }

    // ViewPager page change listener
    private final ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);

            // Toggle visibility of back button and ellipses
            backBtn.setVisibility(position > 0 ? View.VISIBLE : View.INVISIBLE);
            ellipse1.setVisibility(position > 0 ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    // Helper to calculate next item position
    private int getItem(int i) {
        return mSlideViewPager.getCurrentItem() + i;
    }
}
