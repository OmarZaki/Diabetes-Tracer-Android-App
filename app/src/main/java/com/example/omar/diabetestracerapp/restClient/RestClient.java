package com.example.omar.diabetestracerapp.restClient;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omar.diabetestracerapp.DataModel.User;


import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by OMAR on 12/10/2016.
 */

/**
 * http://localhost:8080/ServerApp/app/users/register
 */
public class RestClient {
    /**
     * Empty Constructor
     */
    public RestClient() {
    }

    /**
     * Constants String
     * http://10.0.2.2:8080/ServerApp/app/
     */
    static final String _LOCAL_HOST_IP = "10.0.2.2";
    static final String _HTTP = "http";
    static final String _HTTP_PORT = "8080";
    static final String _BASE_APP_NAME = "ServerApp/app";
    /**
     * build the bas Https url
     * @return return the base url as String
     */
    private String get_base_HTTPs_URL() {
        return _HTTP + "://" + _LOCAL_HOST_IP + ":" + _HTTP_PORT + "/" + _BASE_APP_NAME;
    }

    public void HelloTest(final Activity activity)   {
        User result = null;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = get_base_HTTPs_URL()+"/hello";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(activity.getBaseContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    public Boolean RegistrationRequest(final Activity activity,User user){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = get_base_HTTPs_URL()+"/users/register";
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, url, User.toJsonObject(user),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(activity.getBaseContext(),response.toString(),Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              // Log.i("ERROR",error.getLocalizedMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(strReq);
        return false;
    }




}
