package com.example.omar.diabetestracerapp;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.database.DataSource;


public class ActivityLogo extends AppCompatActivity {
    TextView tvTitle;
    ProgressBar pb;
    DataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_logo);

        // link UI with elements
        pb = (ProgressBar) findViewById(R.id.pb_waiting);
        tvTitle = (TextView) findViewById(R.id.tv_diabetesIconTitle);

        // Link
        new CountDownTimer(4000,1000){
            @Override
            public void onTick(long l) {
                /**
                 * Check the connection and precondition for the application to run.
                 */

                /**
                 * Get User from dataBase
                 */
            }
            @Override
            public void onFinish() {
                dataSource = new DataSource(getBaseContext());
                dataSource.open();
                User user = dataSource.retrieveUserFromDataBase();
                dataSource.close();
                finish();
                if(user!=null){
                    Intent intent = new Intent(getBaseContext(),ActivityLogin.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getBaseContext(),ActivityIntro.class);
                    startActivity(intent);
                }



            }
        }.start();

    }






}
