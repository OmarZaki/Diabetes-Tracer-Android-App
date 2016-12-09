package com.example.omar.diabetestracerapp.asyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.omar.diabetestracerapp.DataModel.User;

/**
 * Created by OMAR on 12/9/2016.
 */

public class RegistrationRequest  extends AsyncTask<Object,Integer, Object> {
    Activity ThisActivity ;
    ProgressDialog progressDialog;
    User currentUser;

    public RegistrationRequest(Activity thisActivity, User user){
        super();
        this.ThisActivity=thisActivity;
        this.currentUser=user;
    }


    protected void onPreExecute(){

    }

    @Override
    protected Object doInBackground(Object... params) {

    int i = 0;

    return new Object();
    }

    @Override
    protected void onPostExecute(Object response) {

    }
}
