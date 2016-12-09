package com.example.omar.diabetestracerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ActivityRegistration extends AppCompatActivity{

    Spinner spinnerType ;
    Button btnRegister;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //UI binding
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnCancel=(Button) findViewById(R.id.btnCancel);


        //populate the spinner with user choices
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.diabetes_types,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
