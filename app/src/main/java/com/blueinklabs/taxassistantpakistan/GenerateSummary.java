package com.blueinklabs.taxassistantpakistan;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Dell on 6/18/2015.
 */
public class GenerateSummary extends AppCompatActivity {
    private Toolbar toolbar;
    private ArrayList<Group> eGroup;
    private ArrayList<Double> groupSumTaxable, groupSumExempt;
    private Double taxableSalary;
    private Double grossTaxonSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_summary);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        //Analytics
        //AnalyticsSampleApp asp = new AnalyticsSampleApp();
        // Get tracker.
        Tracker t = ((AnalyticsSampleApp) getApplication()).getTracker(
                AnalyticsSampleApp.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName("GenerateScreen");
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
        // This event will also be sent with the most recently set screen name.
        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder()
                .setCategory("GenerateCategory")
                .setAction("GenerateAction")
                .setLabel("GenerateLabel")
                .build());
        // Clear the screen name field when we're done.
        t.setScreenName(null);

        /*
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);
        tracker = analytics.newTracker("UA-63236610-2"); // Replace with actual tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
            // Enable Advertising Features.
        tracker.enableAdvertisingIdCollection(true);
            //Send Tracker
        tracker.setScreenName("main screen");
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("submit")
                .build());
        */

        //Ads
        AdView mAdView = (AdView) findViewById(R.id.adView_mainpage);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(MainActivity.myDeviceID)
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        eGroup = (ArrayList<Group>) getIntent().getSerializableExtra("expAdapts");
        runSummaryRoutine();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void runSummaryRoutine() {

        if (eGroup.get(0).isGroupEmpty() && eGroup.get(1).isGroupEmpty() && eGroup.get(2).isGroupEmpty() && eGroup.get(3).isGroupEmpty()) {
            TableLayout tempTable = (TableLayout) findViewById(R.id.table);
            tempTable.setVisibility(View.GONE);
            tempTable = (TableLayout) findViewById(R.id.table1);
            tempTable.setVisibility(View.GONE);
            TextView tempText = (TextView) findViewById(R.id.main_page_text1);
            tempText.setText("Please fill any income source on the previous screen to show tax summary (press back).");
            return;
        }

        TableRow tempRow;
        TextView tempTextView;
        TextView tempTextView1;
        groupSumTaxable = new ArrayList<Double>();
        groupSumExempt = new ArrayList<Double>();
        Double tempDouble = Double.valueOf(0);
        Double tempDouble1 = Double.valueOf(0);
        DecimalFormat formatter = new DecimalFormat("#,###");


        for (int i = 0; i < 4; i++) {
            groupSumTaxable.add(Double.valueOf(0));
            groupSumExempt.add(Double.valueOf(0));
        }
        Resources res = getResources();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < eGroup.get(i).getChildCount(); j++) {
                if (getChildfromPos(i, j).getComponentsSum().intValue() == 0) {
                    int id = res.getIdentifier("tableRow" + i + j, "id", getPackageName());
                    tempRow = (TableRow) findViewById(id);
                    tempRow.setVisibility(View.GONE);
                } else {
                    int id1 = res.getIdentifier("textView" + i + j + "01", "id", getPackageName());
                    int id2 = res.getIdentifier("textView" + i + j + "02", "id", getPackageName());
                    tempTextView = (TextView) findViewById(id1);
                    tempTextView1 = (TextView) findViewById(id2);
                    switch (i) {
                        case 0:
                            switch (j) {
                                case 0:
                                    tempTextView.setText("-");
                                    tempTextView1.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                    tempDouble = groupSumTaxable.get(i) + getChildfromPos(i, j).getComponentsSum();
                                    groupSumTaxable.set(i, tempDouble);
                                    break;
                                case 1:
                                    if (getChildfromPos(i, j).getComponentsSum() <= (getChildfromPos(0, 0).getChildComponent(0) * 0.1)) {
                                        tempTextView.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                        tempDouble = groupSumExempt.get(i) + getChildfromPos(i, j).getComponentsSum();
                                        groupSumExempt.set(i, tempDouble);
                                        tempTextView1.setText("-");
                                    } else {
                                        tempTextView.setText(formatter.format(getChildfromPos(0, 0).getChildComponent(0) * 0.1));
                                        tempDouble = groupSumExempt.get(i) + (getChildfromPos(0, 0).getChildComponent(0) * 0.1);
                                        groupSumExempt.set(i, tempDouble);
                                        tempTextView1.setText(formatter.format(getChildfromPos(i, j).getComponentsSum() - (getChildfromPos(0, 0).getChildComponent(0) * 0.1)));
                                        tempDouble1 = groupSumTaxable.get(i) + (getChildfromPos(i, j).getComponentsSum() - (getChildfromPos(0, 0).getChildComponent(0) * 0.1));
                                        groupSumTaxable.set(i, tempDouble1);
                                    }
                                    break;
                                case 2:
                                    tempTextView.setText("-");
                                    tempTextView1.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                    tempDouble = groupSumTaxable.get(i) + getChildfromPos(i, j).getComponentsSum();
                                    groupSumTaxable.set(i, tempDouble);
                                    break;
                                case 3:
                                    tempTextView.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                    tempTextView1.setText("-");
                                    tempDouble = groupSumExempt.get(i) + getChildfromPos(i, j).getComponentsSum();
                                    groupSumExempt.set(i, tempDouble);
                                    break;
                            }
                            break;
                        case 1:
                            switch (j) {
                                case 0:
                                    tempTextView.setText("-");
                                    tempTextView1.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                    tempDouble = groupSumTaxable.get(i) + getChildfromPos(i, j).getComponentsSum();
                                    groupSumTaxable.set(i, tempDouble);
                                    break;
                                case 1:
                                    tempTextView.setText("-");
                                    tempTextView1.setText("(" + getChildfromPos(i, j).getDisplayAmountOnly() + ")");
                                    tempDouble = groupSumTaxable.get(i) - getChildfromPos(i, j).getComponentsSum();
                                    if (tempDouble < 0) {
                                        tempDouble = Double.valueOf(0);
                                    }
                                    groupSumTaxable.set(i, tempDouble);
                                    break;
                            }
                            break;
                        case 2:
                            switch (j) {
                                case 0:
                                    tempTextView.setText("-");
                                    tempTextView1.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                    tempDouble = groupSumTaxable.get(i) + getChildfromPos(i, j).getComponentsSum();
                                    groupSumTaxable.set(i, tempDouble);
                                    break;
                                case 1:
                                    tempTextView.setText("-");
                                    tempTextView1.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                    tempDouble = groupSumTaxable.get(i) + getChildfromPos(i, j).getComponentsSum();
                                    groupSumTaxable.set(i, tempDouble);
                                    break;
                            }
                            break;
                        case 3:
                            switch (j) {
                                case 0:
                                    tempTextView.setText("-");
                                    tempTextView1.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                    tempDouble = groupSumTaxable.get(i) + getChildfromPos(i, j).getComponentsSum();
                                    groupSumTaxable.set(i, tempDouble);
                                    break;
                                case 1:
                                    tempTextView.setText("-");
                                    tempTextView1.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                    tempDouble = groupSumTaxable.get(i) + getChildfromPos(i, j).getComponentsSum();
                                    groupSumTaxable.set(i, tempDouble);
                                    break;
                                case 2:
                                    tempTextView.setText("-");
                                    tempTextView1.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                    tempDouble = groupSumTaxable.get(i) + getChildfromPos(i, j).getComponentsSum();
                                    groupSumTaxable.set(i, tempDouble);
                                    break;
                                case 3:
                                    tempTextView.setText("-");
                                    tempTextView1.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                                    tempDouble = groupSumTaxable.get(i) + getChildfromPos(i, j).getComponentsSum();
                                    groupSumTaxable.set(i, tempDouble);
                                    break;
                            }
                            break;
                        case 4:
                            switch (j) {
                                case 0:

                                    break;
                                case 1:

                                    break;
                            }
                            break;
                        case 5:
                            switch (j) {
                                case 0:

                                    break;
                                case 1:

                                    break;
                                case 2:

                                    break;
                            }
                            break;
                        case 6:
                            switch (j) {
                                case 0:

                                    break;
                                case 1:

                                    break;
                                case 2:

                                    break;
                                case 3:

                                    break;
                            }
                            break;
                    }

                }
            }
            if (eGroup.get(i).isGroupEmpty()) {
                int id = res.getIdentifier("tableRowGroup" + i, "id", getPackageName());
                tempRow = (TableRow) findViewById(id);
                tempRow.setVisibility(View.GONE);
            } else {
                int id1 = res.getIdentifier("textViewGroup" + i + "1", "id", getPackageName());
                int id2 = res.getIdentifier("textViewGroup" + i + "2", "id", getPackageName());
                if (groupSumExempt.get(i).intValue() == 0) {
                    tempTextView = (TextView) findViewById(id1);
                    tempTextView.setText("-");
                } else {
                    tempTextView = (TextView) findViewById(id1);
                    tempTextView.setText(formatter.format(groupSumExempt.get(i)));
                }
                if (groupSumTaxable.get(i).intValue() == 0) {
                    tempTextView = (TextView) findViewById(id2);
                    tempTextView.setText("-");
                } else {
                    tempTextView = (TextView) findViewById(id2);
                    tempTextView.setText(formatter.format(groupSumTaxable.get(i)));
                }
            }
        }
        if (groupSumTaxable.get(0).intValue() == 0 || getChildfromPos(4, 0).getComponentsSum().intValue() == 0) {
            int id = res.getIdentifier("taxCalcTotalSalary", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
            id = res.getIdentifier("taxCalcZakat", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
        } else {
            int id1 = res.getIdentifier("taxCalcTotalSalaryPKR", "id", getPackageName());
            tempTextView = (TextView) findViewById(id1);
            tempTextView.setText(formatter.format(groupSumTaxable.get(0)));
            int id2 = res.getIdentifier("taxCalcZakatPKR", "id", getPackageName());
            tempTextView = (TextView) findViewById(id2);
            tempTextView.setText("(" + formatter.format(getChildfromPos(4, 0).getComponentsSum()) + ")");
        }
        if (groupSumTaxable.get(0).intValue() == 0) {
            int id = res.getIdentifier("taxCalcTaxableSalary", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
            taxableSalary = Double.valueOf(0);
            id = res.getIdentifier("taxCalcGrossTax", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
            id = res.getIdentifier("taxCalcSeniorCitizen", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
            id = res.getIdentifier("taxCalcTeacher", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
            id = res.getIdentifier("taxCalcDonation", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
            id = res.getIdentifier("taxCalcInvestment", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
            id = res.getIdentifier("taxCalcPension", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
            id = res.getIdentifier("taxCalcNetTax", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
        } else {
            int id1 = res.getIdentifier("taxCalcTaxableSalaryPKR", "id", getPackageName());
            tempTextView = (TextView) findViewById(id1);
            taxableSalary = groupSumTaxable.get(0) - getChildfromPos(4, 0).getComponentsSum();
            tempTextView.setText(formatter.format(taxableSalary));
        }

        if (taxableSalary.intValue() == 0) {
            int id = res.getIdentifier("taxCalcTaxableSalary", "id", getPackageName());
            tempRow = (TableRow) findViewById(id);
            tempRow.setVisibility(View.GONE);
            grossTaxonSalary = Double.valueOf(0);
        } else {
            if (taxableSalary <= 400000) {
                grossTaxonSalary = Double.valueOf(0);
            } else if (taxableSalary > 400000 && taxableSalary <= 750000) {
                grossTaxonSalary = (taxableSalary - 400000) * 0.05;
            } else if (taxableSalary > 750000 && taxableSalary <= 1400000) {
                grossTaxonSalary = 17500 + (taxableSalary - 750000) * 0.1;
            } else if (taxableSalary > 1400000 && taxableSalary <= 1500000) {
                grossTaxonSalary = 82500 + (taxableSalary - 1400000) * 0.125;
            } else if (taxableSalary > 1500000 && taxableSalary <= 1800000) {
                grossTaxonSalary = 95000 + (taxableSalary - 1500000) * 0.15;
            } else if (taxableSalary > 1800000 && taxableSalary <= 2500000) {
                grossTaxonSalary = 140000 + (taxableSalary - 1800000) * 0.175;
            } else if (taxableSalary > 2500000 && taxableSalary <= 3000000) {
                grossTaxonSalary = 262500 + (taxableSalary - 2500000) * 0.2;
            } else if (taxableSalary > 3000000 && taxableSalary <= 3500000) {
                grossTaxonSalary = 362500 + (taxableSalary - 3000000) * 0.225;
            } else if (taxableSalary > 3500000 && taxableSalary <= 4000000) {
                grossTaxonSalary = 475000 + (taxableSalary - 3500000) * 0.25;
            } else if (taxableSalary > 4000000 && taxableSalary <= 7000000) {
                grossTaxonSalary = 600000 + (taxableSalary - 4000000) * 0.275;
            } else if (taxableSalary > 7000000) {
                grossTaxonSalary = 1425000 + (taxableSalary - 7000000) * 0.3;
            }
            int id = res.getIdentifier("taxCalcGrossTaxPKR", "id", getPackageName());
            tempTextView = (TextView) findViewById(id);
            tempTextView.setText(formatter.format(grossTaxonSalary));

            Double reductionFromSenior;

            if (getChildfromPos(5, 3).getChildPeriod(0)) {
                if (taxableSalary <= 1000000) {
                    reductionFromSenior = grossTaxonSalary * 0.5;
                } else {
                    reductionFromSenior = Double.valueOf(0);
                }
                int id1 = res.getIdentifier("taxCalcSeniorCitizenPKR", "id", getPackageName());
                tempTextView = (TextView) findViewById(id1);
                tempTextView.setText("(" + formatter.format(reductionFromSenior) + ")");
            } else {
                reductionFromSenior = Double.valueOf(0);
                int id1 = res.getIdentifier("taxCalcSeniorCitizen", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
            }
            Double reductionFromTeacher;
            if (getChildfromPos(5, 2).getChildPeriod(0)) {
                reductionFromTeacher = (grossTaxonSalary - reductionFromSenior) * 0.4;
                int id1 = res.getIdentifier("taxCalcTeacherPKR", "id", getPackageName());
                tempTextView = (TextView) findViewById(id1);
                tempTextView.setText("(" + formatter.format(reductionFromTeacher) + ")");
            } else {
                reductionFromTeacher = Double.valueOf(0);
                int id1 = res.getIdentifier("taxCalcTeacher", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
            }
            Double grossTaxReduced = grossTaxonSalary - reductionFromSenior - reductionFromTeacher;
            Double creditFromDonation;
            if (getChildfromPos(4, 1).getComponentsSum().intValue() == 0) {
                creditFromDonation = Double.valueOf(0);
                int id1 = res.getIdentifier("taxCalcDonation", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
            } else {
                Double Cvalue;
                if (getChildfromPos(4, 1).getComponentsSum() <= (taxableSalary * 0.3)) {
                    Cvalue = getChildfromPos(4, 1).getComponentsSum();
                } else {
                    Cvalue = taxableSalary * 0.3;
                }
                creditFromDonation = (grossTaxReduced / taxableSalary) * Cvalue;
                int id1 = res.getIdentifier("taxCalcDonationPKR", "id", getPackageName());
                tempTextView = (TextView) findViewById(id1);
                tempTextView.setText("(" + formatter.format(creditFromDonation) + ")");
            }
            Double creditFromInvestment;
            if (getChildfromPos(5, 0).getComponentsSum().intValue() == 0) {
                creditFromInvestment = Double.valueOf(0);
                int id1 = res.getIdentifier("taxCalcInvestment", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
            } else {
                Double Cvalue;
                Cvalue = Math.min(1000000, getChildfromPos(5, 0).getComponentsSum());
                Cvalue = Math.min(Cvalue, taxableSalary * 0.2);
                creditFromInvestment = (grossTaxReduced / taxableSalary) * Cvalue;
                int id1 = res.getIdentifier("taxCalcInvestmentPKR", "id", getPackageName());
                tempTextView = (TextView) findViewById(id1);
                tempTextView.setText("(" + formatter.format(creditFromInvestment) + ")");
            }
            Double creditFromPension;
            if (getChildfromPos(5, 1).getComponentsSum().intValue() == 0) {
                creditFromPension = Double.valueOf(0);
                int id1 = res.getIdentifier("taxCalcPension", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
            } else {
                Double Cvalue;
                Cvalue = Math.min(taxableSalary * 0.2, getChildfromPos(5, 1).getComponentsSum());
                creditFromPension = (grossTaxReduced / taxableSalary) * Cvalue;
                int id1 = res.getIdentifier("taxCalcPensiontPKR", "id", getPackageName());
                tempTextView = (TextView) findViewById(id1);
                tempTextView.setText("(" + formatter.format(creditFromPension) + ")");
            }
            Double netTaxonSalary = grossTaxonSalary - reductionFromSenior - reductionFromTeacher - creditFromDonation - creditFromInvestment - creditFromPension;
            int id2 = res.getIdentifier("taxCalcNetTaxPKR", "id", getPackageName());
            tempTextView = (TextView) findViewById(id2);
            tempTextView.setText(formatter.format(netTaxonSalary));

            if (netTaxonSalary.equals(grossTaxonSalary) && netTaxonSalary.intValue() != 0) {
                int id1 = res.getIdentifier("taxCalcGrossTax", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
            }
        }

        Double rentalTax;
        if (groupSumTaxable.get(1).intValue() == 0) {
            int id1 = res.getIdentifier("dividerhead1", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcRentalIncome", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcRentalTax", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            rentalTax = Double.valueOf(0);
        } else {
            int id1 = res.getIdentifier("taxCalcRentalIncomePKR", "id", getPackageName());
            tempTextView1 = (TextView) findViewById(id1);
            tempTextView1.setText(formatter.format(groupSumTaxable.get(1)));
            if (groupSumTaxable.get(1) <= 150000) {
                rentalTax = Double.valueOf(0);
            } else if (groupSumTaxable.get(1) > 150000 && groupSumTaxable.get(1) <= 1000000) {
                rentalTax = (groupSumTaxable.get(1) - 150000) * 0.1;
            } else {
                rentalTax = 85000 + (groupSumTaxable.get(1) - 1000000) * 0.15;
            }
            int id3 = res.getIdentifier("taxCalcRentalTaxPKR", "id", getPackageName());
            tempTextView1 = (TextView) findViewById(id3);
            tempTextView1.setText(formatter.format(rentalTax));
        }

        Double CGTsecurities, CGTsecurities0, CGTsecurities1, CGTsecurities2;
        Double CGTimmovableprop, CGTimmovableprop0, CGTimmovableprop1, CGTimmovableprop2;

        if (groupSumTaxable.get(2).intValue() == 0) {
            int id1 = res.getIdentifier("dividerhead2", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcCGIncome", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcCGTax", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcBelow1", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcBtw12", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcGreater2", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcCGImmov", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcCGImmovTax", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcImmovBelow1", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcImmovBtw12", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            id1 = res.getIdentifier("taxCalcImmovGreater2", "id", getPackageName());
            tempRow = (TableRow) findViewById(id1);
            tempRow.setVisibility(View.GONE);
            CGTsecurities = Double.valueOf(0);
            CGTimmovableprop = Double.valueOf(0);

        } else {
            if (getChildfromPos(2, 0).getComponentsSum().intValue() == 0) {
                int id1 = res.getIdentifier("taxCalcCGTax", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
                id1 = res.getIdentifier("taxCalcBelow1", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
                id1 = res.getIdentifier("taxCalcBtw12", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
                id1 = res.getIdentifier("taxCalcGreater2", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
                CGTsecurities = Double.valueOf(0);
            } else {
                int id4 = res.getIdentifier("taxCalcCGIncomePKR", "id", getPackageName());
                tempTextView1 = (TextView) findViewById(id4);
                tempTextView1.setText(formatter.format(getChildfromPos(2, 0).getComponentsSum()));
                if (getChildfromPos(2, 0).getChildComponent(0).intValue() == 0) {
                    int id1 = res.getIdentifier("taxCalcBelow1", "id", getPackageName());
                    tempRow = (TableRow) findViewById(id1);
                    tempRow.setVisibility(View.GONE);
                    CGTsecurities0 = Double.valueOf(0);
                } else {
                    CGTsecurities0 = getChildfromPos(2, 0).getChildComponent(0) * 0.125;
                    int id1 = res.getIdentifier("taxCalcBelow1PKR", "id", getPackageName());
                    tempTextView1 = (TextView) findViewById(id1);
                    tempTextView1.setText(formatter.format(CGTsecurities0));
                }
                if (getChildfromPos(2, 0).getChildComponent(1).intValue() == 0) {
                    int id1 = res.getIdentifier("taxCalcBtw12", "id", getPackageName());
                    tempRow = (TableRow) findViewById(id1);
                    tempRow.setVisibility(View.GONE);
                    CGTsecurities1 = Double.valueOf(0);
                } else {
                    CGTsecurities1 = getChildfromPos(2, 0).getChildComponent(1) * 0.1;
                    int id1 = res.getIdentifier("taxCalcBtw12PKR", "id", getPackageName());
                    tempTextView1 = (TextView) findViewById(id1);
                    tempTextView1.setText(formatter.format(CGTsecurities1));
                }
                if (getChildfromPos(2, 0).getChildComponent(2).intValue() == 0) {
                    int id1 = res.getIdentifier("taxCalcGreater2", "id", getPackageName());
                    tempRow = (TableRow) findViewById(id1);
                    tempRow.setVisibility(View.GONE);
                    CGTsecurities2 = Double.valueOf(0);
                } else {
                    CGTsecurities2 = getChildfromPos(2, 0).getChildComponent(2) * 0;
                    int id1 = res.getIdentifier("taxCalcGreater2PKR", "id", getPackageName());
                    tempTextView1 = (TextView) findViewById(id1);
                    tempTextView1.setText(formatter.format(CGTsecurities2));

                }
                CGTsecurities = CGTsecurities0 + CGTsecurities1 + CGTsecurities2;
                int id1 = res.getIdentifier("taxCalcCGTaxPKR", "id", getPackageName());
                tempTextView1 = (TextView) findViewById(id1);
                tempTextView1.setText(formatter.format(CGTsecurities));
            }


            if (getChildfromPos(2, 1).getComponentsSum().intValue() == 0) {
                int id1 = res.getIdentifier("taxCalcCGImmovTax", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
                id1 = res.getIdentifier("taxCalcImmovBelow1", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
                id1 = res.getIdentifier("taxCalcImmovBtw12", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
                id1 = res.getIdentifier("taxCalcImmovGreater2", "id", getPackageName());
                tempRow = (TableRow) findViewById(id1);
                tempRow.setVisibility(View.GONE);
                CGTimmovableprop = Double.valueOf(0);
            } else {
                int id4 = res.getIdentifier("taxCalcCGImmovPKR", "id", getPackageName());
                tempTextView1 = (TextView) findViewById(id4);
                tempTextView1.setText(formatter.format(getChildfromPos(2, 1).getComponentsSum()));
                if (getChildfromPos(2, 1).getChildComponent(0).intValue() == 0) {
                    int id1 = res.getIdentifier("taxCalcImmovBelow1", "id", getPackageName());
                    tempRow = (TableRow) findViewById(id1);
                    tempRow.setVisibility(View.GONE);
                    CGTimmovableprop0 = Double.valueOf(0);
                } else {
                    CGTimmovableprop0 = getChildfromPos(2, 1).getChildComponent(0) * 0.1;
                    int id1 = res.getIdentifier("taxCalcImmovBelow1PKR", "id", getPackageName());
                    tempTextView1 = (TextView) findViewById(id1);
                    tempTextView1.setText(formatter.format(CGTimmovableprop0));
                }
                if (getChildfromPos(2, 1).getChildComponent(1).intValue() == 0) {
                    int id1 = res.getIdentifier("taxCalcImmovBtw12", "id", getPackageName());
                    tempRow = (TableRow) findViewById(id1);
                    tempRow.setVisibility(View.GONE);
                    CGTimmovableprop1 = Double.valueOf(0);
                } else {
                    CGTimmovableprop1 = getChildfromPos(2, 1).getChildComponent(1) * 0.05;
                    int id1 = res.getIdentifier("taxCalcImmovBtw12PKR", "id", getPackageName());
                    tempTextView1 = (TextView) findViewById(id1);
                    tempTextView1.setText(formatter.format(CGTimmovableprop1));
                }
                if (getChildfromPos(2, 1).getChildComponent(2).intValue() == 0) {
                    int id1 = res.getIdentifier("taxCalcImmovGreater2", "id", getPackageName());
                    tempRow = (TableRow) findViewById(id1);
                    tempRow.setVisibility(View.GONE);
                    CGTimmovableprop2 = Double.valueOf(0);
                } else {
                    CGTimmovableprop2 = getChildfromPos(2, 1).getChildComponent(2) * 0;
                    int id1 = res.getIdentifier("taxCalcImmovGreater2PKR", "id", getPackageName());
                    tempTextView1 = (TextView) findViewById(id1);
                    tempTextView1.setText(formatter.format(CGTimmovableprop2));

                }
                CGTimmovableprop = CGTimmovableprop0 + CGTimmovableprop1 + CGTimmovableprop2;
                int id1 = res.getIdentifier("taxCalcCGImmovTaxPKR", "id", getPackageName());
                tempTextView1 = (TextView) findViewById(id1);
                tempTextView1.setText(formatter.format(CGTimmovableprop));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Toast.makeText(this, "Er", Toast.LENGTH_LONG).show();
            // create an Intent to take you over to a new DetailActivity
            Intent detailIntent = new Intent(this, AboutPage.class);

            // TODO: add any other data you'd like as Extras

            // start the next Activity using your prepared Intent
            startActivity(detailIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public Child getChildfromPos(int gPos, int cPos) {
        return eGroup.get(gPos).getChild(cPos);
    }
}
