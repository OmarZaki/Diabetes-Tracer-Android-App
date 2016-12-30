package com.example.omar.diabetestracerapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.omar.diabetestracerapp.auxiliary.CustomScheduleAdapter;
import com.example.omar.diabetestracerapp.auxiliary.SyncIndicators;
import com.example.omar.diabetestracerapp.auxiliary.TypeEvent;
import com.example.omar.diabetestracerapp.data_model.Schedule;
import com.example.omar.diabetestracerapp.database.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  The Fragment that should hold
 */
public class FragmentSchedule extends Fragment {
    /** ------------> Back-end Code <------- */
    ArrayList<Schedule> scheduleArrayList;
    ArrayList<Schedule> allEvents;
    DataSource dataSource ;
    Handler eventHandler;
    Boolean downloaded = false;

    Spinner spinnerYears;
    Spinner spinnerMonths;
    Spinner spinnerType;
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
        spinnerYears = (Spinner) getActivity().findViewById(R.id.spinnerYear);
        spinnerMonths = (Spinner) getActivity().findViewById(R.id.spinnerMonth);
        spinnerType= (Spinner) getActivity().findViewById(R.id.spinnerType);
        dataSource=new DataSource( getActivity());

        setupListAdapters();

        final int month = spinnerMonths.getSelectedItemPosition();
        final int year = Integer.valueOf(spinnerYears.getSelectedItem().toString());
        final TypeEvent type =TypeEvent.valueOf(spinnerType.getSelectedItem().toString());

        spinnerMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                filterSchedule();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                filterSchedule();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterSchedule();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // download the list.
        /**
         * The background task is redundant.
         * I passed the check on whether database was synced to the filterSchedule function.
         * Check filterSchedule() for more details.
         * Filtering won't raise an exception anymore.
         */
        /*eventHandler = new Handler();
        eventHandler.post( new GetAllEventsTask(month,year,type) );*/
    }

    private void setupListAdapters() {
        // month spinner adapter
        List<String> monthList =   Arrays.asList(getActivity().getResources().getStringArray(R.array.months_array));
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, monthList);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(monthAdapter);
        spinnerMonths.setSelection(0);
        // years spinner adapter
        List <String> yearList = Arrays.asList(getActivity().getResources().getStringArray(R.array.years_array));
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYears.setAdapter(yearAdapter);
        spinnerYears.setSelection(0);
        // types spinner adapter.
        List <String> typeList = new ArrayList<String>();
        typeList.add(String.valueOf(TypeEvent.BLOODSUGAR));
        typeList.add(String.valueOf(TypeEvent.DOSE));
        typeList.add(String.valueOf(TypeEvent.HEARTRATE));
        typeList.add(String.valueOf(TypeEvent.MEAL));
        typeList.add(String.valueOf(TypeEvent.MEDICATION));
        typeList.add(String.valueOf(TypeEvent.MESSAGE));
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);
        spinnerType.setSelection(0);
    }

    private void filterSchedule() {
        int month = spinnerMonths.getSelectedItemPosition();
        int year = Integer.valueOf(spinnerYears.getSelectedItem().toString());
        final TypeEvent type = TypeEvent.valueOf(spinnerType.getSelectedItem().toString());
        if(SyncIndicators.SyncCategories  && SyncIndicators.SyncMessages && SyncIndicators.SyncMeal && SyncIndicators.SyncInsulin) {
            allEvents = new ArrayList<>();
            allEvents = dataSource.retrieveListEvents(month, year, type);
            CustomScheduleAdapter customScheduleAdapter = new CustomScheduleAdapter(getActivity(), allEvents);
            lvEvents.setAdapter(customScheduleAdapter);
        }
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

    /**
     * The background task is redundant.
     * I passed the check on whether database was synced to the filterSchedule function.
     * Check filterSchedule() for more details.
     * Filtering won't raise an exception anymore.
     */
    /*
    class GetAllEventsTask implements Runnable {
     int month = 0 ;
    int year = 0 ;
    TypeEvent type=TypeEvent.DOSE;
    public GetAllEventsTask( int month, int year,TypeEvent type) {
        this.month = month;
        this.year = year;
        this.type= type;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        SyncIndicators.SyncSchedule = true;
    }

    @Override
        public void run() {
            eventHandler.postDelayed(this,1000*2);
            Log.i("TRYING-SCHEDULE","trying to download");
            if(SyncIndicators.SyncCategories  && SyncIndicators.SyncMessages && SyncIndicators.SyncMeal && SyncIndicators.SyncInsulin) {
                allEvents = new ArrayList<>();
                allEvents = dataSource.retrieveListEvents(month,year,type);
                CustomScheduleAdapter customScheduleAdapter = new CustomScheduleAdapter(getActivity(), allEvents);
                lvEvents.setAdapter(customScheduleAdapter);
                eventHandler.removeCallbacks(this);
            }
        }
    }
*/
}
