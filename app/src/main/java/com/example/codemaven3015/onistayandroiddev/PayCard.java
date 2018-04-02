package com.example.codemaven3015.onistayandroiddev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PayCard extends AppCompatActivity {
    LinearLayout backApp_Bar,appmenuLL;
    Button EditBack_btn;
    ImageButton backButton;
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_card2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headerText = findViewById(R.id.headerText);
        headerText.setText("Payble Amount : ₹1200");
        headerText.setTextColor(getResources().getColor(R.color.white));
        appmenuLL = findViewById(R.id.appmenuLL);
        backApp_Bar = findViewById(R.id.backApp_Bar);
        appmenuLL.setVisibility(View.GONE);
        backApp_Bar.setVisibility(View.VISIBLE);
        backApp_Bar.setBackgroundColor(getResources().getColor(R.color.red_trans));
        EditBack_btn = findViewById(R.id.EditBack_btn);
        EditBack_btn.setVisibility(View.INVISIBLE);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PayCard.this,PayNow.class);
                startActivity(i);
            }
        });
    }
}
