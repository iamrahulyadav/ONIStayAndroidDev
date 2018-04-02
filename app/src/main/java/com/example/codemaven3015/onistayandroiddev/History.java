package com.example.codemaven3015.onistayandroiddev;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    // Array of strings...
    ArrayList yourlist = new ArrayList();
    ArrayList yourlist1 = new ArrayList();
    LinearLayout backApp_Bar,appmenuLL;
    Button EditBack_btn;
    ImageButton backButton;
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headerText = findViewById(R.id.headerText);
        headerText.setText("History of One Click service");
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
                Intent i = new Intent(History.this,Home.class);
                startActivity(i);
            }
        });

        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist.add("12/02/2017 : Amount Rs. 600");
        yourlist1.add("Refreshment");
        yourlist1.add("Laundary");
        yourlist1.add("Help me!");
        yourlist1.add("Lunch");

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, yourlist1){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);

                // Get the Layout Parameters for ListView Current Item View
                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 160;
                view.setLayoutParams(params);

                return view;
            }
        };
        ListView servicesListView =findViewById(R.id.servicesListView);
        servicesListView.setAdapter(adapter);

        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, yourlist);
        ListView historyListView = (ListView) findViewById(R.id.historyListView);
        historyListView.setAdapter(adapter1);

    }
}