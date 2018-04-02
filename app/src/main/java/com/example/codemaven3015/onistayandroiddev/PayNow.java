package com.example.codemaven3015.onistayandroiddev;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

public class PayNow extends AppCompatActivity {
    LinearLayout backApp_Bar,appmenuLL;
    Button EditBack_btn,payNowButton,cancelButton;
    ImageButton backButton;
    TextView headerText,oder_id,checkIn_Date,checkOut_Date,booking_date,stay_period,
            total_amount,direction,call_property;
    ImageView map,call,room_image;
    BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headerText = findViewById(R.id.headerText);
        headerText.setText("Confirmation");
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
                Intent i = new Intent(PayNow.this,Product_Image_page.class);
                startActivity(i);
            }
        });
        payNowButton = findViewById(R.id.payNowButton);
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PayNow.this,PayCard.class);
                startActivity(i);
            }
        });
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCancelPopUp();
            }
        });
        textView();
        imageView();
    }

    private void imageView() {

        map=findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        call=findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        room_image=findViewById(R.id.room_image);
        room_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void textView() {
        oder_id=findViewById(R.id.oder_id);
        oder_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        checkIn_Date=findViewById(R.id.checkIn_Date);
        checkIn_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        checkOut_Date=findViewById(R.id.checkOut_Date);
        checkOut_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        booking_date=findViewById(R.id.booking_date);
        booking_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        stay_period=findViewById(R.id.stay_period);
        stay_period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        total_amount=findViewById(R.id.total_amount);
        total_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        direction=findViewById(R.id.direction);
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        call_property=findViewById(R.id.call_property);
        call_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    public void openCancelPopUp(){
        dialog = new BottomSheetDialog(PayNow.this);
        dialog.setContentView(R.layout.cancel_booking);
        dialog.show();
    }
    public void onCancel(View v){
        Intent i = new Intent(PayNow.this,Cancled.class);
        startActivity(i);
    }
    public void onCrossClick(View v){
        if(dialog != null){
            dialog.hide();
        }
    }

}
