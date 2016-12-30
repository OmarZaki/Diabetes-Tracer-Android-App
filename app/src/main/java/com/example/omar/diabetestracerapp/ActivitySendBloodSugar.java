package com.example.omar.diabetestracerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.diabetestracerapp.data_model.Categories;
import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.rest_client.RestClient;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ActivitySendBloodSugar extends AppCompatActivity{

    TextView tvDate;
    TextView tvTime;
    Spinner spInput;
    Spinner spInputDec;
    ImageView ivHeart;
    Date currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_blood_sugar);

        tvDate = (TextView) findViewById(R.id.BloodSugar_fieldDate);
        tvTime = (TextView) findViewById(R.id.BloodSugar_fieldTime);
        spInput = (Spinner) findViewById(R.id.BloodSugar_input);
        spInputDec = (Spinner) findViewById(R.id.BloodSugar_input_dec);
        ivHeart = (ImageView) findViewById(R.id.BloodSugar_iconLevel);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers_array, R.layout.custom_spinner);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spInput.setAdapter(adapter);
        spInputDec.setAdapter(adapter);

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
        String value = spInput.getSelectedItem().toString()+"."+spInputDec.getSelectedItem().toString();
        if(!value.equals("")){
            categories.setValue(value);
            categories.setCategory_name_id(3);
            RestClient client = new RestClient(this);
            client.sendCategories(categories);
        } else {
            Toast.makeText(this, "Specify current blood sugar level.", Toast.LENGTH_SHORT).show();
        }
    }

}
