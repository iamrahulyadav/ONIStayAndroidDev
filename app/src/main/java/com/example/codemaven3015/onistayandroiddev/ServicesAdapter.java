package com.example.codemaven3015.onistayandroiddev;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Admin on 2/21/2018.
 */

public class ServicesAdapter  extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    private Context context;
    private  String fromWhere;
    private String[] productFeatureurl;
    private String[] productFeatureName;
    private  String[] offerImageUrl;


    public ServicesAdapter(Context context,String fromWhere) {
        this.context = context;
        this.fromWhere = fromWhere;
    }
    public ServicesAdapter(Context context,String fromWhere,String[] productFeatureurl, String[] productFeatureName) {
        this.productFeatureurl = productFeatureurl;
        this.context = context;
        this.fromWhere = fromWhere;
        this.productFeatureName = productFeatureName;
    }
    public ServicesAdapter(Context context,String fromWhere, String[] offerImageUrl) {
        this.offerImageUrl = offerImageUrl;
        this.context = context;
        this.fromWhere = fromWhere;
    }
    private int[] images = {
            R.drawable.red_help_me,
            R.drawable.red_ac,
            R.drawable.red_breakfast,
            R.drawable.red_lunch,
            R.drawable.red_dinner,
            R.drawable.red_laundry,
            R.drawable.red_houskeeping,
            R.drawable.red_refreshment,
    };
    private String[] text={
            "Help Me",
            "A.C",
            "Breakfast",
            "Lunch",
            "Dinner",
            "Laundry",
            "Housekeeping",
            "Refreshment"
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView services_image;
        ImageView offers_image,productFeature_image;
        TextView product_textview,service_text;
        LinearLayout offers_imageLinearLayout,productFeature_imageLinearLayout,oneClick;
        public ViewHolder(View itemView) {
            super(itemView);
            services_image = itemView.findViewById(R.id.services_image);
            productFeature_imageLinearLayout=itemView.findViewById(R.id.productFeature_imageLinearLayout);
            productFeature_image=itemView.findViewById(R.id.productFeature_image);
            offers_image = itemView.findViewById(R.id.offers_image);
            offers_imageLinearLayout = itemView.findViewById(R.id.offers_imageLinearLayout);
            product_textview = itemView.findViewById(R.id.product_textview);
            service_text=itemView.findViewById(R.id.service_text);
            oneClick = itemView.findViewById(R.id.oneClick);
            if(fromWhere.equals("service")){
                offers_imageLinearLayout.setVisibility(View.GONE);
                productFeature_imageLinearLayout.setVisibility(View.GONE);
                services_image.setVisibility(View.VISIBLE);
                service_text.setVisibility(View.VISIBLE);
                oneClick.setVisibility(View.VISIBLE);
            }else if(fromWhere.equals("product")){
                offers_imageLinearLayout.setVisibility(View.GONE);
                service_text.setVisibility(View.GONE);
                productFeature_imageLinearLayout.setVisibility(View.VISIBLE);
                services_image.setVisibility(View.GONE);
                oneClick.setVisibility(View.GONE);
            }else if(fromWhere.equals("offers")){
                offers_imageLinearLayout.setVisibility(View.VISIBLE);
                service_text.setVisibility(View.GONE);
                productFeature_imageLinearLayout.setVisibility(View.GONE);
                services_image.setVisibility(View.GONE);
                oneClick.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Snackbar.make(v, "Click detect on item" + position, Snackbar.LENGTH_LONG);

                }
            });
        }
    }

    public ServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.services_card, null,false);
        ServicesAdapter.ViewHolder holder = new ServicesAdapter.ViewHolder(view);
        return holder;

    }

    public void onBindViewHolder(ServicesAdapter.ViewHolder viewHolder, int i) {
        if(fromWhere.equals("service")){
            viewHolder.services_image.setBackgroundResource(images[i]);
            viewHolder.service_text.setText(text[i]);
        }else if(fromWhere.equals("product")){
            //viewHolder.productFeature_image.setBackgroundResource(productFeature[i]);
            Picasso
                    .with(context)
                    .load(productFeatureurl[i])
                    .into(viewHolder.productFeature_image);
            viewHolder.product_textview.setText(productFeatureName[i]);
        }else if(fromWhere.equals("offers")){
            //viewHolder.offers_image.setBackgroundResource(offerImageUrl[i]);
            Picasso
                    .with(context)
                    .load(offerImageUrl[i])
                    .into(viewHolder.offers_image);
        }



    }
    @Override
    public int getItemCount() {
        if(fromWhere.equals("service")){
            return images.length;
        }else if(fromWhere.equals("product")){
            return productFeatureurl.length;
        } else if(fromWhere.equals("offers")){
            return offerImageUrl.length;
        }
        return images.length;
    }
}