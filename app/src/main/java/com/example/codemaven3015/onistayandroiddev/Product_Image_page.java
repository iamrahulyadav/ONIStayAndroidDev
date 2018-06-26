package com.example.codemaven3015.onistayandroiddev;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import in.shadowfax.proswipebutton.ProSwipeButton;

public class Product_Image_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Harpreet
    android.support.v4.view.ViewPager ViewPager;
    ArrayList<String> arrayImage = new ArrayList<String>();
    Map<String, String> header;
    boolean isFavourite = false;
    LinearLayout backApp_Bar,appmenuLL;
    Button EditBack_btn,bookNowButton,buttonOccupany1;
    ImageButton backButton,show_imageBtn;
    TextView headerText;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;
    LinearLayout SliderDots;
    private int dotsCount;
    private ImageView[]dots;
    private ImageButton getDropIn_imgBtn,rating_imgbtn;
    private TextView getDropIn_textView,getMonth_textView,getDropOut_textView,hotel_textView,droppedPrice,address_textView,youSaved,amountPrice;
    String[] country = { "Select","Month", "3 Months", "6 Months", "9 Months", "12 Months"};
    private Spinner getMonth_Spinner,occupancy,bed;
    String nid = "", booked_rooms,total_rooms,no_of_rooms,dateIn,dateOut,months;
    int empty_rooms=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__image_page);
        bed=findViewById(R.id.bed);
        getMonth_Spinner  = (Spinner) findViewById(R.id.getMonth_Spinner);
        getMonth_textView=findViewById(R.id.getMonth_textView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headerText = findViewById(R.id.headerText);
        headerText.setText("Book Now");
        appmenuLL = findViewById(R.id.appmenuLL);
        backApp_Bar = findViewById(R.id.backApp_Bar);
        appmenuLL.setVisibility(View.GONE);
        backApp_Bar.setVisibility(View.VISIBLE);
        EditBack_btn = findViewById(R.id.EditBack_btn);
        EditBack_btn.setVisibility(View.INVISIBLE);
        backButton = findViewById(R.id.backButton);
        hotel_textView=findViewById(R.id.hotel_textView);
        address_textView=findViewById(R.id.address_textView);
        amountPrice=findViewById(R.id.amountPrice);
        youSaved=findViewById(R.id.youSaved);
        droppedPrice=findViewById(R.id.droppedPrice);
        // FOR PAGER VIEW
        ViewPager=findViewById(R.id.ViewPager);
        SliderDots=findViewById(R.id.SliderDotes);

        bookNowButton = findViewById(R.id.bookNowButton);
        // FOR RATING BUTTON
        rating_imgbtn = findViewById(R.id.rating_imgbtn);




        setDataToDetailPage();
        setFavButton();
        setOnclickBookNowButton();

        setOnClickbuttonOccupany();
        onSwipeBookNowClick();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_Image_page.this,Site_listView.class);
                startActivity(i);
            }
        });


//for STAY DURATION
        setDurationSpinner();

        rating_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingView(v);
            }
        });


// FOR SET THE CALENDER
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        getDropIn_textView=findViewById(R.id.getDropIn_textView);
        getMonth_textView=findViewById(R.id.getMonth_textView);
        getDropOut_textView=findViewById(R.id.getDropOut_textView);
        getDropIn_imgBtn=findViewById(R.id.getDropIn_imgBtn);
        //updateDisplayOut();
        updateDisplay();


        ////Spinner for occupancy
        occupancy=findViewById(R.id.occupancy);




        getDropIn_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

    }
