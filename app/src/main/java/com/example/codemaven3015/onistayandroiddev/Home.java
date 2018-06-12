package com.example.codemaven3015.onistayandroiddev;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class
Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    TextView greeting_textView;
    SearchView searchbar;
    GridView androidGridView;
    ImageButton menuBar_imgBtn,notification_imgBtn;
    DrawerLayout drawer;
    Button City_Btn,searchBtn;
    Menu menu;
    Spinner SearchFor_sppiner,searchCity_spinner;
    RecyclerView recyclerView,offerRecyclerView,product_RecyclerView;
    LinearLayoutManager layoutManager;
    ServicesAdapter adapter,adapterOffer;
    ImageButton wallet_Image,fav_imgBtn;
    ImageView home_firstImage;
    ProgressBar progressBar;
    HashMap<String, String> cityId ;



    String[] gridViewString = {"Get 30% off\\n on your first booking",
            "Get 30% off\\n on your first booking",
            "Get 30% off\\n on your first booking",
            "Get 30% off\\n on your first booking",} ;
    int[] gridViewImageId = {
            R.drawable.hotel1, R.drawable.hotel2, R.drawable.hotel3, R.drawable.hotel4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        setGridData();
        greeting_textView=findViewById(R.id.greeting_textView);
        menuBar_imgBtn=findViewById(R.id.menuBar_imgBtn);

        //search view
        searchBtn=findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
                if(searchbar.getQuery().equals("")) {
                    Toast.makeText(getApplicationContext(), "enter what you want to search", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(Home.this, Site_listView.class);
                    i.putExtra("fromWhere", "search");
                    i.putExtra("SEARCH", searchbar.getQuery());
                    i.putExtra("UID", 0);
                    startActivity(i);
                }
            }
        });

        searchbar=findViewById(R.id.searchbar);
        searchbar.setQueryHint("Where you want to stay");
        //EditText et= (EditText) findViewById(R.id.searchbar);
        //et.setHint("Where you want to stay");
        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.equals("")){
                    Toast.makeText(getApplicationContext(), "enter what you want to search", Toast.LENGTH_LONG).show();
                }else {

                    Intent i = new Intent(Home.this, Site_listView.class);
                    i.putExtra("fromWhere","search");
                    i.putExtra("SEARCH", query);
                    i.putExtra("UID", 0);
                    startActivity(i);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuBar_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBar();
            }
        });

        // sppiner For Search & Cities
        SearchFor_sppiner=findViewById(R.id.SearchFor_sppiner);
        searchCity_spinner=findViewById(R.id.searchCity_spinner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setOnclickOnToolBar();
        greeting();
        setServicesimages();
        //setOfferimages();
        //setProductimages();
        setNotificationView();
        apiCallForFeaturedProperty();
        apiCallForofferImage();
        apiCalltogetCity();
        setOnClickCitySpinner();
        //updateMenuTitles();

    }

    private void setOnClickCitySpinner() {

        searchCity_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(position>=1) {
                    String tid = cityId.get(selectedItem);
                    Intent i = new Intent(Home.this, Site_listView.class);
                    i.putExtra("fromWhere","city");
                    i.putExtra("CITY", selectedItem);
                    i.putExtra("UID", tid);
                    startActivity(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void apiCalltogetCity() {
            String url = "http://www.onistays.com/api/v1.1/oni_state_city/2";

            final VolleyAPICallJsonObject volleyAPICallJsonObject1 = new VolleyAPICallJsonObject(this,url);
            volleyAPICallJsonObject1.executeRequest(Request.Method.GET, new VolleyAPICallJsonObject.VolleyCallback() {
                @Override
                public void getResponse(JSONObject response) {
                    Log.e("city",response.toString());
                    List<String> city = new ArrayList<String>();
                    city.add("City");
                    //city.add("City");
                    cityId =  new HashMap<String, String>();
                    for(Iterator<String> iter = response.keys(); iter.hasNext();) {
                        String key = iter.next();
                        try {
                            JSONObject jsonObject = response.getJSONObject(key);
                            JSONObject children = jsonObject.getJSONObject("children");
                            for(Iterator<String> i = children.keys();i.hasNext();){
                                String childKey = i.next();
                                JSONObject obj = children.getJSONObject(childKey);
                                city.add(obj.getString("name"));
                                cityId.put(obj.getString("name"),obj.getString("tid"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, city);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    searchCity_spinner.setAdapter(dataAdapter);
                }

                @Override
                public void getError(VolleyError error) {
                    Log.e("Error",error.toString());
                }

            });

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void setGridData()
    {
        DealsGridView adapterViewAndroid = new DealsGridView(Home.this, gridViewString, gridViewImageId);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(Home.this, "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setOnclickOnToolBar(){
        wallet_Image = findViewById(R.id.wallet_Image);
        wallet_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,OniCredits.class);
                startActivity(i);
            }
        });
        fav_imgBtn = findViewById(R.id.fav_imgBtn);
        fav_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Wishlist.class);
                startActivity(i);
            }
        });
    }
    //apicall for featured property
    public void apiCallForFeaturedProperty(){
        String url = "http://www.onistays.com/api/v1/features_prop";

        final VolleyAPICall volleyAPICall = new VolleyAPICall(this,url);
        volleyAPICall.executeRequest(Request.Method.GET, new VolleyAPICall.VolleyCallback() {
                    @Override
                    public void getResponse(JSONArray response) {
                        Log.e("VOLLEY","RES"+response);
                        try {
                            parsingFeaturedPropertiesData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
    // success call back for featured properties
    public void parsingFeaturedPropertiesData(JSONArray response ) throws JSONException {
        String[] image = new String [response.length()];
        String[] tittle = new String [response.length()];
        for(int i = 0;i<response.length();i++){
            JSONObject obj = new JSONObject();
            obj = response.getJSONObject(i);
            image[i]= obj.getString("Thumbnail");
            tittle[i] = obj.getString("title");
        }
        setProductimages(image,tittle);
    }
    //NotificationView
    public void setNotificationView(){
        notification_imgBtn = findViewById(R.id.notification_imgBtn);
        notification_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNorificationPopUp(v);
            }
        });
    }
    public void showNorificationPopUp(View v){
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.notification_list,null);
        ListView listView = layout.findViewById(R.id.notification_listView);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Congo! your request has been send....");
        list.add("Congo! your request has been send....");
        int images[] = {R.drawable.wifi_icon,
                R.drawable.featured_icon};
        setNotificationBadgeValue(images.length);
        ListApdapter listApdapter = new ListApdapter(list,this,images,"notification");
        listView.setAdapter(listApdapter);
        layout.measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);
        final PopupWindow popup = new PopupWindow(layout,FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,true);
        //popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.transparent));
        popup.showAsDropDown(v,5,5);
    }
    // setting notification badge value
    public void setNotificationBadgeValue(int value){
        TextView badge = findViewById(R.id.textOne);
        badge.setText(value+"");

    }
    //seting services adapter images
    public void setServicesimages(){
        recyclerView = findViewById(R.id.servicesRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ServicesAdapter(this,"service");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setAdapter(adapter);
    }
    // for Product Featured
    public void setProductimages(String[] images,String[] tittle){
        product_RecyclerView = findViewById(R.id.product_RecyclerView);
        product_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        product_RecyclerView.setLayoutManager(layoutManager);
        adapterOffer=new ServicesAdapter(this,"product",images,tittle);
        product_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        product_RecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        product_RecyclerView.setAdapter(adapterOffer);
    }
    //apicall for offer Image
    public void apiCallForofferImage(){
        home_firstImage = findViewById(R.id.home_firstImage);
        String url = "http://www.onistays.com/api/v2/homebanner";
        final VolleyAPICall volleyAPICall1 = new VolleyAPICall(this,url);
        volleyAPICall1.executeRequest(Request.Method.GET, new VolleyAPICall.VolleyCallback() {
                    @Override
                    public void getResponse(JSONArray response) {
                        Log.e("VOLLEY","RES"+response);
                        try {
                            parsingOfferImageData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
    // success call back for offer image
    public void parsingOfferImageData(JSONArray response ) throws JSONException {
        String[] image = new String [response.length()];
        //String[] tittle = new String [response.length()];
        for(int i = 0;i<response.length();i++){
            JSONObject obj = new JSONObject();
            obj = response.getJSONObject(i);
            image[i]= obj.getString("Image");
        }
         Picasso
                 .with(this)
                .load(image[2])
                .into(home_firstImage);
        progressBar.setVisibility(View.GONE);
        setOfferimages(image);
    }

    // for offers
    public void setOfferimages(String[] images){
        offerRecyclerView = findViewById(R.id.offerRecyclerView);
        offerRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        offerRecyclerView.setLayoutManager(layoutManager);
        adapterOffer=new ServicesAdapter(this,"offers",images);
        offerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        offerRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        offerRecyclerView.setAdapter(adapterOffer);
    }
    // for greeting according to time
    // Harpreet
    public void greeting(){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();        c.setTime(dt);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        String name = getIntent().getStringExtra("Name");

        if(hours>=1 && hours<=12){
            //Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
            greeting_textView.append("Good Morning "+name);
        }else if(hours>=12 && hours<=16){
            //Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
            greeting_textView.append("Good Afternoon "+name);
        }else if(hours>=16 && hours<=21){
            //Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
            greeting_textView.append("Good Evening "+name);
        }else if(hours>=21 && hours<=24){
            // Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
            greeting_textView.append("Good Night "+name);
        }
    }

    // for app bar
    // Harpreet
    public void appBar(){
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menuItem = navigationView.getMenu();
        MenuItem profileMenuItem = menuItem.findItem(R.id.profile);
        if (getIntent().getStringExtra("Name").toLowerCase().equals("guest")) {
             profileMenuItem.setTitle("Registration");
        } else {
            profileMenuItem.setTitle("Profile");
        }

        navigationView.setNavigationItemSelectedListener(this);

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
//        // Create your menu...
//
//        this.menu = menu;
//        //updateMenuTitles();
//        MenuItem profileMenuItem = menu.findItem(R.id.profile);
//        if (getIntent().getStringExtra("Name").toLowerCase().equals("guest")) {
//            profileMenuItem.setTitle("Registration");
//        } else {
//            profileMenuItem.setTitle("Profile");
//        }
//        return true;
//    }

    private void updateMenuTitles() {
        MenuItem profileMenuItem = menu.findItem(R.id.profile);
        if (getIntent().getStringExtra("Name").toLowerCase().equals("guest")) {
            profileMenuItem.setTitle("Registration");
        } else {
            profileMenuItem.setTitle("Profile");
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i ;
        if (id == R.id.profile) {
            i = new Intent(Home.this,UserRegistration.class);
            i.putExtra("NAME",getIntent().getStringExtra("Name"));
            startActivity(i);
            // Handle the camera action


        } else if (id == R.id.booking) {
            i = new Intent(Home.this,BookingTabbed.class);
            startActivity(i);


        } else if (id == R.id.invite) {
            i = new Intent(Home.this,ShareAndEarn.class);
            startActivity(i);

        }  else if (id == R.id.logout) {
            logoutApi();

        }else if (id == R.id.menu_callUs){
            askPermissionForCall();
        }else if (id == R.id.menu_emailUs){
            askPermissionForMail();
        }else if (id == R.id.services){
            i = new Intent(Home.this,History.class);
            startActivity(i);
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    private void logoutApi()
    {

        String url="http://www.onistays.com/oni-endpoint/user/logout";
        final VolleyAPICallJsonObject volleyAPICallJsonObject=new VolleyAPICallJsonObject(this,url);
        volleyAPICallJsonObject.executeRequest(Request.Method.POST, new VolleyAPICallJsonObject.VolleyCallback() {
            @Override
            public void getResponse(JSONObject response)
            {




            }

            @Override
            public void getError(VolleyError error)
            {

            }
        });

    }

    //email permission check
    public void askPermissionForMail(){
        Contact_Us contact_us = new Contact_Us(this);
        contact_us.emailNow("harpreet241608@gmail.com");
    }


    //call permission check

    public void askPermissionForCall(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    android.Manifest.permission.CALL_PHONE
            }, 8);
            return;
        }else {
            Contact_Us contact_us = new Contact_Us(this);
            contact_us.callNow("8882001245");
        }
    }
    public void dealOnClick(View v){
        Intent i = new Intent(Home.this,AllLocationsActivity.class);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 8:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //startActivity(makeCall);
                    Contact_Us contact_us = new Contact_Us(this);
                    contact_us.callNow("8882001245");
                }


        }
    }

}

