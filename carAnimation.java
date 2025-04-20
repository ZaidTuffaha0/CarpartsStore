package com.example.carpartsstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class carAnimation extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_animation);

        ImageView carImage = findViewById(R.id.carImage);
        TranslateAnimation animation = new TranslateAnimation(0, 0, -1000, 0);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        carImage.startAnimation(animation);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(carAnimation.this, Log_in.class));
            finish();
        }, 2500);
    }
}