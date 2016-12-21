package com.example.omar.diabetestracerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.omar.diabetestracerapp.data_model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SendHeartRateActivity extends AppCompatActivity {

    TextView tvDate;
    TextView tvTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_heart_rate);

        tvDate = (TextView) findViewById(R.id.HeartRate_fieldDate);
        tvTime = (TextView) findViewById(R.id.HeartRate_fieldTime);

        /**
         * To set date and the time
         *
         */
        Date currentDate = new Date();
        String currentDateString = User.ConvertDateToString(currentDate);
        tvDate.setText(currentDateString);
        String time = getCurrentTime(currentDate);
        tvTime.setText(time);
    }

    private String getCurrentTime(Date currentDate) {

        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(currentDate);   // assigns calendar to given date
        long hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        long minutes = calendar.get(Calendar.MINUTE);        // get current format;
        return hours+":"+ minutes;
    }
}
