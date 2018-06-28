package com.example.codemaven3015.onistayandroiddev;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;

public class Tabbed_CardLayout extends RecyclerView.Adapter<Tabbed_CardLayout.ViewHolder> {
    private Context context;
    private String name_activity;
    JSONArray jsonArray=new JSONArray();

    public Tabbed_CardLayout(Context context,String name_activity,JSONArray jsonArray) {
        this.context = context;
        this.name_activity = name_activity;
        this.jsonArray=jsonArray;
    }

    private int[] images = {R.drawable.hotel1, R.drawable.hotel2};
    private String[] city={"Noida","Delhi"};


    class ViewHolder extends RecyclerView.ViewHolder {
        public int currentItem;

        TextView cityName_textview,address_textView,date_textView,bookingDate_textView;
        LinearLayout tabbed_layout;
        ImageView hotel_image;
        Button book_btn;
        public ViewHolder(View itemView) {

            super(itemView);

            tabbed_layout=itemView.findViewById(R.id.tabbed_layout);
            book_btn=itemView.findViewById(R.id.book_btn);
            if(name_activity.equals("Upcoming"))
            {    book_btn.setText("Cancel");
                book_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context,Cancled.class);
                        context.startActivity(i);

                    }
                });





            }else if(name_activity.equals("Complete")){
                book_btn.setText("Book Again");
                book_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context,Site_listView.class);
                        context.startActivity(i);

                    }
                });

            }
            else if(name_activity.equals("Cancelled")){
                book_btn.setText("Book Again");
                book_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,Site_listView.class);
                        context.startActivity(i);


                    }
                });

            }
            cityName_textview=itemView.findViewById(R.id.cityName_textview);
            address_textView=itemView.findViewById(R.id.address_textView);
            date_textView=itemView.findViewById(R.id.date_textView);
            bookingDate_textView=itemView.findViewById(R.id.bookingDate_textView);
            hotel_image=itemView.findViewById(R.id.hotel_image);


        }
    }

    public Tabbed_CardLayout.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_tabbed__card_layout, null,false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        Tabbed_CardLayout.ViewHolder holder = new Tabbed_CardLayout.ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            holder.cityName_textview.setText("");
            holder.address_textView.setText(jsonObject.getString("Title"));
            holder.date_textView.setText(jsonObject.getString("Booking Start Date"));
            holder.bookingDate_textView.setText(jsonObject.getString("Booking End Date"));
            holder.hotel_image.setBackgroundResource(images[i]);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }




}