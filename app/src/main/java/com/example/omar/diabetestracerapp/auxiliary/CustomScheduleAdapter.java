package com.example.omar.diabetestracerapp.auxiliary;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.omar.diabetestracerapp.R;
import com.example.omar.diabetestracerapp.data_model.Categories;
import com.example.omar.diabetestracerapp.data_model.InsulinDose;
import com.example.omar.diabetestracerapp.data_model.Meal;
import com.example.omar.diabetestracerapp.data_model.Messages;
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
        ivIconEvent.setImageResource(getObjectTypeIcon(events.get(position).getObject()));
        tvItemTitle.setText(events.get(position).getTitle());
        tvItemDate.setText(events.get(position).getDate().toString());
        return viewRow;


    }

    private int getObjectTypeIcon(Object object) {
        int type = R.drawable.ic_item_blood_sugar;
        if(object instanceof InsulinDose){
            type=R.drawable.ic_item_dose;
        }
        if(object instanceof Meal){
            type= R.drawable.ic_item_meal;
        }
        if(object instanceof Messages){
            type = R.drawable.ic_item_message;
        }
        if(object instanceof Categories){
            Categories categories = (Categories)object;
            // this for heart rate;
            if(categories.getCategory_name_id()==1){
                type = R.drawable.ic_item_heart_rate;
            }
            // for the blood sugar
            if(categories.getCategory_name_id()==2){
                    type= R.drawable.ic_item_blood_sugar;
            }
        }
        return type ;

    }
}
