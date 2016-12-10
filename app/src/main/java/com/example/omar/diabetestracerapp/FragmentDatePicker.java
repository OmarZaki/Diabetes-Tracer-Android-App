package com.example.omar.diabetestracerapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.omar.diabetestracerapp.DataModel.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by OMAR on 12/9/2016.
 */

public class FragmentDatePicker extends DialogFragment{
    String selectedDate="";
    DatePicker datePicker ;
    EditText etDateText;




    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.date_picker_layout,null);
        datePicker= (DatePicker)view.findViewById(R.id.dpCalender);

        etDateText= (EditText) getActivity().findViewById(R.id.etDate);
        selectedDate=etDateText.getText().toString();
        if(!selectedDate.equals("")){
                Date d = User.ConvertStringToDateObject(selectedDate);
                Calendar calender = Calendar.getInstance();
                calender.setTime(d);
                datePicker.init(calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH),null);

        }
        builder.setView(view);


        builder.setMessage("Choose Your birth date ")
                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year =  datePicker.getYear();

                        Calendar calendar = User.ConvertIntegersToCalenderObject(day,month,year);
                        Date date = calendar.getTime();

                        etDateText.setText(User.ConvertDateToString(date));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
