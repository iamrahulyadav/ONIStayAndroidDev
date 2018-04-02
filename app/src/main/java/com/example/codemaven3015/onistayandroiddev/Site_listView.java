package com.example.codemaven3015.onistayandroiddev;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Site_listView extends AppCompatActivity {
    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    Card_layout adapter;
    TextView sort_textView ,filter_textView,headerText;
    ListApdapter listApdapter;
    LinearLayout backApp_Bar,appmenuLL;
    Button EditBack_btn;
    ImageButton backButton;


// HARPREET

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headerText = findViewById(R.id.headerText);
        headerText.setText("Select Property");
        appmenuLL = findViewById(R.id.appmenuLL);
        backApp_Bar = findViewById(R.id.backApp_Bar);
        appmenuLL.setVisibility(View.GONE);
        backApp_Bar.setVisibility(View.VISIBLE);
        EditBack_btn = findViewById(R.id.EditBack_btn);
        EditBack_btn.setVisibility(View.INVISIBLE);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Site_listView.this,Home.class);
                startActivity(i);
            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        sort_textView = findViewById(R.id.sort_textView);

        //for filter click
        filter_textView = findViewById(R.id.filter_textView);
        filter_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Site_listView.this, FilterActivity.class);
                startActivity(i);
            }
        });

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        int[] images = {
                R.drawable.hotel1,
                R.drawable.hotel2,
                R.drawable.hotel3,
                R.drawable.hotel4,
                R.drawable.hotel5,
                R.drawable.hotel6
        };
         String[] price = {"₹ 29999", "₹ 29999", "₹ 29999", "₹ 29999", "₹ 29999", "₹ 29999"};
         String[] hotel = {"Chapter One",
                "Chapter Two",
                "Chapter Three",
                "Chapter Four",
                "Chapter Five",
                "Chapter Six"};
        JSONArray list = new JSONArray();
        for(int i = 0;i<6;i++){
            JSONObject obj = new JSONObject();
            try {
                obj.put("image",images[i]);
                obj.put("price",price[i]);
                obj.put("hotel",hotel[i]);
                list.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        adapter=new Card_layout(this,"SiteList",list);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels,"list"));
        recyclerView.setAdapter(adapter);
        sort_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet();
            }
        });
    }
    public void bottomSheet(){
        new BottomSheet.Builder(this).title("Sort By").sheet(R.menu.sort).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.invite:
                        Toast.makeText(getApplicationContext(),"jhgdjk",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }).show();

    }

}
