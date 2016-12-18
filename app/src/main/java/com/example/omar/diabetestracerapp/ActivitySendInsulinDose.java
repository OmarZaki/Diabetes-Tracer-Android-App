package com.example.omar.diabetestracerapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar.diabetestracerapp.data_model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Activity Main Code
 */
public class ActivitySendInsulinDose extends AppCompatActivity {
    ImageView ivIconDateSendInsulinDose;
    ImageView ivIconDoseSendInsulinDose;
    TextView tvDate;
    TextView tvTime;
    TextView tvQuantity;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_insulin_dose);
        /**
         * Link user interface elements
         *
         */
        ivIconDateSendInsulinDose = (ImageView) findViewById(R.id.ivIconDateSendInsulinDose);
        ivIconDoseSendInsulinDose = (ImageView) findViewById(R.id.ivIconDoseSendInsulinDose);
        tvDate= (TextView) findViewById(R.id.tvDateSendInsulinDose);
        tvTime= (TextView) findViewById(R.id.tvTimeSendInsulinDose);
        tvQuantity=(TextView) findViewById(R.id.tvDoseQuantitySendInsulinDose);
        btnSend = (Button) findViewById(R.id.btnSendInsulinDose);

        /**
         * To set date and the time
         *
         */
        Date currentDate = new Date();
        String currentDateString = User.ConvertDateToString(currentDate);
        tvDate.setText(currentDateString);
        String time = getCurrentTime(currentDate);
        tvTime.setText(time);
        /**
         * Set the onClick
         */
        ivIconDoseSendInsulinDose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentAddQuantityField fragmentAddQuantityField = new FragmentAddQuantityField();
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

    public void sendOnClick(final View view) {
        //TODO: Rest function for sending Insulin dose
    }
}
