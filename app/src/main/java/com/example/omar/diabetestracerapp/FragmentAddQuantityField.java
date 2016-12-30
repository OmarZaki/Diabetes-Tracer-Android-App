package com.example.omar.diabetestracerapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by OMAR on 12/18/2016.
 */

public class FragmentAddQuantityField extends DialogFragment{
    NumberPicker npQuantity;
    TextView tvActivityQuantity;

    private int textView;
    private int message;
    private int title;
    private int max=1000;
    private int value = -1;
    private int min=0;
    private int unit=-1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_quantity,null);
        tvActivityQuantity = (TextView) getActivity().findViewById(textView);

        // inflate and set the layout for the dialog
        // Pass null as the parent view because its going in dialog layout.
        builder.setView(view);
        npQuantity= (NumberPicker) view.findViewById(R.id.npQuantity);
        npQuantity.setMinValue(min);
        npQuantity.setMaxValue(max);
        if(value>=0){
            npQuantity.setValue(value);
        }
        builder.setMessage(message)
                .setTitle(title);


        builder.setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long q = npQuantity.getValue();
                String res = String.valueOf(q);
                if(unit>=0){
                    res=res+getString(unit);
                }
                tvActivityQuantity.setText(res);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        return builder.create();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public void setTextView(int textView) {
        this.textView = textView;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
