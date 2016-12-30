package com.example.omar.diabetestracerapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.rest_client.RestClient;

import java.util.Date;


public class ActivityRegistration extends AppCompatActivity {


    public static final String DATE_BUNDLE_TAG = "DATE";
    public Date selectedDateObject;
    Boolean Result= false;
    /**
     * User Interface Elements
     */
    Spinner spinnerType;
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
        btnRegister = (Button) findViewById(R.id.btnRegisterNewUser);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnPickDate = (ImageButton) findViewById(R.id.btnPick);
        etSelectedBirthDate = (EditText) findViewById(R.id.etDate);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPassword = (EditText) findViewById(R.id.etPassword1);
        etPasswordAgain = (EditText) findViewById(R.id.etPassword2);
        etPhoneNumber = (EditText) findViewById(R.id.etPhone);


        /**
         * Actions and Listeners
         */
        // datePicker dialog
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentDatePicker fdb = new FragmentDatePicker();
                fdb.show(getFragmentManager(), "TAGE");

            }
        });

        //populate the spinner with user choices
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.diabetes_types, R.layout.support_simple_spinner_dropdown_item);
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
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = collectUserInfo();
                sendRegistrationRequest((Activity) view.getContext(), user);
            }
        });
    }

    /**
     * collect the user info from the User Interface fields
     *
     * @return
     */
    private User collectUserInfo() {
        User user = new User();
        String password1 = etPassword.getText().toString();
        String password2 = etPasswordAgain.getText().toString();
        if (!password1.equals(password2)) {
            Toast.makeText(getBaseContext(), "Password is not matched ! ", Toast.LENGTH_SHORT).show();
        } else {
            String email = etEmail.getText().toString();
            if (!User.validateEmailFormat(email)) {
                Toast.makeText(getBaseContext(), "Email is not formatted well ! ", Toast.LENGTH_SHORT).show();
            } else {
                user.setFirstName(etFirstName.getText().toString());
                user.setLastName(etLastName.getText().toString());
                user.setEmail(etEmail.getText().toString());
                user.setPassword(password1);
                user.setAddress(etAddress.getText().toString());
                user.setAdmin(false);
                user.setCreationDate(new Date());
                user.setPhoneNumber(etPhoneNumber.getText().toString());
                user.setToken("");
                String type = spinnerType.getSelectedItem().toString();
                if (type.equals("Type 1")) {
                    user.setType(true);
                } else {
                    user.setType(false);
                }

                user.setBirthDate(User.ConvertStringToDateObject(etSelectedBirthDate.getText().toString()));
            }
        }
        return user;
    }


    /**
     * Collect the information
     *
     * @param activity
     * @return
     */
    public void sendRegistrationRequest(Activity activity, User user ) {
        RestClient client = new RestClient(this);
        client.registration(user);
    }




}
