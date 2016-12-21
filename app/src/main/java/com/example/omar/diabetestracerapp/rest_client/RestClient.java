package com.example.omar.diabetestracerapp.rest_client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omar.diabetestracerapp.ActivityLogin;
import com.example.omar.diabetestracerapp.ActivityMain;
import com.example.omar.diabetestracerapp.data_model.Meal;
import com.example.omar.diabetestracerapp.data_model.InsulinDose;
import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.database.DataSource;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by OMAR on 12/10/2016.
 */

/**
 * http://localhost:8080/ServerApp/app/users/register
 */
public class RestClient {
    Activity activity;
    DataSource dataSource;

    /**
     * Empty Constructor
     */
    public RestClient(Activity activity) {
        this.activity = activity;
        dataSource = new DataSource(activity.getBaseContext());
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
     */
    public void HelloTest() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this.activity);
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
     * @param user
     * @return
     */
    public void registrationRequest(final User user) {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this.activity);
        String url = get_base_HTTPs_URL() + "/users/register";
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, url, User.toJsonObject(user),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has(User._ID)) {
                                // Insert into database ;
                                dataSource = new DataSource(activity);
                                dataSource.cleanTables(); // users table should be cleaned before insert the new user.
                                dataSource.insertUserToDataBase(User.convertJsonToUser(response.toString()));
                                Intent intent = new Intent(activity.getBaseContext(), ActivityLogin.class);
                                intent.putExtra(User._USER_ONFO_PUT_EXTRA_STRING, response.toString());
                                activity.startActivity(intent);

                            } else {
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
     * Include check if the user email is existed
     *
     * @param user
     */
    public void registration(final User user) {
        //  1: Request to check if the user is already exist.
        //  2: Request to register new User.

        RequestQueue queue = Volley.newRequestQueue(this.activity);
        String url = get_base_HTTPs_URL() + "/users/IsEmailExist";
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, url, User.toJsonObject(user),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (!response.getBoolean("result")) {
                                registrationRequest( user);

                            } else {
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

    /**
     * @param user
     */
    public void logInUser(final User user) {

        RequestQueue queue = Volley.newRequestQueue(this.activity);
        String url = get_base_HTTPs_URL() + "/users/login";
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, url, User.toJsonObject(user),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.has("result")) {

                                dataSource = new DataSource(activity);
                                if(dataSource.retrieveUserFromDataBase()==null) {
                                    User userfound = User.convertJsonToUser(response.toString());
                                    dataSource.insertUserToDataBase(userfound);
                                }
                                activity.finish();
                                Intent intent = new Intent(activity.getBaseContext(), ActivityMain.class);
                                intent.putExtra(ActivityLogin.LOGIN_INDICATOR, true);
                                activity.startActivity(intent);


                            } else {
                                Toast.makeText(activity.getBaseContext(), "registration Failed !", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getBaseContext(), error.getMessage().toString(), Toast.LENGTH_SHORT);
            }
        });

        queue.add(strReq);

    }

    /**
     * Send Insulin dose .
     *
     * @param insulinDose
     */
    public void SendInsulinDose(InsulinDose insulinDose) {

        RequestQueue queue = Volley.newRequestQueue(this.activity);
        String url = get_base_HTTPs_URL() + "/users/setTakenTrue";
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, url, InsulinDose.toJsonObject(insulinDose),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("result")) {
                                Toast.makeText(activity.getBaseContext(), "Dose has been Sent ! ", Toast.LENGTH_SHORT).show();
                                activity.finish();
                            } else {
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
     * Synchronize data.
     *
     * @param user
     */
    public void syncData(User user) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = get_base_HTTPs_URL() + "/users/allDoses";
        CustomJsonArrayRequest arrayRequest = new CustomJsonArrayRequest(Request.Method.POST, url, User.toJsonObject(user), new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i("RESPONSE", response.toString());
                dataSource.cleanTable(InsulinDose._InsulinDose_TABLE);
                List<InsulinDose> insulinDoses = InsulinDose.convertJsonToList(response.toString());
                dataSource.insertListOfInsulinDoses(insulinDoses);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR_VOLLEY", "ERROR in the response ");
            }
        });

        queue.add(arrayRequest);

        url = get_base_HTTPs_URL() + "/users/allMeals";
        arrayRequest = new CustomJsonArrayRequest(Request.Method.POST, url, User.toJsonObject(user), new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i("RESPONSE", response.toString());
                dataSource.cleanTable(Meal._Meal_TABLE);
                List<Meal> meals = Meal.convertJsonToList(response.toString());
                dataSource.insertListOfMeals(meals);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR_VOLLEY", "ERROR in the response ");
            }
        });

        queue.add(arrayRequest);

        // TODO 3: Sync Categories .
        // TODO 5: Sync Appointment.
        // TODO 6: Sync Messages.




    }

    public void sendMeal(final Meal meal){
        // TODO send a log in request to the server to check if the user is registered.
        // Instantiate the RequestQueue.
        Log.d("[textPic","Getting ready to explode");
        DataSource datasource = new DataSource(this.activity);
        RequestQueue queue = Volley.newRequestQueue(this.activity);
        String url = get_base_HTTPs_URL() + "/users/meal";
        String filepath = meal.getImage();
        Bitmap bmp = BitmapFactory.decodeFile(meal.getImage());
        File f = new File(filepath);
        int size = (int) f.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(f));
            buf.read(bytes, 0, bytes.length);
            buf.close();
            meal.setImage(Base64.encodeToString(bytes,Base64.NO_WRAP));
            User user = datasource.retrieveUserFromDataBase();
            meal.setUsers_id(user.getId());
            Gson gson = new Gson();
            //TODO: Send user for authentication
            JSONObject json = new JSONObject(gson.toJson(meal));
            meal.setImage(filepath);
            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, url, json,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.has("result")) {
                            //TODO:  Insert into database ;
                            DataSource dataSource =new DataSource(activity);
                            dataSource.insertMealToDataBase(meal);
                            Toast.makeText(activity.getBaseContext(), "Meal sent!", Toast.LENGTH_LONG).show();
                            activity.finish();
            //                                dataSource.open();
            //                                // clean all tables should be done when we log out
            //                                dataSource.cleanTable(User._USER_TABLE); // users table should be cleaned before insert the new user.
            //                                dataSource.insertUserToDataBase(User.convertJsonToUser(response.toString()));
            //                                dataSource.close();
            //


                        }else{
                            Toast.makeText(activity.getBaseContext(), "Sending Meal failed", Toast.LENGTH_SHORT).show();

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
        } catch (JSONException e) {
            e.printStackTrace();
            //TODO: Remove this log
            Log.e("[KBM]","IT BLEW UP SON");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
