package com.example.codemaven3015.onistayandroiddev;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class AllLocationsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    GridView allLocationGridView;
    ListApdapter listApdapter;
    //AppBarLayout toolbar_layout;
    RelativeLayout appmenuLL;
    DrawerLayout drawer;
    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar_layout = findViewById(R.id.app_bar);
        //toolbar.setTitle("Select Location");
        appmenuLL = findViewById(R.id.appmenuLL);
        setSupportActionBar(toolbar);
        imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllLocationsActivity.this,Home.class);
                startActivity(i);
            }
        });
//        toolbar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
//                {
//                    //  Collapsed
//                    appmenuLL.setVisibility(View.VISIBLE);
//
//
//                }
//                else
//                {
//                    //Expanded
//                    appmenuLL.setVisibility(View.GONE);
//
//
//                }
//            }
//        });
        allLocationGridView = (GridView)findViewById(R.id.allLocationGridView);
        allLocationGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(AllLocationsActivity.this,Site_listView.class);
                startActivity(i);
            }
        });
        ArrayList<String> list = new ArrayList<String>();
        list.add("Mandi House");
        list.add("Yamuna Bank");
        list.add("Rajeev Chowk");
        list.add("Janakpuri");
        list.add("Kashmiri Gate");
        list.add("Mandi House");
        list.add("Yamuna Bank");
        list.add("Rajeev Chowk");
        list.add("Janakpuri");
        list.add("Kashmiri Gate");
        list.add("Mandi House");
        list.add("Yamuna Bank");
        list.add("Rajeev Chowk");
        list.add("Janakpuri");
        list.add("Kashmiri Gate");
        list.add("Mandi House");
        list.add("Yamuna Bank");
        list.add("Rajeev Chowk");
        list.add("Janakpuri");
        list.add("Kashmiri Gate");
        int images[] = {R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location,
                R.drawable.location};
        listApdapter = new ListApdapter(list,this,images,"getStarted");
        allLocationGridView.setAdapter(listApdapter);
        ;
        ImageButton menuBar_imgBtn = (ImageButton) findViewById(R.id.menuRight);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_close,R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();
        menuBar_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {

            // Handle the camera action


        } else if (id == R.id.booking) {



        } else if (id == R.id.invite) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}
