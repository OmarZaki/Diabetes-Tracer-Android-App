package com.example.omar.diabetestracerapp.asyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.omar.diabetestracerapp.DataModel.User;
import com.example.omar.diabetestracerapp.restClient.RestClient;

import java.io.IOException;

/**
 * Created by OMAR on 12/9/2016.
 */

public class TaskRegistrationRequest extends AsyncTask<User,Boolean, Boolean> {
    Activity ThisActivity ;
    ProgressDialog progressDialog;
    User currentUser;

    public TaskRegistrationRequest(Activity thisActivity, User user){
        super();
        this.ThisActivity=thisActivity;
    }

    protected void onPreExecute(){

    }

    @Override
    protected Boolean doInBackground(User... params) {
        Boolean b = false;
//        try {
////            RestClient task = new RestClient();
////            b = task.RegisterNewUser(params[0]);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return b;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        Toast.makeText(this.ThisActivity,response.toString(),Toast.LENGTH_SHORT);
    }
}
