package com.example.codemaven3015.onistayandroiddev;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class NewUserRegistration extends android.support.v4.app.Fragment {
    Button submitButton1;
    EditText editTextFirstName, editTextLastName, editTextEmail, editTextMobile, editTextDoB;
    RadioGroup radioGroupGender;
    ImageView upload_photo;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String firstName, password, dob, email, phone, gender;
    View v;
    String name = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.user_registration_profile, container, false);

        setWidgets(v);

        selectImageProfile();
        OnClickButtonSubmit();
        return v;
    }

    private void OnClickButtonSubmit() {
        submitButton1.setText("Register");
        submitButton1.setVisibility(View.VISIBLE);
        submitButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToServer();
            }
        });
    }


    public void setWidgets(View v) {
        //submitButton = v.findViewById(R.id.submitButton);
        sharedpreferences = getContext().getSharedPreferences("UserDetails", 0);
        editor = sharedpreferences.edit();
        submitButton1 = v.findViewById(R.id.addressSubmitBtn);
        editTextFirstName = v.findViewById(R.id.editTextFirstName);
        editTextLastName = v.findViewById(R.id.editTextLastName);
        editTextEmail = v.findViewById(R.id.editTextEmail);
        editTextMobile = v.findViewById(R.id.editTextMobile);
        editTextMobile.setFocusable(false);
        editTextMobile.setText(sharedpreferences.getString("CONTACT_NUMBER",""));
        editTextMobile.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  showMessage("Info","Phone number cannot be changed");
                                              }
                                          }
        );
        editTextDoB = v.findViewById(R.id.editTextDoB);
        radioGroupGender = v.findViewById(R.id.radioGroupGender);
        upload_photo = v.findViewById(R.id.upload_photo);
        editTextDoB.setFocusable(false);
        MyEditTextDatePicker myEditTextDatePicker = new MyEditTextDatePicker(getContext(), editTextDoB);
        submitButton1.setVisibility(View.VISIBLE);
    }

    public void selectImageProfile() {
        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();//(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.putExtra("crop", "true");
                i.putExtra("outputX", 100);
                i.putExtra("outputY", 100);
                i.putExtra("scale", true);
                i.putExtra("return-data", true);
                // i.p
                startActivityForResult(Intent.createChooser(i, "Select Profile Picture"), 10);
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
            upload_photo.setImageDrawable(roundedBitmapDrawable);
        }
    }

    private Bitmap getScaledBitmap(Uri uri) {
        Bitmap thumb = null;
        try {
            ContentResolver cr = getActivity().getContentResolver();
            InputStream in = cr.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            thumb = BitmapFactory.decodeStream(in, null, options);
        } catch (FileNotFoundException e) {
        }
        return thumb;
    }

    public void sendDataToServer() {

        if (checkForValidation()) {

            firstName = editTextFirstName.getText().toString().trim();
            password = editTextLastName.getText().toString().trim();
            dob = editTextDoB.getText().toString().trim();
            email = editTextEmail.getText().toString().trim();
            phone = sharedpreferences.getString("CONTACT_NUMBER","");//editTextMobile.getText().toString().trim();
            RadioButton radioButton = v.findViewById(radioGroupGender.getCheckedRadioButtonId());
            gender = radioButton.getText().toString();
            JSONObject reg = new JSONObject();
            try {
                reg.put("name", firstName);
                reg.put("mail", email);
                reg.put("pass", password);
                reg.put("status", "1");
                reg.put("field_contact_number", arrayParamFormat(phone));
                reg.put("field_gender", arrayParamFormatGender(gender.toLowerCase()));


                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                JSONObject obj = new JSONObject();
                obj.put("month","8");
                obj.put("year","2000");
                obj.put("day","7");
                obj.put("hour","0");
                jsonObject.put("value", obj);
                jsonArray.put(jsonObject);

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("und", jsonArray);
                reg.put("field_dob", jsonObject1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            registraionApi(reg);
        } else {
            return;
        }

    }

    private void registraionApi(JSONObject object) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "http://www.onistays.com/oni-endpoint/user/register";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("response", response.toString());
                try {
                    editor.putString("USER_ID",response.getString("uid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.putString("NAME",firstName);
                editor.putString("MAIL",email);
                editor.putString("GENDER",gender);
                editor.putString("DOB",dob);
                editor.commit();
                showMessage("Info","Profile Updated");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                Log.e("ONI", "INSIDE ERROR CALLBACK");
                Log.e("ONI", error.toString());
                showMessage("Info","Email id or phone number already taken");
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("X-CSRF-Token",sharedpreferences.getString("TOKEN",""));
                headers.put("Content-Type", "application/json");
                return headers;
            }



        }
                ;
        requestQueue.add(jsonObjectRequest);

    }

    public JSONObject arrayParamFormat(String string) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", string);
        jsonArray.put(jsonObject);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("und", jsonArray);
        return jsonObject1;
    }

    public JSONObject arrayParamFormatGender(String string) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONArray ar = new JSONArray();
        ar.put(string);
        jsonObject.put("value", ar);
        jsonArray.put(jsonObject);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("und", ar);
        return jsonObject1;
    }



    public boolean getSelectedIndex(){
        int selectedIndex = radioGroupGender.getCheckedRadioButtonId();
        if(selectedIndex<0){
            showMessage("Info","Please select gender");

            return false;
        }else {
            return true;
        }
    }
    public boolean checkForValidation(){
        if(emptyFieldValidation() || emailValidation() || specialCharValidation() || getSelectedIndex()){
            return true;
        }
        return false;
    }
    public boolean emailValidation(){
        String email = editTextEmail.getText().toString().trim();
        if(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else {
            editTextEmail.setError("Please enter valid Email");
            return false;
        }
    }
    public boolean emptyFieldValidation(){
        if(editTextFirstName.getText().toString().isEmpty()){
            editTextFirstName.setError("Please enter First Name");
            return false;
        }
        if(editTextEmail.getText().toString().isEmpty()){
            editTextEmail.setError("Please enter valid Email");
            return false;
        }
        if(editTextMobile.getText().toString().isEmpty()){
            editTextMobile.setError("Please enter valid Phone number ");
            return false;
        }
        if(editTextDoB.getText().toString().isEmpty()){
            editTextDoB.setError("Please select DoB ");
            return false;
        }
        return true;
    }
    public boolean specialCharValidation(){
        String s = editTextFirstName.toString();
        Pattern regex = Pattern.compile("[$&+;=\\\\?@#|/<>^*()%!]");

        if (regex.matcher(s).find()) {
            editTextFirstName.setError("Enter valid Name");
            return false;
        }
        if(regex.matcher(editTextLastName.getText()).find()){
            editTextLastName.setError("Enter valid Name");
            return false;
        }
        return true;
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        //builder.set
        builder.setMessage(message);
        //builder.show();
        AlertDialog dialog1 = builder.create();
        dialog1.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Window view = ((AlertDialog)dialog).getWindow();
                view.setBackgroundDrawableResource(R.color.white);
            }
        });
        dialog1.show();
    }

}