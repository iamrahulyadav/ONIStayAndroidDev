package com.example.codemaven3015.onistayandroiddev;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Login_page extends AppCompatActivity {
    Button button,login_referalBtn;
    EditText login_editText, login_referalEditText;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    boolean permissionFlag;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Map<String, String> header;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        login_referalEditText = findViewById(R.id.login_referalEditText);
        login_referalEditText.setVisibility(View.INVISIBLE);
        button = findViewById(R.id.login_verifybutton);
        sharedpreferences = getApplicationContext().getSharedPreferences("UserDetails", 0);
        editor = sharedpreferences.edit();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = login_editText.getText().toString();
                if(checkValidation(phone_number)) {
                    registerNumber(phone_number,"");
                    //registerNumber(login_editText.getText().toString(),login_referalEditText.getText().toString());
                    //openOTPPage();
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
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

        String url = "http://www.onistays.com/oni-endpoint/oni_user_auth/"+number;
        final VolleyAPICallJsonObject volleyAPICallJsonObject = new VolleyAPICallJsonObject(this,url);
        progressBar.setVisibility(View.VISIBLE);
        volleyAPICallJsonObject.executeRequest(Request.Method.GET, new VolleyAPICallJsonObject.VolleyCallback() {
            @Override
            public void getResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                Log.e("VOLLEY", "RES" + response);
                if (response == null) {

                } else {
                    parsingtheResponseData(response);

                }

            }

                    @Override
                    public void getError(VolleyError error) {
                        Log.e("VOLLEY", "RES" + error);
                        progressBar.setVisibility(View.GONE);
//                        if (error != null) {
//                            String errorCheck = "org.json.JSONException: Value null of type org.json.JSONObject$1 cannot be converted to JSONObject";
//                            Log.e("check", error.getMessage());
//
//
//                        }
                    }
                }
        );

    }
    // check the successful response
    public void parsingtheResponseData(JSONObject responce){
        try{
                String result = responce.getString("result");
                if(result.trim().equals("Mobile number not exist")) {
                    editor.putString("CONTACT_NUMBER", login_editText.getText().toString());
                    editor.apply();
                    Intent i = new Intent(Login_page.this, Home.class);
                    i.putExtra("Name", "Guest");
                    startActivity(i);
                }

        }catch (JSONException e) {
            e.printStackTrace();
        }

       Log.e("JsonObject",responce.toString());
        try {
            editor.putString("NAME",responce.getString("name"));
            editor.putString("MAIL",responce.getString("mail"));
            editor.putString("CONTACT_NUMBER",login_editText.getText().toString());
            editor.putString("USER_ID",responce.getString("uid"));
            editor.putString("GENDER",getValueFromResponseObj(responce.getJSONObject("field_gender")));
            editor.putString("DOB",getValueFromResponseObj(responce.getJSONObject("field_dob")));
            editor.commit();
            //openOTPPage();
            showCustomView(responce.getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //showAlertMessage showAlertMessage = new showAlertMessage(this,"Message","Tittle");
        //showAlertMessage.showMessage();

    }
    public String getValueFromResponseObj(JSONObject obj){
        JSONArray array = new JSONArray();
        try {
            array = obj.getJSONArray("und");
            JSONObject obj1 =  new JSONObject();
            obj1 = array.getJSONObject(0);
            return obj1.getString("value");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }
    public void showCustomView(final String name){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        final LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.enter_password, null);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        final AlertDialog dialog = alertDialogBuilder.create();
        TextView passwordText = view.findViewById(R.id.password_view);
        passwordText.setText("Hi! "+name);
        TextView cross = view.findViewById(R.id.cross);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        final EditText password_text= view.findViewById(R.id.password_text);
        Button forgotLogin_button=view.findViewById(R.id.forgotLogin_button);
        forgotLogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                forgotApi();

            }
        });
        Button password_button = view.findViewById(R.id.password_button);
        password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = password_text.getText().toString().trim();
                if(password.equals("")){
                    password_text.setError("Please enter password");
                    return;
                }else {
                    header = new HashMap<>();
                    header.put("username",sharedpreferences.getString("NAME",""));
                    header.put("password",password);

                    loginApiCall();

                     //String url = "http://www.onistays.com/api/v4/users/login";
                }
            }
        });
        dialog.show();

    }

    private void forgotApi() {
        String url = "http://www.onistays.com/oni-endpoint-xml/user/request_new_password";
        HashMap header1 = new HashMap<>();
        header1.put("name", sharedpreferences.getString("MAIL", ""));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        SimpleXmlRequest<result> simpleRequest = new SimpleXmlRequest<result>(Request.Method.POST, url, result.class, header1,
                new Response.Listener<result>() {
                    @Override
                    public void onResponse(result response) {
                        // response Object
                        Log.e("response",response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error Object
                        Log.e("error",error.toString());
                    }
                }
        );
        requestQueue.add(simpleRequest);
    }


//        final VolleyAPICall volleyAPICallJsonObject1 = new VolleyAPICall(this,url,header1);
//        volleyAPICallJsonObject1.executeRequest(Request.Method.POST, new VolleyAPICall.VolleyCallback() {
//            @Override
//            public void getResponse(JSONArray response) {
//
//                Log.e("lol",response.toString());
//
//            }
//
////            @Override
////            public void getError(VolleyError error) {
////                Log.e("lol",error.toString());
////            }
//        });


    public void loginApiCall(){
        String url = "http://www.onistays.com/oni-endpoint/user/login";

        final VolleyAPICallJsonObject volleyAPICallJsonObject1 = new VolleyAPICallJsonObject(this,url,header);
        volleyAPICallJsonObject1.executeRequest(Request.Method.POST, new VolleyAPICallJsonObject.VolleyCallback() {
            @Override
            public void getResponse(JSONObject response) {

                Log.e("lol",response.toString());

                try {
                    editor.putString("SESSION_ID",response.getString("sessid"));
                    editor.putString("TOKEN",response.getString("token"));
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Login_page.this,Home.class);
                i.putExtra("Name",sharedpreferences.getString("NAME",""));
                startActivity(i);
            }

            @Override
            public void getError(VolleyError error) {
                showAlertMessage showAlertMessage = new showAlertMessage(getApplicationContext(),"You have entered an invalid phone number or password","Info");
                showAlertMessage.showMessage();
            }
        });
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
