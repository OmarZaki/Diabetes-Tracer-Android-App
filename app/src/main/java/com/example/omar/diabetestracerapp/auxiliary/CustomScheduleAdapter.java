package com.example.omar.diabetestracerapp.auxiliary;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.omar.diabetestracerapp.R;
import com.example.omar.diabetestracerapp.data_model.Schedule;

import java.util.ArrayList;

/**
 * Created by OMAR on 12/26/2016.
 */

public class CustomScheduleAdapter extends ArrayAdapter {
    /**
     * Parameters of the adapter.
     *
     */
    private Activity activityContext;
    private ArrayList<Schedule> events;
    private static LayoutInflater inflater = null;


    /**
     * Constructor of the Adapter.
     * @param activityContext
     * @param events
     */
    public CustomScheduleAdapter(Activity activityContext, ArrayList<Schedule> events) {
        super(activityContext, R.layout.list_view_schedule, events);
        // Passed values.
        this.activityContext = activityContext;
        this.events = events;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater layoutInflater = (LayoutInflater) activityContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.list_view_schedule,null, true);
        // List User Interface elements
        ImageView ivIconEvent = (ImageView)viewRow.findViewById(R.id.ivIconScheduleItem);
        TextView tvItemTitle = (TextView) viewRow.findViewById(R.id.tvTitleScheduleItem);
        TextView tvItemDate = (TextView) viewRow.findViewById(R.id.tvDateScheduleItem);
       // Button tbnDetails = (Button)viewRow.findViewById(R.id.btnDetailsScheduleItem);

        // setup the values.
        // set the icon based on the Type of the icon.
        ivIconEvent.setImageResource(getObjectTypeIcon(events.get(position).getType()));
        tvItemTitle.setText(events.get(position).getTitle());
        tvItemDate.setText(events.get(position).getDate().toString());
        return viewRow;


    }

    private int getObjectTypeIcon(TypeEvent object) {
        int type = R.drawable.ic_item_blood_sugar;
        if(object == TypeEvent.DOSE){
            type=R.drawable.ic_item_dose;
        }
        if(object == TypeEvent.MEAL){
            type= R.drawable.ic_item_meal;
        }
        if(object == TypeEvent.MESSAGE){
            type = R.drawable.ic_item_message;
        }
        if(object == TypeEvent.BLOODSUGAR) {
            type = R.drawable.ic_item_blood_sugar;
        }
            // this for heart rate;
        if(object == TypeEvent.HEARTRATE){
            type = R.drawable.ic_item_heart_rate;
        }
            // for the blood sugar
        if(object == TypeEvent.MEDICATION){
            type = R.drawable.ic_item_medication;
        }

        return type ;

    }
}
