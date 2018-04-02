package com.example.codemaven3015.onistayandroiddev;

/**
 * Created by CodeMaven3015 on 1/23/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListApdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private int images[];
    private Context context;
    private String fromWhere;




    public ListApdapter(ArrayList<String> list, Context context,int images[],String fromWhere) {
        this.list = list;
        this.images = images;
        this.context = context;
        this.fromWhere = fromWhere;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_image_text, null);
        }

        //Handle TextView and display string from your list
        LinearLayout notification_lineraLayout = view.findViewById(R.id.notification_lineraLayout);
        LinearLayout linearLayout_image_text = view.findViewById(R.id.linearLayout_image_text);
        if(fromWhere.equals("getStarted")) {
            notification_lineraLayout.setVisibility(View.GONE);
            linearLayout_image_text.setVisibility(View.VISIBLE);
            TextView listItemText = (TextView) view.findViewById(R.id.txtList);
            listItemText.setText(list.get(position));
            ImageView imageView = (ImageView) view.findViewById(R.id.imgList);
            imageView.setImageResource(images[position]);
        }else if(fromWhere.equals("notification")){
            notification_lineraLayout.setVisibility(View.VISIBLE);
            linearLayout_image_text.setVisibility(View.GONE);
            TextView listItemText = (TextView) view.findViewById(R.id.notification_details);
            listItemText.setText(list.get(position));
            ImageView imageView = (ImageView) view.findViewById(R.id.notification_image);
            imageView.setImageResource(images[position]);
        }

        return view;
    }
}