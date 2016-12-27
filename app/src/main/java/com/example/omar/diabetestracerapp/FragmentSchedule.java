package com.example.omar.diabetestracerapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.omar.diabetestracerapp.auxiliary.CustomScheduleAdapter;
import com.example.omar.diabetestracerapp.data_model.InsulinDose;
import com.example.omar.diabetestracerapp.data_model.Schedule;
import com.example.omar.diabetestracerapp.database.DataSource;

import java.util.ArrayList;
import java.util.Date;

/**
 *  The Fragment that should hold
 */
public class FragmentSchedule extends Fragment {
    /** ------------> Back-end Code <------- */
    ArrayList<Schedule> scheduleArrayList;
    CustomScheduleAdapter customScheduleAdapter;
    ArrayList<Schedule> allEvents;
    /** ------> List-View Elemtns <------ **/
    ListView lvEvents;



    private OnFragmentInteractionListener mListener;

    public FragmentSchedule() {
        // Required empty public constructor
    }


    public static FragmentSchedule newInstance() {
        FragmentSchedule fragment = new FragmentSchedule();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvEvents = (ListView)getActivity().findViewById(R.id.lvEvents);
        //allevents = getAllEventFromDataBase();
        // TODO 1. get the events schedule from database;

        // TODO 2. set up it with data adapter.

        customScheduleAdapter = new CustomScheduleAdapter(getActivity(),allEvents);
        lvEvents.setAdapter(customScheduleAdapter);



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public class GetAllEventsTask extends AsyncTask<Integer, Void, Void> {

        DataSource dataSource;

        public GetAllEventsTask(Activity thisActivity) {
            super();

            dataSource = new DataSource(thisActivity.getBaseContext());
        }

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Integer... params) {
//            Boolean b = true;
//            while (b) {
//                Log.i("Sync-Task", "I am trying ... ");
//                InsulinDose insulinDose = dataSource.retrieveCurrentInsulinDose(new Date());
//                if (insulinDose != null) {
//                    b = false;
//                } else {
//                    Thread.currentThread();
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void response) {

//        /** ----- > Back-end source code <----- */

        }
    }
}
