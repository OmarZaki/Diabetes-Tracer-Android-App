package com.example.omar.diabetestracerapp.rest_client;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omar.diabetestracerapp.ActivityLogin;
import com.example.omar.diabetestracerapp.ActivityMain;
import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.database.DataSource;


import org.json.JSONObject;


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
     *
     * @return return the base url as String
     */
    private String get_base_HTTPs_URL() {
        return _HTTP + "://" + _LOCAL_HOST_IP + ":" + _HTTP_PORT + "/" + _BASE_APP_NAME;
    }

    /**
     * Get the full url Path for the Restful Api server App;
     *
     * @param activity
     */
    public void HelloTest(final Activity activity) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = get_base_HTTPs_URL() + "/hello";

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

    /**
     * Register New Patient to the System.
     *
     * @param activity
     * @param user
     * @return
     */
    public void registrationRequest(final Activity activity, final User user) {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = get_base_HTTPs_URL() + "/users/register";
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, url, User.toJsonObject(user),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has(User._ID)) {
                                // Insert into database ;
                                DataSource dataSource =new DataSource(activity);
                                dataSource.open();
                                dataSource.cleanTable(User._USER_TABLE); // users table should be cleaned before insert the new user.

                                Log.i("TAG", response.toString());
                                dataSource.insertUserToDataBase(User.convertJsonToUser(response.toString()));
                                Log.i("TAG", "DATABASE HAS BEEN CREATED!");
                                dataSource.close();

                                Intent intent = new Intent(activity.getBaseContext(), ActivityLogin.class);
                                intent.putExtra(User._USER_ONFO_PUT_EXTRA_STRING, response.toString());
                                activity.startActivity(intent);

                            }else{
                                Toast.makeText(activity.getBaseContext(), "registration Failed !", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.i("ERROR",error.getLocalizedMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(strReq);

    }

    /**
     *
     * Include check if the user email is existed
     * @param activity
     * @param user
     */
    public void registration(final Activity activity, final User user) {
        //  1: Request to check if the user is already exist.
        //  2: Request to register new User.

        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = get_base_HTTPs_URL() + "/users/IsEmailExist";
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, url, User.toJsonObject(user),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                        if(!response.getBoolean("result")){
                            registrationRequest(activity,user);

                        }else{
                            Toast.makeText(activity.getBaseContext(), "Email is already existed", Toast.LENGTH_SHORT).show();
                        }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.i("ERROR",error.getLocalizedMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonReq);


    }

    public void logInUser(final Activity activity, final User user){
        // TODO send a log in request to the server to check if the user is registered.
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = get_base_HTTPs_URL() + "/users/login";
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, url, User.toJsonObject(user),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(!response.has("result")) {
                                // Insert into database ;
                                DataSource dataSource =new DataSource(activity);
//                                dataSource.open();
//                                // clean all tables should be done when we log out
//                                dataSource.cleanTable(User._USER_TABLE); // users table should be cleaned before insert the new user.
//                                dataSource.insertUserToDataBase(User.convertJsonToUser(response.toString()));
//                                dataSource.close();
//
                                Intent intent = new Intent(activity.getBaseContext(), ActivityMain.class);
                                activity.startActivity(intent);

                            }else{
                                Toast.makeText(activity.getBaseContext(), "registration Failed !", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.i("ERROR",error.getLocalizedMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(strReq);

    }
}
