package com.example.codemaven3015.onistayandroiddev;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Login_page extends AppCompatActivity {
    Button button,login_referalBtn;
    EditText login_editText, login_referalEditText;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    boolean permissionFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        login_referalEditText = findViewById(R.id.login_referalEditText);
        login_referalEditText.setVisibility(View.INVISIBLE);
        button = findViewById(R.id.login_verifybutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidation(login_editText.getText().toString())) {
                    //registerNumber(login_editText.getText().toString(),login_referalEditText.getText().toString());
                    openOTPPage();
                    //checkAndRequestPermissions();
                }
            }
        });
        login_editText = findViewById(R.id.login_editText);
        login_referalBtn = findViewById(R.id.login_referalBtn);
        login_referalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_referalEditText.getVisibility() == View.VISIBLE) {
                    login_referalEditText.setVisibility(View.INVISIBLE);
                }else {
                    login_referalEditText.setVisibility(View.VISIBLE);
                }
            }
        });
        permissionFlag = checkAndRequestPermissions();

        //login_editText.setVisibility(View.INVISIBLE);
        //checkValidation();
    }
    public boolean checkAndRequestPermissions(){
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);

        int receiveSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    public void openOTPPage(){
        Intent i = new Intent(Login_page.this, OTP_Verification.class);
        i.putExtra("PERMISSIONS",permissionFlag);
        startActivity(i);
    }
    // login api call
    public void registerNumber(String number, String referalCode){
        number = number.trim();
        referalCode = referalCode.trim();

        String url = "http://onistays.com/api/v2/features_prop";
        final VolleyAPICall volleyAPICall = new VolleyAPICall(this,url);
        volleyAPICall.executeRequest(Request.Method.GET, new VolleyAPICall.VolleyCallback() {
                    @Override
                    public void getResponse(JSONArray response) {
                        Log.e("VOLLEY","RES"+response);

                        parsingtheResponseData(response);

                    }
                }
        );

    }
    // check the successful response
    public void parsingtheResponseData(JSONArray responce){
        // success
        //
        //failed
        showAlertMessage showAlertMessage = new showAlertMessage(this,"Message","Tittle");
        showAlertMessage.showMessage();

    }
    // checking if phone is valid or not
    public boolean checkValidation(String text){
        long number = -1;
        try {

            number = Long.parseLong(text);
        }catch (NumberFormatException e){

        }
        if(number<0){
            login_editText.setError("Please enter valid phone number");
            return false;
        }else if(text.isEmpty()){
            login_editText.setError("Phone Number Cannot be empty");
            return false;
        }else if(text.length()!=10) {
            login_editText.setError("Please enter valid phone number");
            return false;
        }else if(login_referalEditText.getVisibility()==View.VISIBLE && login_referalEditText.getText().toString().isEmpty()){
            login_referalEditText.setError("Please Enter Valid Referal Code");
            return false;
        }
        return true;

    }

}
