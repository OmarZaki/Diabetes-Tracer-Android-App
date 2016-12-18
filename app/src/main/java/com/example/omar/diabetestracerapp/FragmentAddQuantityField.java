package com.example.omar.diabetestracerapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by OMAR on 12/18/2016.
 */

public class FragmentAddQuantityField extends DialogFragment{
    NumberPicker npQuantity;
    TextView tvActivityQuantity;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_quantity,null);
        tvActivityQuantity = (TextView) getActivity().findViewById(R.id.tvDoseQuantitySendInsulinDose);

        // inflate and set the layout for the dialog
        // Pass null as the parent view because its going in dialog layout.
        builder.setView(view);
        npQuantity= (NumberPicker) view.findViewById(R.id.npQuantity);
        npQuantity.setMinValue(0);
        npQuantity.setMaxValue(1000);
        builder.setMessage(R.string.insulin_dose_quantity_dialog)
                .setTitle(R.string.insulin_dose_quantity_dialog_title);

        builder.setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long q = npQuantity.getValue();
                tvActivityQuantity.setText(String.valueOf(q) +getString( R.string.insulin_dose_unit));
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        return builder.create();
    }
}
