package com.example.codemaven3015.onistayandroiddev;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.cocosw.bottomsheet.BottomSheet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Site_listView extends AppCompatActivity {
    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    Card_layout adapter;
    TextView sort_textView ,filter_textView,headerText;
    ListApdapter listApdapter;
    LinearLayout backApp_Bar,appmenuLL;
    Button EditBack_btn;
    ImageButton backButton;
    ProgressDialog dialog;


// HARPREET

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog = new ProgressDialog(this);
        headerText = findViewById(R.id.headerText);
        headerText.setText("Select Property");
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
                Intent i = new Intent(Site_listView.this,Home.class);
                startActivity(i);
            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        sort_textView = findViewById(R.id.sort_textView);

        //for filter click
        filter_textView = findViewById(R.id.filter_textView);
        filter_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Site_listView.this, FilterActivity.class);
                startActivity(i);
            }
        });

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
         setDataToListView();

        sort_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet();
            }
        });
    }

    private void alertUserAboutError()
    {
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        alertDialogFragment.show(getFragmentManager(), "error_dialog");
//        Intent intent=new Intent(this,Home.class);
//        startActivity(intent);


    }

    private void setDataToListView() {
        String url = "";
        dialog.show();
        if(getIntent().getStringExtra("fromWhere").equals("city")) {
            url  = "http://www.onistays.com/stateapi/v1.1/property-state?args[0]=";
            url = url + getIntent().getStringExtra("CITY");

        }else{
            url = "http://www.onistays.com/stateapi/v1.1/property-state?args[0]=''&args[1]=";
            url = url + getIntent().getStringExtra("SEARCH");


        }

        final VolleyAPICall volleyAPICallJsonObject1 = new VolleyAPICall(this, url);
        volleyAPICallJsonObject1.executeRequest(Request.Method.GET, new VolleyAPICall.VolleyCallback() {
            @Override
            public void getResponse(JSONArray response) {
                dialog.setMessage("Searching......");
                dialog.dismiss();
                Log.e("check", response.toString());
                if(response.length()<=0)
                {
//                    showAlertMessage showAlertMessage = new showAlertMessage(getApplicationContext(),"You have entered an invalid phone number or password","Info");
//                    showAlertMessage.showMessage();

                    alertUserAboutError();


                }else {
                    adapter = new Card_layout(getApplicationContext(), "SiteList", response);
                    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
                    recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, "list"));
                    recyclerView.setAdapter(adapter);
                }
            }
        });

    }

    public void bottomSheet(){
        new BottomSheet.Builder(this).title("Sort By").sheet(R.menu.sort).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.invite:
                        Toast.makeText(getApplicationContext(),"jhgdjk",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }).show();

    }

}
