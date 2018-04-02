package com.example.codemaven3015.onistayandroiddev;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by CodeMaven3015 on 3/28/2018.
 */

public class UserRegistrationICard extends android.support.v4.app.Fragment {
    View v;
    ImageView uploadphoto;
    EditText editTextDoB,editTextNumber, editTextName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.user_registration_idcard,container,false);
        editTextDoB = v.findViewById(R.id.editTextDoB);
        editTextNumber = v.findViewById(R.id.editTextNumber);
        editTextName = v.findViewById(R.id.editTextName);
        showCalenderDialoge();
        setUploadPhoto(v);
        setOnclickSumit(v);
        return v;
    }
    public void showCalenderDialoge(){
        editTextDoB.setFocusable(false);
        MyEditTextDatePicker myEditTextDatePicker = new MyEditTextDatePicker(getContext(),editTextDoB);
    }
    public void setOnclickSumit(final  View v){
        Button submitButton = v.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editTextDoB = v.findViewById(R.id.editTextDoB);

                String name,dob,iDnumber;
                name= editTextName.getText().toString();
                if(name.isEmpty()){
                    editTextName.setError("Please enter Name");
                    return;
                }
                dob = editTextDoB.getText().toString();
                if(dob.isEmpty()){
                    editTextName.setError("Please select DOB");
                    return;
                }
                iDnumber = editTextNumber.getText().toString();
                if(iDnumber.isEmpty()){
                    editTextName.setError("Please enter I-Card Number");
                    return;
                }
            }
        });

    }
    public void setUploadPhoto(View v){
        uploadphoto = v.findViewById(R.id.upload_photo);
        uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();//(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.putExtra("crop", "true");
                i.putExtra("outputX", 100);
                i.putExtra("outputY", 100);
                i.putExtra("scale", true);
                i.putExtra("return-data", true);
                // i.p
                startActivityForResult(Intent.createChooser(i,"Select Profile Picture"),10);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == -1 && data != null && data.getData() != null) {
            Uri uri = data.getData();//data.getStringExtra()
            Bitmap bitmap = null;
            bitmap = getScaledBitmap(uri);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            roundedBitmapDrawable.setAntiAlias(true);
            uploadphoto.setImageDrawable(roundedBitmapDrawable);
        }
    }
    private Bitmap getScaledBitmap(Uri uri){
        Bitmap thumb = null ;
        try {
            ContentResolver cr = getActivity().getContentResolver();
            InputStream in = cr.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=4;
            thumb = BitmapFactory.decodeStream(in,null,options);
        } catch (FileNotFoundException e) {
        }
        return thumb ;
    }
}
