package com.example.codemaven3015.onistayandroiddev;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 2/2/2018.
 */

class Product_Details__ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private int[] images={R.drawable.hotel1,R.drawable.hotel2,R.drawable.hotel3,R.drawable.hotel4,R.drawable.hotel5
    ,R.drawable.hotel6,R.drawable.hotel7,R.drawable.hotel8};
    GridView androidGridView;

    private ArrayList<String> arrayImage = new ArrayList<String>();

    String[] gridViewString = {"Room 1", "Room 2", "Room 3", "Room 4", "CRIME", "ENTERTAINMENT"} ;
    int[] gridViewImageId = {
            R.drawable.bed_grey, R.drawable.bed_grey, R.drawable.bed_grey, R.drawable.bed_grey, R.drawable.bed_grey, R.drawable.bed_grey
    };


    public Product_Details__ViewPagerAdapter(Context context,ArrayList<String> arrayImage){
        this.context=context;
        this.arrayImage = arrayImage;
    }
    @Override
    public int getCount(){
        return arrayImage.size();
    }
    @Override
    public boolean isViewFromObject(View view,Object object){
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container,final int Position){
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom_layout,null);
        ImageView imageView=(ImageView)view.findViewById(R.id.CustomImage);
        //imageView.setImageResource(images[Position]);
        Picasso
                .with(context)
                .load(arrayImage.get(Position))
                .into(imageView);
        ViewPager vp=(ViewPager)container;
        vp.addView(view,0);
        String flag = ((Activity) context).getIntent().getStringExtra("IN_VIEW_IMAGE");
        if(flag == null){
            flag = "";
        }
        if(flag.equals("true")){
            //imageView.setAdjustViewBounds(true);
            //imageView.setScaleType(ImageView.ScaleType.);
        }else {
            //imageView.setAdjustViewBounds(false);
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //moveToImageSiteView();
                }
            });
        }
        return view;

    }
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
    public void moveToImageSiteView(){
        Intent i = new Intent(context,Site_Image_View.class);
        i.putExtra("IN_VIEW_IMAGE","true");
        context.startActivity(i);
    }
}
