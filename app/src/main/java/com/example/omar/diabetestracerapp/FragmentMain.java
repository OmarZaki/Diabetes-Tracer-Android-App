package com.example.omar.diabetestracerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar.diabetestracerapp.data_model.InsulinDose;
import com.example.omar.diabetestracerapp.database.DataSource;
import com.example.omar.diabetestracerapp.notification_service.DoseAlarmReceiver;
import com.example.omar.diabetestracerapp.rest_client.RestClient;
import com.example.omar.diabetestracerapp.shared_preference.SharedPreferenceMethods;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Date;


/**
 * This text exists for push purposes
 */

/**

 */
public class FragmentMain extends android.support.v4.app.Fragment {
    /**
     * ---------> Back-End Code <-----------
     */
    DoseAlarmReceiver alarm; // AlarmReceiver for the NotificationService
    DataSource dataSource;
    RestClient restClient;
    InsulinDose currentInsulinDose = null;
    Handler TimeHandler;
    Handler timeLive;

    SharedPreferenceMethods sharedPreferenceMethods;
    Long HoursLeft;
    Long MinutesLeft;
    String todayDate;
    Boolean taken=false;
    /**
     * ----------> end <----------
     **/

    ImageView ivTimeLeftCircle;
    TextView tvTodayDate;
    TextView tvTimeLeft;


    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton sendInsulinActionButton, sendBloodSugarActionButton, sendHeartRateActionButton,
            sendMealActionButton, sendMedicationActionButton;

    private OnFragmentInteractionListener mListener;

    public FragmentMain() {
        // Required empty public constructor
    }


