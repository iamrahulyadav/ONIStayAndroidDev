package com.example.codemaven3015.onistayandroiddev;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OTP_Verification extends AppCompatActivity {
    EditText otp_editText;
    Button otp_sendBtn;
    TextView otp_textview;
    LinearLayout hopInLL;
    boolean permissionFlags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__verification);
        permissionFlags = getIntent().getBooleanExtra("PERMISSIONS",false);
        otp_editText=findViewById(R.id.otp_editText);
        otp_textview=findViewById(R.id.otp_textview);
        setResendOTP();
        hopInLL = findViewById(R.id.hopInLL);
        hopInLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidation(otp_editText.getText().toString())) {
                    Intent i = new Intent(OTP_Verification.this,Home.class);
                    startActivity(i);
                }
            }
        });
        if(!permissionFlags) {
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
            lbm.registerReceiver(receiver, new IntentFilter("filter_string"));
        }
    }
    public void setResendOTP(){
        otp_sendBtn=findViewById(R.id.otp_sendBtn);
        otp_sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickYes();
            }
        });
    }

    public void onClickYes(){
        showMessage("","Do you want to change the mobile number?");
//        showAlertMessage.showMessageWithYesAndNo(new showAlertMessage.YesAlertCallback() {
//            @Override
//            public void yesFunction() {
//                moveToLoginPage();
//            }
//        });
    }
    public void moveToLoginPage(){
        Intent i = new Intent(OTP_Verification.this,Login_page.class);
        startActivity(i);
    }
    public boolean checkValidation(String text){
        if(text.isEmpty()) {
            otp_editText.setError("Phone Number Cannot be empty2");
            return false;
        }
        return true;


    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String str = intent.getStringExtra("key");
                otp_editText.setText(str);
                // get all your data from intent and do what you want
            }
        }
    };
    @Override
    public void onResume() {
        if(!permissionFlags)
            LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("filter_string"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(!permissionFlags)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

