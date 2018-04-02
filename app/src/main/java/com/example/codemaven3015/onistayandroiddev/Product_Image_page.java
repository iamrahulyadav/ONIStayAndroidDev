package com.example.codemaven3015.onistayandroiddev;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import in.shadowfax.proswipebutton.ProSwipeButton;

public class Product_Image_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Harpreet
    android.support.v4.view.ViewPager ViewPager;
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
    private TextView getDropIn_textView,getMonth_textView,getDropOut_textView;
    String[] country = { "Select","Month", "3 Months", "6 Months", "9 Months", "12 Months"};
    private Spinner getMonth_Spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__image_page);
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
        setFavButton();
        setOnEyeClick();
        setOnClickbuttonOccupany();
        onSwipeBookNowClick();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_Image_page.this,Site_listView.class);
                startActivity(i);
            }
        });
        bookNowButton = findViewById(R.id.bookNowButton);
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_Image_page.this,PayNow.class);
                startActivity(i);
                //viewDialogue();
            }
        });

//for STAY DURATION
        getMonth_Spinner  = (Spinner) findViewById(R.id.getMonth_Spinner);
        getMonth_Spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getMonth_Spinner.setAdapter(aa);
// FOR RATING BUTTON
        rating_imgbtn = findViewById(R.id.rating_imgbtn);
        rating_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingView(v);
            }
        });

// FOR PAGER VIEW
        ViewPager=findViewById(R.id.ViewPager);
        SliderDots=findViewById(R.id.SliderDotes);
// FOR SET THE CALENDER
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        getDropIn_textView=findViewById(R.id.getDropIn_textView);
        getMonth_textView=findViewById(R.id.getMonth_textView);
        getDropOut_textView=findViewById(R.id.getDropOut_textView);
        getDropIn_imgBtn=findViewById(R.id.getDropIn_imgBtn);
        updateDisplayOut();
        updateDisplay();
        getDropIn_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        Product_Details__ViewPagerAdapter productDetailsViewPagerAdapter =new Product_Details__ViewPagerAdapter(this);
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
    private void updateDisplayOut() {
        getDropOut_textView.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));
    }


    // updates the date we display in the TextView
    private void updateDisplay() {
        getDropIn_textView.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));
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
                    updateDisplayOut();
                }
            };


}

