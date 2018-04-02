package com.example.codemaven3015.onistayandroiddev;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;


public class SplashActivity extends AppCompatActivity {
    ImageView imageView,imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);
        TranslateAnimation mAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, -0.9f);
        mAnimation.setFillAfter(true);
        TranslateAnimation mAnimation1 = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.3f);
        mAnimation1.setFillAfter(true);

        mAnimation.setDuration(1500);
        mAnimation1.setDuration(1500);
        imageView.startAnimation(mAnimation);
        imageView1.startAnimation(mAnimation1);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this,Login_page.class));
                SplashActivity.this.finish();
            }



        },3000);}
}