package com.example.codemaven3015.onistayandroiddev;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by CodeMaven3015 on 3/16/2018.
 */

public class UserRegistrationAddress extends android.support.v4.app.Fragment {
    RecyclerView address_recycler_view;
    private RecyclerView.LayoutManager rvLayoutManager;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.user_registreation_address,container,false);
        address_recycler_view = view.findViewById(R.id.address_recycler_view);
        rvLayoutManager = new LinearLayoutManager(getActivity());
        address_recycler_view.setLayoutManager(rvLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        address_recycler_view.addItemDecoration(new SpacesItemDecoration(spacingInPixels,"Address"));
        AddressAdapter addressAdapter = new AddressAdapter(getContext());
        address_recycler_view.setAdapter(addressAdapter);
        imageView = view.findViewById(R.id.addAddress);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdressClick();
            }
        });
        return view;

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
        dialog.show();
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
           final TextView saveImageView = dialog.findViewById(R.id.saveImageView);
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


}
