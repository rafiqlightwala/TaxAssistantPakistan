package com.blueinklabs.taxassistantpakistan;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class InputDialogFragment extends DialogFragment {
    private Child ch1;
    private View insideFragView;
    int gPosition;
    int cPosition;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle mArgs = getArguments();
        gPosition = mArgs.getInt("gPosition");
        cPosition = mArgs.getInt("cPosition");
        ch1 = ((SalaryIncome) getActivity()).getChildfromPos(gPosition, cPosition);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        insideFragView = inflater.inflate(getLayoutInt(), null);
        ((TextView) insideFragView.findViewById(R.id.headerview)).setText(ch1.getName());
        insideFragView = editLayout(insideFragView);
        builder.setView(insideFragView)
                //.setTitle(ch1.getName())
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TextView doubleone = (TextView) insideFragView.findViewById(R.id.input1);
                        Double passdouble = Double.parseDouble(doubleone.getText().toString());
                        ch1.setDisplayAmount(passdouble);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    private int getLayoutInt() {
        switch (gPosition) {
            case 0:
                switch (cPosition) {
                    case 0:
                        return R.layout.salary_pay_wages;
                }
                break;
        }
        return 0;
    }

    private View editLayout(View inputView) {
        Spinner spinner1 = (Spinner) inputView.findViewById(R.id.dropdown1);
        Spinner spinner2 = (Spinner) inputView.findViewById(R.id.dropdown2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.salary_period, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        return inputView;
    }
}
