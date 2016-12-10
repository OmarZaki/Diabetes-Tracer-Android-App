package com.example.omar.diabetestracerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.omar.diabetestracerapp.DataModel.User;


public class ActivityRegistration extends AppCompatActivity{
    public static final String DATE_BUNDLE_TAG="DATE";

    Spinner spinnerType ;
    Button btnRegister;
    Button btnCancel;
    ImageButton btnPickDate;
    EditText etSelectedBirthDate;
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etAddress;
    EditText etPassword;
    EditText etPasswordAgain;
    EditText etPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //UI binding
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnCancel=(Button) findViewById(R.id.btnCancel);
        btnPickDate= (ImageButton)findViewById(R.id.btnPick);
        etSelectedBirthDate=(EditText)findViewById(R.id.etDate);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etAddress= (EditText)findViewById(R.id.etAddress) ;
        etPassword= (EditText)findViewById(R.id.etPassword1) ;
        etPasswordAgain=(EditText) findViewById(R.id.etPassword2);
        etPhoneNumber=(EditText) findViewById(R.id.etPhone);


        /**
         * Actions and Listeners
         */
        // datePicker dialog
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentDatePicker fdb = new FragmentDatePicker();
                fdb.show(getFragmentManager(),"TAGE");

            }
        });

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
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    public User SendRegisrationRequest(User currentUser){
        User user = new User();
        user.setFirstName(etFirstName.getText().toString());
        user.setLastName(etLastName.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setAddress(etAddress.getText().toString());


        return new User() ;

    }
}