    public static FragmentMain newInstance() {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HoursLeft = 0L;
        MinutesLeft = 0L;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        materialDesignFAM = (FloatingActionMenu) getActivity().findViewById(R.id.material_design_android_floating_action_menu);
        sendInsulinActionButton = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.material_design_floating_action_menu_item1);
        sendBloodSugarActionButton = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.material_design_floating_action_menu_item2);
        sendHeartRateActionButton = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.material_design_floating_action_menu_item3);
        sendMealActionButton = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.material_design_floating_action_menu_item4);
        sendMedicationActionButton = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.material_design_floating_action_menu_item5);

        ivTimeLeftCircle = (ImageView) getActivity().findViewById(R.id.ivMainCircle);
        tvTodayDate = (TextView) getActivity().findViewById(R.id.tvTodayDate);
        tvTimeLeft = (TextView) getActivity().findViewById(R.id.tvTimeLeft);

        sendInsulinActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivitySendInsulinDose.class);
                startActivity(intent);
            }
        });
        sendBloodSugarActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivitySendBloodSugar.class);
                startActivity(intent);
            }
        });
        sendHeartRateActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivitySendHeartRate.class);
                startActivity(intent);
            }
        });
        sendMealActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivitySendMeal.class);
                startActivity(intent);
            }
        });
        sendMedicationActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivitySendMedication.class);
                startActivity(intent);
            }
        });
        ivTimeLeftCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivitySendInsulinDose.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        if(alarm!=null)
            alarm.cancelAlarm(getActivity());
    }


    public void syncTimeLeftForNextDose() {

        Log.d("MainFrag","Going to Sync");
        Date todayCurrentDate = new Date();
        dataSource = new DataSource(this.getActivity());
        currentInsulinDose = dataSource.retrieveCurrentInsulinDose(todayCurrentDate);
        if (currentInsulinDose != null) {
            if(!currentInsulinDose.getTaken()) {
                taken = false;
                todayDate = InsulinDose.ConvertDateToString(currentInsulinDose.getDate_time());
                Log.d("DATES", "InsulinDose: " + currentInsulinDose.getDate_time().toString() + "/Current: " + todayCurrentDate.toString());
                tvTodayDate.setText(todayDate);
                Long[] time = InsulinDose.getTimeLeft(currentInsulinDose.getDate_time(), todayCurrentDate);
                HoursLeft = time[0];
                MinutesLeft = time[1];

                tvTimeLeft.setText(HoursLeft + "H" + ":" + MinutesLeft + "M");

            } else {
                taken = true;
                tvTimeLeft.setText("Dose Taken");
            }
        } else {
            tvTimeLeft.setText("No Dose");
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataSource = new DataSource(getActivity());
        restClient = new RestClient(getActivity());
        sharedPreferenceMethods = new SharedPreferenceMethods(getActivity());
        Log.i("MAIN", "onAttach!");
        TaskGetCurrentInsulinDoseAfterItDownloaded t = new TaskGetCurrentInsulinDoseAfterItDownloaded(this.getActivity());
        t.execute();
        TimeHandler = new Handler();
        TimeHandler.post(new UpdateTimeTask());

        timeLive = new Handler();
        timeLive.post(new UpdateTimeLive());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        TimeHandler.removeCallbacks(new UpdateTimeTask());
        timeLive.removeCallbacks(new UpdateTimeLive());
    }

    @Override
    public void onPause() {
        super.onPause();
        TimeHandler.removeCallbacks(new UpdateTimeTask());
        timeLive.removeCallbacks(new UpdateTimeLive());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    public void setUpAlarm( ){
       //run the alarm
        alarm= new DoseAlarmReceiver();
        alarm.setAlarm(getActivity());

    }

    public class TaskGetCurrentInsulinDoseAfterItDownloaded extends AsyncTask<Integer, Void, Void> {
        Activity ThisActivity;
        DataSource dataSource;

        public TaskGetCurrentInsulinDoseAfterItDownloaded(Activity thisActivity) {
            super();
            this.ThisActivity = thisActivity;
            dataSource = new DataSource(thisActivity.getBaseContext());

        }

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Integer... params) {
            Boolean b = true;
            while (b) {
                Log.i("Sync-Task", "I am trying ... ");
                InsulinDose insulinDose = dataSource.retrieveCurrentInsulinDose(new Date());
                if (insulinDose != null) {
                    b = false;

                } else {
                    Thread.currentThread();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void response) {

            syncTimeLeftForNextDose();
            setUpAlarm();

            dataSource = new DataSource(getActivity());
            currentInsulinDose = dataSource.retrieveCurrentInsulinDose(new Date());
            todayDate = InsulinDose.ConvertDateToString(currentInsulinDose.getDate_time());
            Log.d("DATES", "InsulinDose: " + currentInsulinDose.getDate_time().toString() + "/Current: " + new Date().toString());
            tvTodayDate.setText(todayDate);
            Long[] time = InsulinDose.getTimeLeft(currentInsulinDose.getDate_time(), new Date());
            HoursLeft = time[0];
            MinutesLeft = time[1];

            tvTimeLeft.setText(HoursLeft + "H" + ":" + MinutesLeft + "M");

        }
    }

    class UpdateTimeTask implements Runnable {

        @Override
        public void run() {
            syncTimeLeftForNextDose();
            TimeHandler.postDelayed(this,1000*60);
        }
    }

    class UpdateTimeLive implements Runnable {
        boolean b = true;
        @Override
        public void run() {
            tvTodayDate.setText(todayDate);
            if(currentInsulinDose!=null) {
                if (!taken) {
                    if (b) {
                        tvTimeLeft.setText(HoursLeft + "H" + " " + MinutesLeft + "M");
                        b = false;
                    } else {
                        tvTimeLeft.setText(HoursLeft + "H" + ":" + MinutesLeft + "M");
                        b = true;
                    }
                    if (HoursLeft == 0 && MinutesLeft == 0) {
                        ivTimeLeftCircle.setImageResource(R.drawable.circle_main_send);
                    }
                } else {
                    ivTimeLeftCircle.setImageResource(R.drawable.circle_main);
                    tvTimeLeft.setText("Dose Taken");
                }
            } else {
                tvTimeLeft.setText("No Dose");
            }
            Log.i("HEY", "DELAY!");
            timeLive.postDelayed(this,1000);
        }
    }

}