//    final String months=getMonth_textView.getText().toString();
//    final String no_of_rooms = bed.getSelectedItem().toString();
//    final String dateIn = getDropIn_textView.getText().toString();
//    final String dateOut = getDropOut_textView.getText().toString();


    private void setOnclickBookNowButton() {
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
              months=getMonth_textView.getText().toString();
                no_of_rooms = bed.getSelectedItem().toString();
                dateIn = getDropIn_textView.getText().toString();
                dateOut = getDropOut_textView.getText().toString();
                if(dateIn.equals("")){
                    showMessage("Error","Please select check-in date");
                }else if(dateOut.equals("")){
                    showMessage("Error","Please select check-out date");
                }else {
                    header = new HashMap<>();
                    header.put("nid",nid);
                    header.put("no_of_rooms",no_of_rooms);
                    header.put("bookingDate",dateIn);
                    header.put("bookingEndDate",dateOut);
                    header.put("confirm","1");

                confirmBookingApi();
//                Intent intent=new Intent(getApplicationContext(),PayNow.class);
//                intent.putExtra("NID",nid);
            }
            }

        });
    }

    public void setPager(ArrayList<String> arrayImage){
        Product_Details__ViewPagerAdapter productDetailsViewPagerAdapter =new Product_Details__ViewPagerAdapter(this,arrayImage);
        ViewPager.setAdapter(productDetailsViewPagerAdapter);
        dotsCount= productDetailsViewPagerAdapter.getCount();
        final ImageView[]dots = new ImageView[dotsCount];

        for(int i=0;i<dotsCount;i++){
            dots[i]=new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            SliderDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot));
        ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new myTimerTask(), 4000 ,4000);
    }

    private void setDataToDetailPage() {
        String details;
        details = getIntent().getStringExtra("Details");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(details);
            nid=jsonObject.getString("Nid");
            total_rooms=jsonObject.getString("Total Number of Rooms");
            booked_rooms=jsonObject.getString("Booked Rooms");
            int t_room=0,b_room=0;
            try {
                t_room = Integer.parseInt(total_rooms);
                b_room = Integer.parseInt(booked_rooms);

            } catch(NumberFormatException nfe) {
                // Handle parse error.
            }
            empty_rooms=t_room-b_room;
            setDropDownRoom(empty_rooms);
            hotel_textView.setText(jsonObject.getString("title"));
            address_textView.setText(jsonObject.getString("Address"));
            amountPrice.setText(jsonObject.getString("Currency Symbol") + jsonObject.getString("Price"));
            droppedPrice.setText("Priced Deopped " + jsonObject.getString("Discount") + "% Off");
            int myNum = 0;
            int price = 0;

            try {
                myNum = Integer.parseInt(jsonObject.getString("Discount"));
                price = Integer.parseInt(jsonObject.getString("Price"));
                price = (price * 100) / (myNum);
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            youSaved.setText("You Saved " + jsonObject.getString("Currency Symbol") + price);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray imageArray = new JSONArray();
        JSONArray serviceArray = new JSONArray();
        try {
            imageArray = jsonObject.getJSONArray("Gallery Images");
            serviceArray = jsonObject.getJSONArray("Services");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (imageArray != null) {
            for (int i = 0; i < imageArray.length(); i++) {
                try {
                    arrayImage.add(imageArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            setPager(arrayImage);
            setOnEyeClick();
            try {
                setServicesData(serviceArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




    private void setDropDownRoom(int empty_rooms)
    {
        Integer[] items = new Integer[empty_rooms];
        for(int i=1;i<=empty_rooms;i++)
        {
            items[i-1]=i;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        bed=findViewById(R.id.bed);
        bed.setAdapter(adapter);
        String no_of_rooms = bed.getSelectedItem().toString();
    }




    private void setServicesData(JSONArray serviceArray) throws JSONException {
        for(int i = 0; i< serviceArray.length();i++){
            LinearLayout linearLayout;
            switch(serviceArray.getString(i)){

                case "ONI Breakfast":
                    linearLayout = findViewById(R.id.Breakfast);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;
                case "AC":
                    linearLayout = findViewById(R.id.ac);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;

                case "WATER":
                    linearLayout = findViewById(R.id.water);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;

                case "Free WIFI":
                    linearLayout = findViewById(R.id.wifi);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;

                case "Geyser":
                    linearLayout = findViewById(R.id.Geyser);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;
                case "Parking Facility":
                    linearLayout = findViewById(R.id.parking);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;

                case "Power backup":
                    linearLayout = findViewById(R.id.power);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;

                case "Complementary Refreshment":
                    linearLayout = findViewById(R.id.Refreshment);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;

                case "Lift/Elevator":
                    linearLayout = findViewById(R.id.lift);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;

                case "Seating Area":
                    linearLayout = findViewById(R.id.seating_area);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;


            }
        }
    }








    public void setDurationSpinner(){

        getMonth_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
               // showMessage showAlertMessage;
                if (getDropIn_textView.getText().toString().isEmpty()) {
                   // showMessage("Info","Please select DropIn Date");
                } else {
                    if (text.toLowerCase().equals("select")) {
                      //  showMessage("info","Please select duration");
                    }else {
                        switch (position){
                            //to update outTime
                            case 1:
                                if(mMonth >10){
                                    updateDisplayOut(0,mYear+1);
                                }else {
                                    updateDisplayOut(mMonth+1,mYear);
                                }

                                break;
                            case 2:
                                if(mMonth >8){
                                    updateDisplayOut(11 - (mMonth +3),mYear+1);
                                }else {
                                    updateDisplayOut((mMonth +3),mYear);
                                }

                                break;
                            case 3:
                                if(mMonth >5){

                                    updateDisplayOut(11 - (mMonth +6),mYear+1);
                                }else {
                                    updateDisplayOut((mMonth +6),mYear);
                                }
                                break;
                            case 4:
                                if(mMonth >2){
                                    updateDisplayOut((11 - (mMonth +9)),mYear+1);
                                }else {
                                    updateDisplayOut((mMonth +9),mYear);
                                }
                                break;
                            case 5:
                                updateDisplayOut((mMonth),mYear+1);

                                break;

                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getMonth_Spinner.setAdapter(aa);
    }






    private void setFavButton()
    {
        final ImageButton fav_imageBtn =(ImageButton)findViewById(R.id.fav_imageBtn);
        fav_imageBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // = readState();

                if (!isFavourite) {
                    fav_imageBtn.setBackgroundResource(R.drawable.heart);
                    isFavourite = true;
                    //saveState(isFavourite);

                } else {
                    fav_imageBtn.setBackgroundResource(R.drawable.heart2);
                    isFavourite = false;
                    //saveState(isFavourite);

                }

            }


        });

    }






    public void onSwipeBookNowClick(){
        final ProSwipeButton proSwipeBtn = (ProSwipeButton) findViewById(R.id.awesome_btn);
        proSwipeBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                // user has swiped the btn. Perform your async operation now
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // task success! show TICK icon in ProSwipeButton
                        proSwipeBtn.showResultIcon(true);
                        Intent i = new Intent(Product_Image_page.this,PayNow.class);
                        startActivity(i);// false if task failed
                        //proSwipeBtn.showResultIcon(true); //if task succeeds
                        //proSwipeBtn.showResultIcon(false); //if task fails
                    }
                }, 2000);
            }
        });
    }




    public void setOnClickbuttonOccupany(){
        buttonOccupany1 = findViewById(R.id.doubleButton);
        buttonOccupany1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.gridview_layout,null);
                final PopupWindow popup = new PopupWindow(getApplicationContext());
                popup.setContentView(layout);
                popup.setFocusable(true);
                popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                popup.showAsDropDown(v);
            }
        });
    }



    public void setOnEyeClick(){
        show_imageBtn = findViewById(R.id.show_imageBtn);
        show_imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToView();
            }
        });
    }



