package com.example.omar.diabetestracerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.omar.diabetestracerapp.auxiliary.TabsPagerAdapter;
import com.example.omar.diabetestracerapp.data_model.User;
import com.example.omar.diabetestracerapp.database.DataSource;
import com.example.omar.diabetestracerapp.rest_client.RestClient;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RestClient restClient;
    DataSource dataSource;
    User currentUser;

    TabLayout tabLayout;
    TextView tvPatientName;
    TextView tvPatientEmail;
    TextView tvFirstName;

    TextView tvPhone;
    TextView tvBirthdate;
    TextView tvType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Set User Interface Elements
         */
        /** -------> Back-end Logic <------ **/
        restClient = new RestClient(this);
        dataSource = new DataSource(this);
        currentUser = dataSource.retrieveUserFromDataBase();
        if (currentUser != null) {
            Boolean login = getIntent().getExtras().getBoolean(ActivityLogin.LOGIN_INDICATOR);
            if (login) {
                restClient.syncData(currentUser);
            }


        }




        /** ------------ end -------------**/
        setUserInterfaceElements();
    }

    /**
     * Set user interface Elements.
     */
    private void setUserInterfaceElements() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);


            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                tvPatientEmail = (TextView) findViewById(R.id.tvPatientEmail);
                tvPatientName=(TextView) findViewById(R.id.tvPatientName);
                // get menu from navigationView


                tvPatientName.setText(currentUser.getFirstName());
                tvPatientEmail.setText(currentUser.getEmail());
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        MenuItem miUser= menu.findItem(R.id.nav_name);
        MenuItem miPhone=  menu.findItem(R.id.nav_phone);
        MenuItem miBirthdate= menu.findItem(R.id.nav_birth_date);
        MenuItem miType= menu.findItem(R.id.nav_type);

        miUser.setTitle(currentUser.getFirstName()+" "+ currentUser.getLastName());
        miPhone.setTitle(currentUser.getPhoneNumber());
        miBirthdate.setTitle(User.ConvertDateToString(currentUser.getBirthDate()));
        miType.setTitle((currentUser.getType()) ? "Type 1" : "Type 2");
        setupTablayout();

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        /***
                         * Do stuff. In this case it just displayed a toast,
                         * but other things can be done.
                         * Leaving this here as a reminder.
                         */
                        /*
                        Context context = getApplicationContext();
                        CharSequence text = "Hello toast!";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, "Tab 1", duration);
                        toast.show();
                        */
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_name) {
            // Handle the camera action
        } else if (id == R.id.nav_phone) {

        } else if (id == R.id.nav_birth_date) {

        } else if (id == R.id.nav_type) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            // clean the database ;
            finish();
            dataSource.deleteDatabase(getBaseContext());
            Intent LogoutIntent = new Intent(getBaseContext(), ActivityIntro.class);
            startActivity(LogoutIntent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupTablayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }


}
