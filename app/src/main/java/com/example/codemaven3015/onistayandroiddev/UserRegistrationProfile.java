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
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by CodeMaven3015 on 3/8/2018.
 */

public class UserRegistrationProfile extends android.support.v4.app.Fragment {
    Button submitButton1;
    EditText editTextFirstName,editTextLastName,editTextEmail,editTextMobile,editTextDoB;
    RadioGroup radioGroupGender;
    ImageView upload_photo;
    View v;
    String name = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.user_registration_profile,container,false);        setWidgets(v);
        return v;
    }
    public void setWidgets(View v){
        //submitButton = v.findViewById(R.id.submitButton);
        submitButton1 = v.findViewById(R.id.addressSubmitBtn);
        editTextFirstName = v.findViewById(R.id.editTextFirstName);
        editTextLastName = v.findViewById(R.id.editTextLastName);
        editTextEmail = v.findViewById(R.id.editTextEmail);
        editTextMobile = v.findViewById(R.id.editTextMobile);
        editTextDoB = v.findViewById(R.id.editTextDoB);
        radioGroupGender = v.findViewById(R.id.radioGroupGender);
        upload_photo = v.findViewById(R.id.upload_photo);

        editTextDoB.setFocusable(false);
        editTextFirstName.setEnabled(false);
        editTextLastName.setEnabled(false);
        editTextEmail.setEnabled(false);
        editTextMobile.setEnabled(false);
        editTextDoB.setEnabled(false);
        radioGroupGender.setEnabled(false);
        upload_photo.setEnabled(false);
        editTextDoB.setEnabled(false);



        MyEditTextDatePicker myEditTextDatePicker = new MyEditTextDatePicker(getContext(),editTextDoB);
    }

}
