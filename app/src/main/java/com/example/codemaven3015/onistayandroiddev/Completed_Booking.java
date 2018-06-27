package com.example.codemaven3015.onistayandroiddev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;

import org.json.JSONArray;

public class Completed_Booking extends AppCompatActivity {
    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    Tabbed_CardLayout adapter;
    ListApdapter listApdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled__booking);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        setCompleted();
    }

    private void setCompleted()
    {
        String url="http://onistays.com/oni-endpoint/my-past-bookings-service";
        VolleyAPICall volleyAPICallJsonObject=new VolleyAPICall(this,url);
        volleyAPICallJsonObject.executeRequest(Request.Method.GET, new VolleyAPICall.VolleyCallback() {
            @Override
            public void getResponse(JSONArray response) {
                if (response != null) {
                    if (response.length() > 0) {
                        Log.e("Success", "Response" + response.toString());
                        adapter = new Tabbed_CardLayout(getApplicationContext(),"Complete",response);
                        recyclerView.setAdapter(adapter);


                    }
                }
            }


        });
    }
}
