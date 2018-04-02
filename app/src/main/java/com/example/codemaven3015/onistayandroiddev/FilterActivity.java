package com.example.codemaven3015.onistayandroiddev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;


public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    //RangeSeekBar rangeBar;
    TextView min,max;
    ImageButton cross_imageButton;

 RadioButton male_radioBtn ,female_radioBtn,student_radioBtn,workingProff_radioBtn;
 Boolean radioPressed = false;
 CheckBox wifi_CheckBox,power_CheckBox,laundry_CheckBox,refrigrator_CheckBox,
         Parking_CheckBox,Cctv_CheckBox,monthly_CheckBox,yearly_CheckBox,
         Quarterly_CheckBox,offices_CheckBox,colleges_CheckBox,workingPlaces_CheckBox,
         single_CheckBox,Double_CheckBox,Triple_CheckBox,Quadraple_CheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        wifi_CheckBox=findViewById(R.id.wifi_CheckBox);
        wifi_CheckBox.setOnClickListener(this);
        power_CheckBox=findViewById(R.id.power_CheckBox);
        power_CheckBox.setOnClickListener(this);
        laundry_CheckBox=findViewById(R.id.laundry_CheckBox);
        laundry_CheckBox.setOnClickListener(this);
        Cctv_CheckBox=findViewById(R.id.Cctv_CheckBox);
        Cctv_CheckBox.setOnClickListener(this);
        Parking_CheckBox=findViewById(R.id.Parking_CheckBox);
        Parking_CheckBox.setOnClickListener(this);
        monthly_CheckBox=findViewById(R.id.monthly_CheckBox);
        monthly_CheckBox.setOnClickListener(this);
        yearly_CheckBox=findViewById(R.id.yearly_CheckBox);
        yearly_CheckBox.setOnClickListener(this);
        refrigrator_CheckBox=findViewById(R.id.refrigrator_CheckBox);
        refrigrator_CheckBox.setOnClickListener(this);
        Quarterly_CheckBox=findViewById(R.id.Quarterly_CheckBox);
        Quarterly_CheckBox.setOnClickListener(this);
        offices_CheckBox=findViewById(R.id.offices_CheckBox);
        offices_CheckBox.setOnClickListener(this);
        colleges_CheckBox=findViewById(R.id.colleges_CheckBox);
        colleges_CheckBox.setOnClickListener(this);
        workingPlaces_CheckBox=findViewById(R.id.workingPlaces_CheckBox);
        workingPlaces_CheckBox.setOnClickListener(this);
        single_CheckBox=findViewById(R.id.single_CheckBox);
        single_CheckBox.setOnClickListener(this);
        Double_CheckBox=findViewById(R.id.Double_CheckBox);
        Double_CheckBox.setOnClickListener(this);
        Triple_CheckBox=findViewById(R.id.Triple_CheckBox);
        Triple_CheckBox.setOnClickListener(this);
        Quadraple_CheckBox=findViewById(R.id.Quadraple_CheckBox);
        Quadraple_CheckBox.setOnClickListener(this);





        // Radio Button for Gender
        male_radioBtn=findViewById(R.id.male_radioBtn);
        male_radioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onGenderButtonClicked();
            }
        });
        female_radioBtn=findViewById(R.id.female_radioBtn);
        male_radioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onGenderButtonClicked();
            }
        });

        // Radio Button for Specility
        student_radioBtn=findViewById(R.id.student_radioBtn);
        student_radioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onSpecilityButtonClicked();
            }
        });
        workingProff_radioBtn = findViewById(R.id.workingProff_radioBtn);
        workingProff_radioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onSpecilityButtonClicked();
            }
        });


        cross_imageButton = findViewById(R.id.cross_imageButton);
        cross_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FilterActivity.this,Site_listView.class);
                startActivity(i);
            }
        });

        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar)findViewById(R.id.rangeSeekbar1);
        //rangeBar = findViewById(R.id.rangebar);
        min = findViewById(R.id.textViewMin);
        max = findViewById(R.id.textViewMax);

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                min.setText(String.valueOf(minValue));
                max.setText(String.valueOf(maxValue));
            }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });


    }

    private void onSpecilityButtonClicked() {
        if(student_radioBtn.isPressed()){
            workingProff_radioBtn.setEnabled(false);
            radioPressed = true;
        } else if (workingProff_radioBtn.isPressed()){
            student_radioBtn.setEnabled(false);
            radioPressed = true;
        } else {
            radioPressed = false;
        }
    }


    private void onGenderButtonClicked() {
        if(male_radioBtn.isPressed()){
            female_radioBtn.setEnabled(false);
            radioPressed = true;
        } else if (female_radioBtn.isPressed()){
            male_radioBtn.setEnabled(false);
            radioPressed = true;
        } else {
            radioPressed = false;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.wifi_CheckBox:
                if (wifi_CheckBox.isChecked())
                break;

            case R.id.power_CheckBox:
                if (power_CheckBox.isChecked())
                    break;

            case R.id.laundry_CheckBox:
                if (laundry_CheckBox.isChecked())
                    break;

            case R.id.Cctv_CheckBox:
                if (Cctv_CheckBox.isChecked())
                    break;

            case R.id.Parking_CheckBox:
                if (Parking_CheckBox.isChecked())
                    break;

            case R.id.monthly_CheckBox:
                if (monthly_CheckBox.isChecked())
                    break;

            case R.id.yearly_CheckBox:
                if (yearly_CheckBox.isChecked())
                    break;

            case R.id.refrigrator_CheckBox:
                if (refrigrator_CheckBox.isChecked())
                    break;

            case R.id.Quarterly_CheckBox:
                if (Quarterly_CheckBox.isChecked())
                    break;

            case R.id.offices_CheckBox:
                if (offices_CheckBox.isChecked())
                    break;

            case R.id.colleges_CheckBox:
                if (colleges_CheckBox.isChecked())
                    break;

            case R.id.workingPlaces_CheckBox:
                if (workingPlaces_CheckBox.isChecked())
                    break;

            case R.id.single_CheckBox:
                if (single_CheckBox.isChecked())
                    break;

            case R.id.Double_CheckBox:
                if (Double_CheckBox.isChecked())
                    break;

            case R.id.Triple_CheckBox:
                if (Triple_CheckBox.isChecked())
                    break;

            case R.id.Quadraple_CheckBox:
                if (Quadraple_CheckBox.isChecked())
                    break;
    }
}}
