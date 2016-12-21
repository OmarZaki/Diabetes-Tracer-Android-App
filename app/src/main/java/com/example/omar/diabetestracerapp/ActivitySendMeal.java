package com.example.omar.diabetestracerapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.diabetestracerapp.data_model.Meal;
import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.rest_client.RestClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ActivitySendMeal extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 1;
    private static final int REQ_CODE_TAKE_PICTURE = 2;
    private Date currentDate;
    private TextView tvDate;
    private TextView tvTime;
    private EditText etTitle;
    private EditText etDescription;
    private ImageView imgTakePhoto;
    private String mCurrentPhotoPath;
    private boolean photoTaken = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_meal);
        tvDate = (TextView) findViewById(R.id.Meal_fieldDate);
        tvTime = (TextView) findViewById(R.id.Meal_fieldTime);
        etTitle = (EditText) findViewById(R.id.Meal_inputTitle);
        etDescription = (EditText) findViewById(R.id.Meal_inputDescription);
        imgTakePhoto = (ImageView) findViewById(R.id.Meal_iconMeal);

        /**
         * To set date and the time
         */
        currentDate = new Date();
        String currentDateString = User.ConvertDateToString(currentDate);
        tvDate.setText(currentDateString);
        String time = getCurrentTime(currentDate);
        tvTime.setText(time);

        // Here, thisActivity is the current activity
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showMessageOKCancel("You need to allow access to Camera and Files.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST);
                            }
                        });
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            //Nothing happens!
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission
                    imgTakePhoto.setEnabled(false);
                    imgTakePhoto.setImageResource(R.drawable.ic_denied);
                    Toast.makeText(this, "Permissions to access camera and write files required, picture cannot be taken.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.Meal_iconMeal);
            imageView.setImageURI(Uri.parse(mCurrentPhotoPath));
            photoTaken = true;
        }
    }

    private String getCurrentTime(Date currentDate) {
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(currentDate);   // assigns calendar to given date
        long hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        long minutes = calendar.get(Calendar.MINUTE);        // get current format;
        return hours+":"+ minutes;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void takePhoto(View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            Log.d("[takePhoto]", "Got up to taking pic");
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.diabetestracerapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.d("[takePhoto]","About to send Intent");
                startActivityForResult(takePictureIntent, REQ_CODE_TAKE_PICTURE);
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    public void sendOnClick(View v){
        Meal meal = new Meal();
        meal.setDate_time(currentDate);
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        if(!title.equals("") && !description.equals("") && photoTaken){
            meal.setType(title);
            meal.setDescription(description);
            meal.setImage(mCurrentPhotoPath);
            RestClient client = new RestClient(this);
            client.sendMeal(meal);
        } else {
            Toast.makeText(this, "Fill all required fields.", Toast.LENGTH_SHORT).show();
        }
    }
}
