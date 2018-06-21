package com.example.codemaven3015.onistayandroiddev;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeMaven3015 on 3/16/2018.
 */

public class UserRegistrationAddress extends android.support.v4.app.Fragment {
    RecyclerView address_recycler_view;
    private RecyclerView.LayoutManager rvLayoutManager;
    ImageView imageView;
    TextView save;
    EditText address1EditText,pinCodeEditText,phoneEditText,stateEdit,cityEdit;
    RadioButton homeRadio,officeRadio;
    RadioGroup typeAddressRadioGroup;
    Map<String, String> header;

    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;

    Spinner citySpinner, stateSpinner;
    ArrayList<String> stringArrayState;
    ArrayList<String> stringArrayCity;
    String spinnerStateValue, city;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.user_registreation_address,container,false);
        address_recycler_view = view.findViewById(R.id.address_recycler_view);
        rvLayoutManager = new LinearLayoutManager(getActivity());
        address_recycler_view.setLayoutManager(rvLayoutManager);
        sharedpreferences = getContext().getSharedPreferences("UserDetails", 0);
        editor = sharedpreferences.edit();
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        address_recycler_view.addItemDecoration(new SpacesItemDecoration(spacingInPixels,"Address"));
        AddressAdapter addressAdapter = new AddressAdapter(getContext());
        address_recycler_view.setAdapter(addressAdapter);

        ////Set Spinner
        SetSpinner(view);

        imageView = view.findViewById(R.id.addAddress);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdressClick();
            }
        });
        return view;

    }

    private void SetSpinner(View view)
    {
        citySpinner = view.findViewById(R.id.spinnerCity);
        stateSpinner = view.findViewById(R.id.spinnerState);


        //set city adapter


//        final ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(this, R.layout.spinnertextview, stringArrayCity);
//        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        citySpinner.setAdapter(adapterCity);

    }

    public void addAdressClick(){
        final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_address);
        final ImageView crossImageView = dialog.findViewById(R.id.crossImageView);
        //final ImageView saveImageView = dialog.findViewById(R.id.saveImageView);
        crossImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        address1EditText=dialog.findViewById(R.id.address1EditText);
        pinCodeEditText=dialog.findViewById(R.id.pinCodeEditText);
        phoneEditText=dialog.findViewById(R.id.phoneEditText);
        stateEdit=dialog.findViewById(R.id.stateEdit);
        cityEdit=dialog.findViewById(R.id.cityEdit);
        typeAddressRadioGroup=dialog.findViewById(R.id.typeAddressRadioGroup);
        homeRadio=dialog.findViewById(R.id.homeRadio);
        officeRadio=dialog.findViewById(R.id.officeRadio);



        save=dialog.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidation()){
                    String address, pin_code, phone_number, state, city,address_type,bool_default_add;

                    address = address1EditText.getText().toString().trim();
                    pin_code = pinCodeEditText.getText().toString().trim();
                    phone_number = phoneEditText.getText().toString().trim();
                    state = stateEdit.getText().toString().trim();
                    city = cityEdit.getText().toString().trim();

                    RadioButton radioButton = v.findViewById(typeAddressRadioGroup.getCheckedRadioButtonId());
                    address_type = radioButton.getText().toString();
                    header = new HashMap<>();
                    header.put("uid",sharedpreferences.getString("USER_ID",""));
                    header.put("address1",address);
                    header.put("city",city);
                    header.put("state",state);
                    header.put("phone_number",phone_number);
                    header.put("address_type",address_type);
                    header.put("pin_code",pin_code);
                    header.put("bool_default_add","1");


                    setAddressApi();
                }
            }

        });
        dialog.show();
    }

    private void setAddressApi()
    {
        String url = "http://www.onistays.com/oni-endpoint/user/login";

        final VolleyAPICallJsonObject volleyAPICallJsonObject1 = new VolleyAPICallJsonObject(getContext(),url,header);
        volleyAPICallJsonObject1.executeRequest(Request.Method.POST, new VolleyAPICallJsonObject.VolleyCallback() {
            @Override
            public void getResponse(JSONObject response) {
                Log.e("success",response.toString());
                //homeAddress();
            }

            @Override
            public void getError(VolleyError error) {

                showMessage("Info","Try again!!!!!");

                //showAlertMessage showAlertMessage = new showAlertMessage(getApplicationContext(),"You have entered an invalid phone number or password","Info");
                //showAlertMessage.showMessage();

            }
        });
    }

    private boolean checkValidation()
    {
        if(address1EditText.getText().toString().equals("")) {
            address1EditText.setError("field Cannot be empty");
            return false;
        }else  if(pinCodeEditText.getText().toString().equals("")) {
            pinCodeEditText.setError("field Cannot be empty");
            return false;
        }else  if(phoneEditText.getText().toString().equals("")) {
            phoneEditText.setError("field Cannot be empty");
            return false;
        }else  if(stateEdit.getText().toString().equals("")) {
            stateEdit.setError("field Cannot be empty");
            return false;
        }else  if(cityEdit.getText().toString().equals("")) {
            cityEdit.setError("field Cannot be empty");
            return false;
        }
        return true;
        }


    public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
        private Context context;


        public AddressAdapter(Context context) {
            this.context = context;
        }

        private String[] address = {"Chapter One",
                "Chapter Two",
                "Chapter Three",
                "Chapter Four",
                "Chapter Five",
                };


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView address_textView;
            public ImageView menuImage;
            public ViewHolder(View itemView) {
                super(itemView);
                address_textView = (TextView) itemView.findViewById(R.id.textAddressHeader);
                menuImage = itemView.findViewById(R.id.menuAddressimage);
                menuImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       final int position = getAdapterPosition();
                        PopupMenu popup = new PopupMenu(context,v);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                int id = item.getItemId();
                                if(id == R.id.action_edit){
                                    final Dialog dialog = new Dialog(context, android.R.style.Theme_Light);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.edit_address);
                                    setDatainDialogue(dialog,position);
                                    setOnclickSave(dialog,position);
                                    final ImageView crossImageView = dialog.findViewById(R.id.crossImageView);
                                    //final ImageView saveImageView = dialog.findViewById(R.id.saveImageView);
                                    crossImageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                }
                                return false;
                            }
                        });// to implement on click event on items of menu
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.menu_user_registration, popup.getMenu());
                        popup.show();
                    }
                });
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.address_cardview, null, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

       public void setDatainDialogue(Dialog dialog, int position){
           final EditText address1EditText = dialog.findViewById(R.id.address1EditText);
           address1EditText.setText(position + "");

       }
       public void setOnclickSave(final Dialog dialog, final int position){
           final TextView saveImageView = dialog.findViewById(R.id.save);
           saveImageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   dialog.dismiss();/*{
                    EditText address1EditText = dialog.findViewById(R.id.address1EditText);
                    EditText pinCodeEditText = dialog.findViewById(R.id.pinCodeEditText);
                    EditText phoneEditText = dialog.findViewById(R.id.phoneEditText);
                   Spinner spinnerState = dialog.findViewById(R.id.spinnerState);
                   Spinner spinnerCity = dialog.findViewById(R.id.spinnerCity);
                   RadioGroup typeAddressRadioGroup = dialog.findViewById(R.id.typeAddressRadioGroup);
                   Button default_Button = dialog.findViewById(R.id.default_Button);
                   String address,pinCode,phone,state,city,type,setDefault;
                   address = address1EditText.getText().toString();
                   pinCode = pinCodeEditText.getText().toString();
                   phone = phoneEditText.getText().toString();
                   state = spinnerState.getSelectedItem().toString();
                   city = spinnerCity.getSelectedItem().toString();
                   RadioButton rb = dialog.findViewById(typeAddressRadioGroup.getCheckedRadioButtonId());
                   type = rb.getText().toString();
                   */
               }
           });


       }
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.address_textView.setText(address[i]);

        }
        @Override
        public int getItemCount() {
            return address.length;
        }
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
    public void homeAddress(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setCancelable(true);
        builder.setTitle("Successfull");
        builder.setMessage("Profile Updated");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getContext() , Home.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton("No", null);
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
