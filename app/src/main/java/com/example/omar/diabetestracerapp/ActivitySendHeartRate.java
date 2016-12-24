package com.example.omar.diabetestracerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.diabetestracerapp.data_model.Categories;
import com.example.omar.diabetestracerapp.data_model.Meal;
import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.rest_client.RestClient;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ActivitySendHeartRate extends AppCompatActivity {

    TextView tvDate;
    TextView tvTime;
    TextView tvInput;
    ImageView ivHeart;
    Date currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_heart_rate);

        tvDate = (TextView) findViewById(R.id.HeartRate_fieldDate);
        tvTime = (TextView) findViewById(R.id.HeartRate_fieldTime);
        tvInput = (TextView) findViewById(R.id.HeartRate_input);
        ivHeart = (ImageView) findViewById(R.id.HeartRate_iconHeart);

        /**
         * To set date and the time
         *
         */
        currentDate = new Date();
        String currentDateString = User.ConvertDateToString(currentDate);
        tvDate.setText(currentDateString);
        String time = getCurrentTime(currentDate);
        tvTime.setText(time);

        ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentAddQuantityField fragmentAddQuantityField = new FragmentAddQuantityField();
                fragmentAddQuantityField.setMax(120);
                fragmentAddQuantityField.setMin(40);
                fragmentAddQuantityField.setValue(70);
                fragmentAddQuantityField.setTitle(R.string.heart_rate_quantity_dialog_title);
                fragmentAddQuantityField.setMessage(R.string.heart_rate_quantity_dialog);
                fragmentAddQuantityField.setTextView(R.id.HeartRate_input);
                fragmentAddQuantityField.show(getFragmentManager(),"TAG");
            }
        });
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
            categories.setCategory_name_id(1);
            RestClient client = new RestClient(this);
            client.sendCategories(categories);
        } else {
            Toast.makeText(this, "Specify current heart rate.", Toast.LENGTH_SHORT).show();
        }
    }
}
