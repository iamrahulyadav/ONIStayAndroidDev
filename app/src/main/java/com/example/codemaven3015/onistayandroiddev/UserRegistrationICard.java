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
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeMaven3015 on 3/28/2018.
 */

public class UserRegistrationICard extends android.support.v4.app.Fragment {
    View v;
    ImageView uploadphoto;
    EditText editTextName;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.user_registration_idcard,container,false);
        sharedPreferences = getContext().getSharedPreferences("UserDetails", 0);
        editor = sharedPreferences.edit();
        //editTextDoB = v.findViewById(R.id.editTextDoB);
        //editTextNumber = v.findViewById(R.id.editTextNumber);
        editTextName = v.findViewById(R.id.editTextName);
        //showCalenderDialoge();
        setUploadPhoto(v);
        setOnclickSumit(v);
        return v;
    }
    public void showCalenderDialoge(){
        //editTextDoB.setFocusable(false);
        //MyEditTextDatePicker myEditTextDatePicker = new MyEditTextDatePicker(getContext(),editTextDoB);
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
//                dob = editTextDoB.getText().toString();
//                if(dob.isEmpty()){
//                    editTextName.setError("Please select DOB");
//                    return;
//                }
//                iDnumber = editTextNumber.getText().toString();
//                if(iDnumber.isEmpty()){
//                    editTextName.setError("Please enter I-Card Number");
//                    return;
//                }
                uploadBitmap(bitmap);
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
            bitmap = null;
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

    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
        final String tags = sharedPreferences.getString("USER_ID","");
        String url = "http://www.onistays.com/oni-endpoint/create-update-image-service/image";//editTextTags.getText().toString().trim();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        Log.e("check",response.toString());
                        showMessage("info","Photo Uploaded");
                            //JSONObject obj = new JSONObject(new String(response.data));
                            //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                       // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", tags);
                params.put("X-CSRF-Token",sharedPreferences.getString("TOKEN",""));
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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