public void navigateToView(){
    Intent i = new Intent(Product_Image_page.this,Site_Image_View.class);
    i.putExtra("IN_VIEW_IMAGE","true");
    i.putStringArrayListExtra("images", arrayImage);
    this.startActivity(i);
}



    private void ratingView(View v) {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.rating_box,null);
        final PopupWindow popup = new PopupWindow(this);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popup.showAsDropDown(v);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public class myTimerTask extends TimerTask {
        @Override
        public void run() {

            Product_Image_page.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(ViewPager.getCurrentItem() == 0){
                        ViewPager.setCurrentItem(1);
                    } else if(ViewPager.getCurrentItem() == 1){
                        ViewPager.setCurrentItem(2);
                    } else
                    {
                        ViewPager.setCurrentItem(0);
                    }

                }
            });
        }
    }
    @Override
    public Dialog onCreateDialog ( int id){
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }


    private void updateDisplayOut(int month, int year) {
        getDropOut_textView.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-")
                        .append(month + 1).append("-")
                        .append(year).append(" "));
    }


    // updates the date we display in the TextView
    private void updateDisplay() {
        getDropIn_textView.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mDay).append(" "));

    }

    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                    //updateDisplayOut();
                }
            };



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

    private void confirmBookingApi()
    {
        String url="http://onistays.com/oni-endpoint/booking_home";

        VolleyAPICallJsonObject volleyAPICallJsonObject=new VolleyAPICallJsonObject(this,url,header);
        volleyAPICallJsonObject.executeRequest(Request.Method.POST, new VolleyAPICallJsonObject.VolleyCallback() {
            @Override
            public void getResponse(JSONObject response)
            {
                Log.e("Success", "getResponse: "+response.toString() );
                try {
                    String scalar = response.getString("scalar");
                    if(scalar.equals("Room(s) are available.")){
                        Intent intent= new Intent(getApplicationContext(),PayNow.class);
                        intent.putExtra("NID",nid);
                        intent.putExtra("DateIn",dateIn);
                        intent.putExtra("DateOut",dateOut);
                        intent.putExtra("No_Of_Room",no_of_rooms);
                        intent.putExtra("Total_Amount", amountPrice.getText());
                        intent.putExtra("Stay_Period",months);
                        intent.putExtra("Address", address_textView.getText());
                        intent.putExtra("Hotel_Name", hotel_textView.getText());
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


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

