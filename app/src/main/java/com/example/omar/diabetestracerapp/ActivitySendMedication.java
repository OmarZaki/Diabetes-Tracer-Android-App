package com.example.omar.diabetestracerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.diabetestracerapp.data_model.Categories;
import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.rest_client.RestClient;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ActivitySendMedication extends AppCompatActivity {

    TextView tvDate;
    TextView tvTime;
    EditText tvInput;
    Date currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_medication);

        tvDate = (TextView) findViewById(R.id.Medication_fieldDate);
        tvTime = (TextView) findViewById(R.id.Medication_fieldTime);
        tvInput = (EditText) findViewById(R.id.Medication_input);

        /**
         * To set date and the time
         *
         */
        currentDate = new Date();
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

    public void sendOnClick(View v){
        Categories categories = new Categories();
        categories.setDate_time(currentDate);
        String value = tvInput.getText().toString();
        if(!value.equals("")){
            categories.setValue(value);
            categories.setCategory_name_id(2);
            RestClient client = new RestClient(this);
            client.sendCategories(categories);
        } else {
            Toast.makeText(this, "Specify taken medication.", Toast.LENGTH_SHORT).show();
        }
    }
}
