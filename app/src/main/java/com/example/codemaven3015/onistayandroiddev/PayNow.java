package com.example.codemaven3015.onistayandroiddev;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class PayNow extends AppCompatActivity {
    LinearLayout backApp_Bar,appmenuLL;
    Map<String, String> header;
    Button EditBack_btn,payNowButton,cancelButton;
    ImageButton backButton;
    TextView headerText,oder_id,checkIn_Date,checkOut_Date,booking_date,stay_period,
            total_amount,direction,call_property,textView9,textView10;
    ImageView map,call,room_image;
    BottomSheetDialog dialog;
    String nid,no_of_rooms,dateIn,dateOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView9=findViewById(R.id.textView9);
        textView10=findViewById(R.id.textView10);

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



                setPayButton();
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
        setDataConfirmation();
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

    private void setDataConfirmation()

    {
        Intent intent = getIntent();
         nid=intent.getStringExtra("NID");
         dateIn=intent.getStringExtra("DateIn");
         dateOut=intent.getStringExtra("DateOut");
        String amountPrice=intent.getStringExtra("Total_Amount");
        String months=intent.getStringExtra("Stay_Period");
        String address_textView=intent.getStringExtra("Address");
        String hotel_textView=intent.getStringExtra("Hotel_Name");
         no_of_rooms=intent.getStringExtra("No_Of_Room");

        oder_id.setText(nid);
        textView9.setText(hotel_textView);
        textView10.setText(address_textView);
        checkIn_Date.setText(dateIn);
        checkOut_Date.setText(dateOut);
        stay_period.setText(months);
        total_amount.setText(amountPrice);

    }
    private void setPayButton()
    {
        header = new HashMap<>();
        header.put("nid",nid);
        header.put("no_of_rooms",no_of_rooms);
        header.put("bookingDate",dateIn);
        header.put("bookingEndDate",dateOut);
        //header.put("confirm","0");

        String url="http://onistays.com/oni-endpoint/booking_home";

        VolleyAPICallJsonObject volleyAPICallJsonObject=new VolleyAPICallJsonObject(this,url,header);
        volleyAPICallJsonObject.executeRequest(Request.Method.POST, new VolleyAPICallJsonObject.VolleyCallback() {
            @Override
            public void getResponse(JSONObject response)
            {
                Log.e("Success", "getResponse: "+response.toString() );
//              try {
//                    String scalar = response.getString("scalar");
//                    if(scalar.equals("Room(s) are available.")){
                        Intent intent= new Intent(getApplicationContext(),PayCard.class);
                        startActivity(intent);

//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


//
            }

            @Override
            public void getError(VolleyError error)
            {

                Log.e("error",error.toString());
            }

        });
    }

}
