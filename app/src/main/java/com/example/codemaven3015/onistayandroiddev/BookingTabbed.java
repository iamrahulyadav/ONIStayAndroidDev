package com.example.codemaven3015.onistayandroiddev;

import android.app.TabActivity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

public class BookingTabbed extends TabActivity {
ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_tabbed);
        setBackClickListner();
        TabHost tabHost = getTabHost();
        tabHost.setBackgroundColor(getResources().getColor(R.color.yellow));

        // Tab for Photos
        TabHost.TabSpec photospec = tabHost.newTabSpec("Photos");
        // setting Title and Icon for the Tab
        photospec.setIndicator("Upcoming");
        Intent photosIntent = new Intent(this, Upcoming_Booking.class);
        photospec.setContent(photosIntent);

        // Tab for Songs
        TabHost.TabSpec songspec = tabHost.newTabSpec("Songs");
        songspec.setIndicator("Cancelled");
        Intent songsIntent = new Intent(this, Cancelled_Booking.class);
        songspec.setContent(songsIntent);

        // Tab for Videos
        TabHost.TabSpec videospec = tabHost.newTabSpec("Videos");
        videospec.setIndicator("Completed");
        Intent videosIntent = new Intent(this, Completed_Booking.class);
        videospec.setContent(videosIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(photospec); // Adding photos tab
        tabHost.addTab(songspec); // Adding songs tab
        tabHost.addTab(videospec); // Adding videos tab
    }
    public void setBackClickListner(){
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookingTabbed.this,Home.class);
                startActivity(i);
            }
        });
    }
}