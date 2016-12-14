package com.example.omar.diabetestracerapp.rest_client;

import org.json.JSONObject;

/**
 * Created by OMAR on 12/13/2016.
 */

public interface VolleyCallback {
    void onSuccessResponse(Boolean result);
    void onSuccessJsonResponse(JSONObject response);
}
