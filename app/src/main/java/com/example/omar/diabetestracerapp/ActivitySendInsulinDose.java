package com.example.omar.diabetestracerapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar.diabetestracerapp.data_model.InsulinDose;
import com.example.omar.diabetestracerapp.database.DataSource;
import com.example.omar.diabetestracerapp.rest_client.RestClient;

import java.util.Date;

/**
 * Activity Main Code
 */
public class ActivitySendInsulinDose extends AppCompatActivity {
    /** ----> Back-end instances <--- **/
    DataSource dataSource;
    RestClient restClient;
    /** -----> end <-------*/


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
        /** -------> Back-End Logic <------ **/
        dataSource= new DataSource(this);
        restClient= new RestClient(this);
        /** -------> end <-------- */

        /**
         * Link user interface elements
         *
         */
        setupUserInterfaceElements();
        /**
         * To set date and the time
         *
         */

        Date currentDate = new Date();
        InsulinDose  currentInsulinDose= dataSource.retrieveCurrentInsulinDose(currentDate);
        if(currentInsulinDose!=null) {
            String currentDateString = InsulinDose.ConvertDateToString(currentInsulinDose.getDate_time());
            tvDate.setText(currentDateString);
            String time = InsulinDose.getCurrentTime(currentInsulinDose.getDate_time());
            tvTime.setText(time);
            tvQuantity.setText(String.valueOf(currentInsulinDose.getQuantity()) + "ML");
            /**
             * Set the onClick
             */
            ivIconDoseSendInsulinDose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentAddQuantityField fragmentAddQuantityField = new FragmentAddQuantityField();
                    fragmentAddQuantityField.setMax(1000);
                    fragmentAddQuantityField.setMin(0);
                    fragmentAddQuantityField.setTitle(R.string.insulin_dose_quantity_dialog_title);
                    fragmentAddQuantityField.setMessage(R.string.insulin_dose_quantity_dialog);
                    fragmentAddQuantityField.setTextView(R.id.tvDoseQuantitySendInsulinDose);
                    fragmentAddQuantityField.setUnit(R.string.insulin_dose_unit);
                    fragmentAddQuantityField.show(getFragmentManager(), "TAG");
                }
            });
        } else {
            tvDate.setText("No Insulin Dose");
            tvTime.setText("");
            tvQuantity.setText("N/A");
            btnSend.setEnabled(false);
        }
    }
    private void setupUserInterfaceElements(){
        ivIconDateSendInsulinDose = (ImageView) findViewById(R.id.ivIconDateSendInsulinDose);
        ivIconDoseSendInsulinDose = (ImageView) findViewById(R.id.ivIconDoseSendInsulinDose);
        tvDate= (TextView) findViewById(R.id.tvDateSendInsulinDose);
        tvTime= (TextView) findViewById(R.id.tvTimeSendInsulinDose);
        tvQuantity=(TextView) findViewById(R.id.tvDoseQuantitySendInsulinDose);
        btnSend = (Button) findViewById(R.id.btnSendInsulinDose);

    }


    public void sendOnClick(final View view) {

        //TODO1: get current date InsulinDose Object from database;
        InsulinDose insulinDose=dataSource.retrieveCurrentInsulinDose(new Date());
        //TODO2: SetTaken(True).
        insulinDose.setTaken(true);
        dataSource.updateInsulinDoseRecord(insulinDose);
        //TODO3: Send request to server to change this data;
        restClient.SendInsulinDose(insulinDose);
        //TODO 4: if request success -> done.

        //TODO 5: if request not -> mark user as dirty flag;


    }
}
