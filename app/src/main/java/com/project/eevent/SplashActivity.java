package com.project.eevent;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity {
    int splashTimeOut = 3000;            //splash window time in mini secound
    GifImageView gifImageView;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        gifImageView = (GifImageView) findViewById(R.id.gf);
        txtTitle = findViewById(R.id.subTitle);
        //Typeface tf = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        // Typeface tf = Typeface.createFromAsset(getAssets(), "AsapCondensed-Regular.ttf");
        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");
        //txtTitle.setTypeface(tf);
        txtTitle.setTypeface(tf);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, splashTimeOut);
    }
}