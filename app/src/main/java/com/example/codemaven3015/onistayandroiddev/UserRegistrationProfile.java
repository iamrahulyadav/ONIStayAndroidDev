package com.example.codemaven3015.onistayandroiddev;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    View v;
    String name = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.user_registration_profile,container,false);
        setWidgets(v);
        setDataifRegistered();
        return v;
    }

    private void setDataifRegistered() {

        sharedPreferences = getContext().getSharedPreferences("UserDetails", 0);
        editor = sharedPreferences.edit();

        String url = "http://www.onistays.com/oni-endpoint/user/";
        url = url+sharedPreferences.getString("USER_ID","");

        final VolleyAPICallJsonObject volleyAPICallJsonObject1 = new VolleyAPICallJsonObject(getContext(),url);
        volleyAPICallJsonObject1.executeRequest(Request.Method.GET, new VolleyAPICallJsonObject.VolleyCallback() {
            @Override
            public void getResponse(JSONObject response) {
                Log.e("success1",response.toString());
                saveAddressFcId(response);

                //homeAddress();
            }

            @Override
            public void getError(VolleyError error) {
                Log.e("error2",error.toString()+"checkingerror");


                //showAlertMessage showAlertMessage = new showAlertMessage(getApplicationContext(),"You have entered an invalid phone number or password","Info");
                //showAlertMessage.showMessage();

            }
        });

        editTextFirstName.setText(sharedPreferences.getString("NAME",""));
        editTextLastName.setText(" ");
        editTextEmail.setText(sharedPreferences.getString("MAIL",""));
        editTextMobile.setText(sharedPreferences.getString("CONTACT_NUMBER",""));
        editTextDoB.setText(sharedPreferences.getString("DOB",""));
        if((sharedPreferences.getString("GENDER","")).toLowerCase().equals("female")){
            radioGroupGender.check(R.id.radioFemale);
        }else {
            radioGroupGender.check(R.id.radioMale);
        }

    }

    private void saveAddressFcId(JSONObject response) {

        try {
            JSONObject jsonObject=response.getJSONObject("field_address");
            JSONArray jsonArray=jsonObject.getJSONArray("und");
            //ArrayList<String> arrayListDemo = new ArrayListDemo<String>();
            editor.putString("ADDRESS",jsonArray.toString());
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        submitButton1.setVisibility(View.INVISIBLE);

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