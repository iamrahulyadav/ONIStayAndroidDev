package com.example.codemaven3015.onistayandroiddev;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Tabbed_CardLayout extends RecyclerView.Adapter<Tabbed_CardLayout.ViewHolder> {
    private Context context;
    private String name_activity;

    public Tabbed_CardLayout(Context context,String name_activity) {
        this.context = context;
        this.name_activity = name_activity;
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

            tabbed_layout=(LinearLayout)itemView.findViewById(R.id.tabbed_layout);
            book_btn=(Button)itemView.findViewById(R.id.book_btn);
            if(name_activity.equals("Upcoming")){
                book_btn.setText("Cancel");
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
            cityName_textview=(TextView)itemView.findViewById(R.id.cityName_textview);
            address_textView=(TextView)itemView.findViewById(R.id.address_textView);
            date_textView=(TextView)itemView.findViewById(R.id.date_textView);
            bookingDate_textView=(TextView)itemView.findViewById(R.id.bookingDate_textView);
            hotel_image=(ImageView)itemView.findViewById(R.id.hotel_image);


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
        holder.hotel_image.setBackgroundResource(images[i]);
        holder.cityName_textview.setText(city[i]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }




}