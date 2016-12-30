package com.example.omar.diabetestracerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.database.DataSource;
import com.example.omar.diabetestracerapp.rest_client.RestClient;
import com.example.omar.diabetestracerapp.shared_preference.SharedPreferenceMethods;


/**
 * A login screen that offers login via email/password.
 */
public class ActivityLogin extends AppCompatActivity {
    public static final String LOGIN_INDICATOR = "Login";
    /**
     * --------> Back-end Logic <-------
     **/
    DataSource dataSource;
    RestClient restClient;
    SharedPreferenceMethods sharedPreferenceMethods;
    /**
     * ------->  End <-------
     **/

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button btnLogin;
    private Button btnRegistration;
    private CheckBox cbRememberMe;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // setup user interface elements;
        SetUserInterfaceElements();


        /** ------> Back End Logic <------ **/
        /*
        1. Get User from database .
        2. Since we came over here, this means that user has already registered in the the app
                            -> setup the user email and password.
         */
        restClient = new RestClient(this);
        dataSource = new DataSource(getBaseContext());
        sharedPreferenceMethods = new SharedPreferenceMethods(this);
        dataSource.open();
        User user = dataSource.retrieveUserFromDataBase();
        dataSource.close();
        if (user != null) {
            if (sharedPreferenceMethods.retrieveRmemeberMeIndicator()) {
                restClient.logInUser(user);
            } else {

                mEmailView.setText(user.getEmail());
                mPasswordView.setText(user.getPassword());

            }
        }
        /** --------- > End Logic <------- **/
    }


    /**
     * Set up user interface Elements and Actions
     */
    private void SetUserInterfaceElements() {

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        cbRememberMe = (CheckBox) findViewById(R.id.checkBox);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        btnRegistration = (Button) findViewById(R.id.btnRegistration);
        btnRegistration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ActivityRegistration.class);
                startActivity(intent);
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // if the remember me is checked, we don't need to show up Activity Login.

            if (cbRememberMe.isChecked()) {
                sharedPreferenceMethods.storeRememberMeIndicator(true);
            }
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            restClient.logInUser(user);


        }
    }


    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 2;
    }


}

