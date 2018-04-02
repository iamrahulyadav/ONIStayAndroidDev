package com.example.codemaven3015.onistayandroiddev;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Site_Image_View extends AppCompatActivity {
    android.support.v4.view.ViewPager ViewPager;
    LinearLayout SliderDots;
    private int dotsCount;
    private ImageView[]dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site__image__view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayout appmenuLL = findViewById(R.id.appmenuLL);
        LinearLayout appmenuLL_Back = findViewById(R.id.appmenuLL_Back);
        ImageButton closeButton = findViewById(R.id.menuBar_imgBtn_back);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClose();
            }
        });
        appmenuLL.setVisibility(View.GONE);
        appmenuLL_Back.setVisibility(View.VISIBLE);
        ViewPager=findViewById(R.id.viewPagerImageDetails);
        SliderDots=findViewById(R.id.SliderDotes);

        Product_Details__ViewPagerAdapter productDetailsViewPagerAdapter =new Product_Details__ViewPagerAdapter(this);
        ViewPager.setAdapter(productDetailsViewPagerAdapter);
        dotsCount= productDetailsViewPagerAdapter.getCount();
        final ImageView[]dots = new ImageView[dotsCount];

        for(int i=0;i<dotsCount;i++){
            dots[i]=new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            SliderDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot));
        ViewPager.addOnPageChangeListener(new android.support.v4.view.ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotsCount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    public void onClickClose(){
        super.onBackPressed();
    }

}
