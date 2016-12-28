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
import java.util.List;

/**
 *  The Fragment that should hold
 */
public class FragmentSchedule extends Fragment {
    /** ------------> Back-end Code <------- */
    ArrayList<Schedule> scheduleArrayList;
    ArrayList<Schedule> allEvents;
    DataSource dataSource ;

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
        allEvents= new ArrayList<Schedule>();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }


    @Override
        public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResume() {
        super.onResume();
        lvEvents = (ListView)getActivity().findViewById(R.id.lvEvents);
        dataSource = new DataSource(getActivity());
        GetAllEventsTask task = new GetAllEventsTask(getActivity());
        task.execute(1);
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

        }

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Integer... params) {
            Boolean lock = true;
            while(lock){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("TRYING-SCHEDULE","trying to download");
                dataSource=new DataSource(getActivity());
                allEvents= new ArrayList<>();
                allEvents = dataSource.retrieveListEvents(12);
                lock = (allEvents==null)? true: false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void response) {
            CustomScheduleAdapter customScheduleAdapter = new CustomScheduleAdapter(getActivity(), allEvents);
            lvEvents.setAdapter(customScheduleAdapter);
        }
    }
}
