package com.example.codemaven3015.onistayandroiddev;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeMaven3015 on 3/5/2018.
 */

public class VolleyAPICall {
        String JsonURL ;
        Context context;
        RequestQueue requestQueue;
        private Map<String, String> header;

public VolleyAPICall(Context context, String JsonURL) {
        this.JsonURL = JsonURL;
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        header = new HashMap<>();

    }

    public VolleyAPICall(Context context, String JsonURL,Map<String, String> header) {
        this.JsonURL = JsonURL;
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        this.header = header;

    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public void executeRequest(int method, final VolleyCallback callback) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(method, JsonURL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.getResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ONI","INSIDE ERROR CALLBACK");
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return header;
            }


        };
        requestQueue.add(jsonArrayRequest);

    }

    public interface VolleyCallback {
        public void getResponse(JSONArray response);
    }
}
