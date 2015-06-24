package com.blueinklabs.taxassistantpakistan;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class InputDialogFragment extends DialogFragment {
    private Child ch1;
    private View insideFragView;
    int gPosition;
    int cPosition;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private Boolean isPaid = (BuildConfig.FLAVOR == "paidversion");

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle mArgs = getArguments();
        gPosition = mArgs.getInt("gPosition");
        cPosition = mArgs.getInt("cPosition");
        ch1 = ((CompleteWorking) getActivity()).getChildfromPos(gPosition, cPosition);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        insideFragView = inflater.inflate(getLayoutInt(), null);
        ((TextView) insideFragView.findViewById(R.id.headerview)).setText(ch1.getName());
        insideFragView = editLayout();
        builder.setView(insideFragView)
                //.setTitle(ch1.getName())
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        setPositiveStep();
                        //Toast.makeText(getActivity(), Integer.toString(ch1.getChildSize()), Toast.LENGTH_SHORT).show();
                        ((CompleteWorking) getActivity()).getExpAdapter().notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
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
                    case 1:
                        return R.layout.medical_allowance;
                    case 2:
                        return R.layout.other_allowances;
                    case 3:
                        return R.layout.expenditure_reimbursements;
                }
                break;
            case 1:
                switch (cPosition) {
                    case 0:
                        return R.layout.rental_income;
                    case 1:
                        Log.d("error1", Integer.toString(gPosition) + Integer.toString(cPosition));
                        return R.layout.rent_expenses;
                }
                break;
            case 2:
                switch (cPosition) {
                    case 0:
                        return R.layout.capital_gains_securities;
                    case 1:
                        return R.layout.capital_gains_immovable;
                }
                break;
            case 3:
                switch (cPosition) {
                    case 0:
                        return R.layout.dividend;
                    case 1:
                        return R.layout.profit_on_debt;
                    case 2:
                        return R.layout.prize_bonds;
                    case 3:
                        return R.layout.bonus_shares;

                }
                break;
            case 4:
                switch (cPosition) {
                    case 0:
                        return R.layout.zakat;
                    case 1:
                        return R.layout.donations;
                }
                break;
            case 5:
                switch (cPosition) {
                    case 0:
                        return R.layout.new_shares;
                    case 1:
                        return R.layout.pension_funds;
                    case 2:
                        return R.layout.teacher;
                    case 3:
                        return R.layout.senior_citizen;
                }
                break;
            case 6:
                return R.layout.generic_one_layout;
        }
        return 0;
    }

    private View editLayout() {
        List<Spinner> spinList;
        List<EditText> editList;
        List<TextView> textList;
        ArrayAdapter<CharSequence> adapterSpin;
        int[] spinnerID;
        int[] inputID;

        switch (gPosition) {
            case 0:
                switch (cPosition) {
                    case 0:
                        spinList = new ArrayList<Spinner>();
                        editList = new ArrayList<EditText>();
                        adapterSpin = ArrayAdapter.createFromResource(getActivity(),
                                R.array.salary_period, android.R.layout.simple_spinner_item);
                        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerID = new int[]{R.id.dropdown1, R.id.dropdown2, R.id.dropdown3, R.id.dropdown4};
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3, R.id.input4};
                        for (int i = 0; i < spinnerID.length; i++) {
                            spinList.add((Spinner) insideFragView.findViewById(spinnerID[i]));
                            spinList.get(i).setAdapter(adapterSpin);
                            if (ch1.getChildPeriod(i)) {
                                spinList.get(i).setSelection(1);
                            } else {
                                spinList.get(i).setSelection(0);
                            }
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    case 1:
                        spinList = new ArrayList<Spinner>();
                        editList = new ArrayList<EditText>();
                        adapterSpin = ArrayAdapter.createFromResource(getActivity(),
                                R.array.salary_period, android.R.layout.simple_spinner_item);
                        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerID = new int[]{R.id.dropdown1};
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < spinnerID.length; i++) {
                            spinList.add((Spinner) insideFragView.findViewById(spinnerID[i]));
                            spinList.get(i).setAdapter(adapterSpin);
                            if (ch1.getChildPeriod(i)) {
                                spinList.get(i).setSelection(1);
                            } else {
                                spinList.get(i).setSelection(0);
                            }
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    case 2:
                        spinList = new ArrayList<Spinner>();
                        editList = new ArrayList<EditText>();
                        adapterSpin = ArrayAdapter.createFromResource(getActivity(),
                                R.array.salary_period, android.R.layout.simple_spinner_item);
                        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerID = new int[]{R.id.dropdown1, R.id.dropdown2, R.id.dropdown3, R.id.dropdown4, R.id.dropdown5};
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3, R.id.input4, R.id.input5};
                        for (int i = 0; i < spinnerID.length; i++) {
                            spinList.add((Spinner) insideFragView.findViewById(spinnerID[i]));
                            spinList.get(i).setAdapter(adapterSpin);
                            if (ch1.getChildPeriod(i)) {
                                spinList.get(i).setSelection(1);
                            } else {
                                spinList.get(i).setSelection(0);
                            }
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    case 3:
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3, R.id.input4};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                }
                break;
            case 1:
                switch (cPosition) {
                    case 0:
                        Log.d("error1", Integer.toString(gPosition) + Integer.toString(cPosition) + "new");
                        spinList = new ArrayList<Spinner>();
                        editList = new ArrayList<EditText>();
                        adapterSpin = ArrayAdapter.createFromResource(getActivity(),
                                R.array.salary_period, android.R.layout.simple_spinner_item);
                        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerID = new int[]{R.id.dropdown1};
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < spinnerID.length; i++) {
                            spinList.add((Spinner) insideFragView.findViewById(spinnerID[i]));
                            spinList.get(i).setAdapter(adapterSpin);
                            if (ch1.getChildPeriod(i)) {
                                spinList.get(i).setSelection(1);
                            } else {
                                spinList.get(i).setSelection(0);
                            }
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    case 1:
                        spinList = new ArrayList<Spinner>();
                        editList = new ArrayList<EditText>();
                        adapterSpin = ArrayAdapter.createFromResource(getActivity(),
                                R.array.salary_period, android.R.layout.simple_spinner_item);
                        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerID = new int[]{R.id.dropdown1, R.id.dropdown2, R.id.dropdown3, R.id.dropdown4};
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3, R.id.input4};
                        for (int i = 0; i < spinnerID.length; i++) {
                            spinList.add((Spinner) insideFragView.findViewById(spinnerID[i]));
                            spinList.get(i).setAdapter(adapterSpin);
                            if (ch1.getChildPeriod(i)) {
                                spinList.get(i).setSelection(1);
                            } else {
                                spinList.get(i).setSelection(0);
                            }
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                }
                break;
            case 2:
                switch (cPosition) {
                    case 0:
                        Log.d("error1", Integer.toString(gPosition) + Integer.toString(cPosition) + "new2");
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    //return R.layout.capital_gains_securities;
                    case 1:
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    //return R.layout.capital_gains_immovable;
                }
                break;
            case 3:
                switch (cPosition) {
                    case 0:
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1, R.id.input2};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    //return R.layout.dividend;
                    case 1:
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    //return R.layout.profit_on_debt;
                    case 2:
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1, R.id.input2};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    //return R.layout.prize_bonds;
                    case 3:
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    //return R.layout.bonus_shares;

                }
                break;
            case 4:
                switch (cPosition) {
                    case 0:
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    //return R.layout.zakat;
                    case 1:
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    //return R.layout.donations;
                }
                break;
            case 5:
                switch (cPosition) {
                    case 0:
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    //return R.layout.new_shares;
                    case 1:
                        editList = new ArrayList<EditText>();
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < inputID.length; i++) {
                            editList.add((EditText) insideFragView.findViewById(inputID[i]));
                            editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                            editList.get(i).setSelection(editList.get(i).length());
                        }
                        break;
                    //return R.layout.pension_funds;
                    case 2:
                        spinList = new ArrayList<Spinner>();
                        adapterSpin = ArrayAdapter.createFromResource(getActivity(),
                                R.array.teacher_status, android.R.layout.simple_spinner_item);
                        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerID = new int[]{R.id.dropdown1};
                        for (int i = 0; i < spinnerID.length; i++) {
                            spinList.add((Spinner) insideFragView.findViewById(spinnerID[i]));
                            spinList.get(i).setAdapter(adapterSpin);
                            if (ch1.getChildPeriod(i)) {
                                spinList.get(i).setSelection(1);
                            } else {
                                spinList.get(i).setSelection(0);
                            }
                        }
                        break;
                    //return R.layout.teacher;
                    case 3:
                        spinList = new ArrayList<Spinner>();
                        adapterSpin = ArrayAdapter.createFromResource(getActivity(),
                                R.array.teacher_status, android.R.layout.simple_spinner_item);
                        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerID = new int[]{R.id.dropdown1};
                        for (int i = 0; i < spinnerID.length; i++) {
                            spinList.add((Spinner) insideFragView.findViewById(spinnerID[i]));
                            spinList.get(i).setAdapter(adapterSpin);
                            if (ch1.getChildPeriod(i)) {
                                spinList.get(i).setSelection(1);
                            } else {
                                spinList.get(i).setSelection(0);
                            }
                        }
                        break;
                }
                break;
            case 6:
                ((TextView) insideFragView.findViewById(R.id.label1)).setText(ch1.getName().substring(0, 1).toUpperCase() + ch1.getName().substring(1).toLowerCase());
                editList = new ArrayList<EditText>();
                inputID = new int[]{R.id.input1};
                for (int i = 0; i < inputID.length; i++) {
                    editList.add((EditText) insideFragView.findViewById(inputID[i]));
                    editList.get(i).setText(convertToStr(ch1.getChildComponent(i), ch1.getChildPeriod(i)));
                    editList.get(i).setSelection(editList.get(i).length());
                }
                break;
            //return R.layout.generic_one_layout;
        }
        return insideFragView;
    }

    private String convertToStr(Double inpDoub, Boolean inpBool) {
        DecimalFormat formatter = new DecimalFormat("#");
        if (inpDoub.equals(Double.valueOf(0))) {
            return "";
        } else {
            if (inpBool) {
                inpDoub = inpDoub / 12;

                return formatter.format(inpDoub);
            }
            return formatter.format(inpDoub);
        }
    }

    private void setPositiveStep() {
        Double finaldoublesum = Double.valueOf(0);
        List<EditText> inputTextNum = new ArrayList<EditText>();
        List<Spinner> periodSelect = new ArrayList<Spinner>();
        int[] inputID;
        int[] spinnerID;

        switch (gPosition) {
            case 0:
                switch (cPosition) {
                    case 0:
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3, R.id.input4};
                        spinnerID = new int[]{R.id.dropdown1, R.id.dropdown2, R.id.dropdown3, R.id.dropdown4};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                            Spinner spinTemp = (Spinner) insideFragView.findViewById(spinnerID[i]);
                            periodSelect.add(spinTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            Boolean tempBool;
                            if (periodSelect.get(i).getSelectedItem().toString().equals("Monthly")) {
                                ch1.addChildPeriod(i, Boolean.TRUE);
                                tempBool = Boolean.TRUE;
                            } else {
                                ch1.addChildPeriod(i, Boolean.FALSE);
                                tempBool = Boolean.FALSE;
                            }
                            putPreferenceBoolean(gPosition, cPosition, i, tempBool);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                if (tempBool) {
                                    tempDoub = tempDoub * 12;
                                }
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    case 1:
                        inputID = new int[]{R.id.input1};
                        spinnerID = new int[]{R.id.dropdown1};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                            Spinner spinTemp = (Spinner) insideFragView.findViewById(spinnerID[i]);
                            periodSelect.add(spinTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            Boolean tempBool;
                            if (periodSelect.get(i).getSelectedItem().toString().equals("Monthly")) {
                                ch1.addChildPeriod(i, Boolean.TRUE);
                                tempBool = Boolean.TRUE;
                            } else {
                                ch1.addChildPeriod(i, Boolean.FALSE);
                                tempBool = Boolean.FALSE;
                            }
                            putPreferenceBoolean(gPosition, cPosition, i, tempBool);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                if (tempBool) {
                                    tempDoub = tempDoub * 12;
                                }
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    case 2:
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3, R.id.input4, R.id.input5};
                        spinnerID = new int[]{R.id.dropdown1, R.id.dropdown2, R.id.dropdown3, R.id.dropdown4, R.id.dropdown5};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                            Spinner spinTemp = (Spinner) insideFragView.findViewById(spinnerID[i]);
                            periodSelect.add(spinTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            Boolean tempBool;
                            if (periodSelect.get(i).getSelectedItem().toString().equals("Monthly")) {
                                ch1.addChildPeriod(i, Boolean.TRUE);
                                tempBool = Boolean.TRUE;
                            } else {
                                ch1.addChildPeriod(i, Boolean.FALSE);
                                tempBool = Boolean.FALSE;
                            }
                            putPreferenceBoolean(gPosition, cPosition, i, tempBool);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                if (tempBool) {
                                    tempDoub = tempDoub * 12;
                                }
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    case 3:
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3, R.id.input4};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                }
                break;
            case 1:
                switch (cPosition) {
                    case 0:
                        inputID = new int[]{R.id.input1};
                        spinnerID = new int[]{R.id.dropdown1};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                            Spinner spinTemp = (Spinner) insideFragView.findViewById(spinnerID[i]);
                            periodSelect.add(spinTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            Boolean tempBool;
                            if (periodSelect.get(i).getSelectedItem().toString().equals("Monthly")) {
                                ch1.addChildPeriod(i, Boolean.TRUE);
                                tempBool = Boolean.TRUE;
                            } else {
                                ch1.addChildPeriod(i, Boolean.FALSE);
                                tempBool = Boolean.FALSE;
                            }
                            putPreferenceBoolean(gPosition, cPosition, i, tempBool);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                if (tempBool) {
                                    tempDoub = tempDoub * 12;
                                }
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    case 1:
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3, R.id.input4};
                        spinnerID = new int[]{R.id.dropdown1, R.id.dropdown2, R.id.dropdown3, R.id.dropdown4};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                            Spinner spinTemp = (Spinner) insideFragView.findViewById(spinnerID[i]);
                            periodSelect.add(spinTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            Boolean tempBool;
                            if (periodSelect.get(i).getSelectedItem().toString().equals("Monthly")) {
                                ch1.addChildPeriod(i, Boolean.TRUE);
                                tempBool = Boolean.TRUE;
                            } else {
                                ch1.addChildPeriod(i, Boolean.FALSE);
                                tempBool = Boolean.FALSE;
                            }
                            putPreferenceBoolean(gPosition, cPosition, i, tempBool);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                if (tempBool) {
                                    tempDoub = tempDoub * 12;
                                }
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                }
                break;
            case 2:
                switch (cPosition) {
                    case 0:
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    //return R.layout.capital_gains_securities;
                    case 1:
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    //return R.layout.capital_gains_immovable;
                }
                break;
            case 3:
                switch (cPosition) {
                    case 0:
                        inputID = new int[]{R.id.input1, R.id.input2};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    //return R.layout.dividend;
                    case 1:
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    //return R.layout.profit_on_debt;
                    case 2:
                        inputID = new int[]{R.id.input1, R.id.input2};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    //return R.layout.prize_bonds;
                    case 3:
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    //return R.layout.bonus_shares;

                }
                break;
            case 4:
                switch (cPosition) {
                    case 0:
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    //return R.layout.zakat;
                    case 1:
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    //return R.layout.donations;
                }
                break;
            case 5:
                switch (cPosition) {
                    case 0:
                        inputID = new int[]{R.id.input1, R.id.input2, R.id.input3};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    //return R.layout.new_shares;
                    case 1:
                        inputID = new int[]{R.id.input1};
                        for (int i = 0; i < inputID.length; i++) {
                            EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                            inputTextNum.add(inputTemp);
                        }
                        for (int i = 0; i < inputID.length; i++) {
                            Double tempDouble;
                            ch1.addChildPeriod(i, Boolean.FALSE);
                            putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            if (inputTextNum.get(i).getText().toString().equals("")) {
                                tempDouble = Double.valueOf(0);
                            } else {
                                Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                                tempDouble = tempDoub;
                            }
                            ch1.addChildComponent(i, tempDouble);
                            putPreferenceValues(gPosition, cPosition, i, tempDouble);
                        }
                        break;
                    //return R.layout.pension_funds;
                    case 2:
                        spinnerID = new int[]{R.id.dropdown1};
                        for (int i = 0; i < spinnerID.length; i++) {
                            Spinner spinTemp = (Spinner) insideFragView.findViewById(spinnerID[i]);
                            periodSelect.add(spinTemp);
                        }
                        for (int i = 0; i < spinnerID.length; i++) {
                            if (periodSelect.get(i).getSelectedItem().toString().equals("Yes")) {
                                ch1.addChildPeriod(i, Boolean.TRUE);
                                putPreferenceBoolean(gPosition, cPosition, i, Boolean.TRUE);
                            } else {
                                ch1.addChildPeriod(i, Boolean.FALSE);
                                putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            }
                        }
                        break;
                    //return R.layout.teacher;
                    case 3:
                        spinnerID = new int[]{R.id.dropdown1};
                        for (int i = 0; i < spinnerID.length; i++) {
                            Spinner spinTemp = (Spinner) insideFragView.findViewById(spinnerID[i]);
                            periodSelect.add(spinTemp);
                        }
                        for (int i = 0; i < spinnerID.length; i++) {
                            if (periodSelect.get(i).getSelectedItem().toString().equals("Yes")) {
                                ch1.addChildPeriod(i, Boolean.TRUE);
                                putPreferenceBoolean(gPosition, cPosition, i, Boolean.TRUE);
                            } else {
                                ch1.addChildPeriod(i, Boolean.FALSE);
                                putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                            }
                        }
                        break;
                }
                break;
            case 6:
                inputID = new int[]{R.id.input1};
                for (int i = 0; i < inputID.length; i++) {
                    EditText inputTemp = (EditText) insideFragView.findViewById(inputID[i]);
                    inputTextNum.add(inputTemp);
                }
                for (int i = 0; i < inputID.length; i++) {
                    Double tempDouble;
                    ch1.addChildPeriod(i, Boolean.FALSE);
                    putPreferenceBoolean(gPosition, cPosition, i, Boolean.FALSE);
                    if (inputTextNum.get(i).getText().toString().equals("")) {
                        tempDouble = Double.valueOf(0);
                    } else {
                        Double tempDoub = Double.parseDouble(inputTextNum.get(i).getText().toString());
                        tempDouble = tempDoub;
                    }
                    ch1.addChildComponent(i, tempDouble);
                    putPreferenceValues(gPosition, cPosition, i, tempDouble);
                }
                break;
            //return R.layout.generic_one_layout;
        }
        ch1.setDisplayAmount();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
    }

    public void putPreferenceValues(int i, int j, int k, Double value) {
        if (isPaid) {
            String tempString = "values" + i + j + k;
            editor.putLong(tempString, Double.doubleToRawLongBits(value));
            editor.commit();
        }
    }

    public void putPreferenceBoolean(int i, int j, int k, Boolean bool) {
        if (isPaid) {
            String tempString = "boolean" + i + j + k;
            editor.putBoolean(tempString, bool);
            editor.commit();
        }
    }
}
