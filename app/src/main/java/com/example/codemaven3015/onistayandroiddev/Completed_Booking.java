package com.example.codemaven3015.onistayandroiddev;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;

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


                    }else {
                        showMessage("Info","Sorry!! There is no Completed Booking");

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
