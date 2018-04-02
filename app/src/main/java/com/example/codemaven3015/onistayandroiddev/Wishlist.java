package com.example.codemaven3015.onistayandroiddev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Wishlist extends AppCompatActivity {
    RecyclerView Wishlist_recycler_view;
    Card_layout adapter;
    Button wish_butn;
    LinearLayoutManager layoutManager;
    LinearLayout backApp_Bar,appmenuLL;
    Button EditBack_btn;
    ImageButton backButton;
    TextView headerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headerText = findViewById(R.id.headerText);
        headerText.setText("Wishlist");
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
                Intent i = new Intent(Wishlist.this,Home.class);
                startActivity(i);
            }
        });
        wish_butn=findViewById(R.id.wish_butn);
        setServicesimages();
    }
    public void setServicesimages(){
        Wishlist_recycler_view = findViewById(R.id.Wishlist_recycler_view);
        Wishlist_recycler_view.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        Wishlist_recycler_view.setLayoutManager(layoutManager);

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
        adapter=new Card_layout(this,"Wishlist",list);
        Wishlist_recycler_view.setItemAnimator(new DefaultItemAnimator());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        Wishlist_recycler_view.addItemDecoration(new SpacesItemDecoration(spacingInPixels,"list"));
        Wishlist_recycler_view.setAdapter(adapter);
    }
}
