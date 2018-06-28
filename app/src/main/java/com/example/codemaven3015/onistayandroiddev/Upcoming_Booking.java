package com.example.codemaven3015.onistayandroiddev;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.cocosw.bottomsheet.BottomSheet;

import org.json.JSONArray;

import java.util.ArrayList;

public class Upcoming_Booking extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Tabbed_CardLayout adapter;
    ListApdapter listApdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming__booking);

        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        setUpcomming();
    }

    private void setUpcomming()
    {
        String url="http://onistays.com/oni-endpoint/my-bookings-service";
        VolleyAPICall volleyAPICallJsonObject=new VolleyAPICall(this,url);
        volleyAPICallJsonObject.executeRequest(Request.Method.GET, new VolleyAPICall.VolleyCallback() {
            @Override
            public void getResponse(JSONArray response) {
                if (response != null) {
                    if (response.length() > 0) {
                        Log.e("Success", "Response" + response.toString());
                        adapter = new Tabbed_CardLayout(getApplicationContext(),"Upcoming",response);
                        recyclerView.setAdapter(adapter);

                    }else {
                        showMessage("Info","Sorry!! There is no Upcomming Booking");

                    }
                }
            }


        });
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        //builder.set
        builder.setMessage(message);
        //builder.show();
        AlertDialog dialog1 = builder.create();
        dialog1.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Window view = ((AlertDialog)dialog).getWindow();
                view.setBackgroundDrawableResource(R.color.white);
            }
        });
        dialog1.show();
    }
}
