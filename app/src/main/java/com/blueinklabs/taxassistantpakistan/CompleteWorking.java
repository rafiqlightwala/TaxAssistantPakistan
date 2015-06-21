package com.blueinklabs.taxassistantpakistan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.Serializable;
import java.util.ArrayList;

public class CompleteWorking extends AppCompatActivity implements Serializable {
    private Toolbar toolbar;
    private ExpandListAdapter ExpAdapter;
    private ArrayList<Group> ExpListItems;
    private ExpandableListView ExpandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tell the activity which XML layout is right
        setContentView(R.layout.main_expandlist_view);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        // Enable the "Up" button for more navigation options
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Analytics
        // Get tracker.
        Tracker t = ((AnalyticsSampleApp) getApplication()).getTracker(
                AnalyticsSampleApp.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName("SalaryScreen");
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());


        //Ads
        AdView mAdView = (AdView) findViewById(R.id.adView_salarypage);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(MainActivity.myDeviceID)
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //check
        //ExpandableList
        ExpandList = (ExpandableListView) findViewById(R.id.exp_list);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapter(CompleteWorking.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
        ExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                /*switch (groupPosition) {
                    case 0:
                        switch (childPosition) {
                            case (0):
                                Bundle args = new Bundle();
                                args.putInt("gPosition", groupPosition);
                                args.putInt("cPosition", childPosition);
                                InputDialogFragment newFragment = new InputDialogFragment();
                                newFragment.setArguments(args);
                                if (getFragmentManager().findFragmentByTag("newtag") == null) {
                                    newFragment.show(getFragmentManager(), "newtag");
                                }
                                break;
                            case (1):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (2):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (3):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (4):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                    case 1:
                        switch (childPosition) {
                            case (0):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (1):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                    case 2:
                        switch (childPosition) {
                            case (0):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (1):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                    case 3:
                        switch (childPosition) {
                            case (0):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (1):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (2):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (3):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                    case 4:
                        switch (childPosition) {
                            case (0):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (1):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                    case 5:
                        switch (childPosition) {
                            case (0):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (1):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (2):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                    case 6:
                        switch (childPosition) {
                            case (0):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (1):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (2):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (3):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (4):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (5):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (6):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (7):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (8):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (9):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                            case (10):
                                Toast.makeText(getApplicationContext(), "Group " + groupPosition + " Child: " + childPosition, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                }*/
                Bundle args = new Bundle();
                args.putInt("gPosition", groupPosition);
                args.putInt("cPosition", childPosition);
                InputDialogFragment newFragment = new InputDialogFragment();
                newFragment.setArguments(args);
                if (getFragmentManager().findFragmentByTag("newtag") == null) {
                    newFragment.show(getFragmentManager(), "newtag");
                }
                return false;
            }
        });
    }

    public ArrayList<Group> SetStandardGroups() {
        String group_names[] = {"INCOME FROM SALARY", "INCOME FROM RENT", "CAPITAL GAINS", "INCOME FROM OTHER SOURCES", "DEDUCTIBLE ALLOWANCES", "TAX REDUCTIONS & CREDITS", "ADVANCE TAX (ADJUSTABLE)"};

        ArrayList<Group> list = new ArrayList<Group>();
        ArrayList<Child> ch_list;

        for (String group_name : group_names) {
            Group gru = new Group();
            gru.setName(group_name);
            ch_list = new ArrayList<Child>();
            Child child1, child2, child3, child4, child5, child6, child7, child8, child9, child10;
            switch (group_name) {
                case "INCOME FROM SALARY":
                    child1 = new Child();
                    child1.setName("PAY, WAGES & REMUNERATION");
                    ch_list.add(child1);
                    child2 = new Child();
                    child2.setName("MEDICAL ALLOWANCE");
                    ch_list.add(child2);
                    child3 = new Child();
                    child3.setName("OTHER ALLOWANCES");
                    ch_list.add(child3);
                    child4 = new Child();
                    child4.setName("EXPENDITURE REIMBURSEMENTS");
                    ch_list.add(child4);
                    break;
                case "INCOME FROM RENT":
                    child1 = new Child();
                    child1.setName("RENTAL INCOME");
                    ch_list.add(child1);
                    child2 = new Child();
                    child2.setName("EXPENSES OR DEDUCTIONS");
                    ch_list.add(child2);
                    break;
                case "CAPITAL GAINS":
                    child1 = new Child();
                    child1.setName("CAPITAL GAINS ON SECURITIES");
                    ch_list.add(child1);
                    child2 = new Child();
                    child2.setName("CAPITAL GAINS ON IMMOVABLE PROPERTY");
                    ch_list.add(child2);
                    break;
                case "INCOME FROM OTHER SOURCES":
                    child1 = new Child();
                    child1.setName("DIVIDEND");
                    ch_list.add(child1);
                    child2 = new Child();
                    child2.setName("PROFIT ON DEBT");
                    ch_list.add(child2);
                    child3 = new Child();
                    child3.setName("PRIZE BONDS, LOTTERY & PUZZLES ETC");
                    ch_list.add(child3);
                    child4 = new Child();
                    child4.setName("BONUS SHARES");
                    ch_list.add(child4);
                    break;
                case "DEDUCTIBLE ALLOWANCES":
                    child1 = new Child();
                    child1.setName("ZAKAT");
                    ch_list.add(child1);
                    child2 = new Child();
                    child2.setName("CHARITABLE DONATIONS");
                    ch_list.add(child2);
                    break;
                case "TAX REDUCTIONS & CREDITS":
                    child1 = new Child();
                    child1.setName("NEW SHARES, MUTUAL FUNDS & INSURANCE PREMIUM");
                    ch_list.add(child1);
                    child3 = new Child();
                    child3.setName("INVESTMENT IN VOLUNTARY PENSION FUNDS");
                    ch_list.add(child3);
                    child2 = new Child();
                    child2.setName("FULL TIME TEACHER OR RESEARCHER");
                    ch_list.add(child2);
                    child4 = new Child();
                    child4.setName("SENIOR CITIZEN ABOVE 60");
                    ch_list.add(child4);
                    break;
                case "ADVANCE TAX (ADJUSTABLE)":
                    child1 = new Child();
                    child1.setName("TAX ON SALARY ALREADY DEDUCTED BY EMPLOYER");
                    ch_list.add(child1);
                    child8 = new Child();
                    child8.setName("ADVANCE TAX ON MOBILE OR PHONE BILLS");
                    ch_list.add(child8);
                    child2 = new Child();
                    child2.setName("ADVANCE TAX ON CASH WITHDRAWAL FROM BANK");
                    ch_list.add(child2);
                    child3 = new Child();
                    child3.setName("ADVANCE TAX RELATED TO MOTOR VEHICLES");
                    ch_list.add(child3);
                    child4 = new Child();
                    child4.setName("ADVANCE TAX ON AIR TICKETS");
                    ch_list.add(child4);
                    child5 = new Child();
                    child5.setName("ADVANCE TAX ON TRANSACTION OF IMMOVABLE PROPERTY");
                    ch_list.add(child5);
                    child6 = new Child();
                    child6.setName("ADVANCE TAX ON FUNCTIONS & GATHERINGS");
                    ch_list.add(child6);
                    child7 = new Child();
                    child7.setName("ADVANCE TAX ON EDUCATIONAL FEE");
                    ch_list.add(child7);
                    child9 = new Child();
                    child9.setName("ADVANCE TAX ON ELECTRICITY BILLS");
                    ch_list.add(child9);
                    child10 = new Child();
                    child10.setName("OTHER ADVANCE TAX");
                    ch_list.add(child10);
                    break;
                default:
                    break;
            }
            gru.setItems(ch_list);
            list.add(gru);
        }

        /*
        ch_list = new ArrayList<Child>();
        for (; j < size; j++) {
            Child ch = new Child();
            ch.setName(country_names[j]);
            ch.setImage(Images[j]);
            ch_list.add(ch);
        }
        gru.setItems(ch_list);
        */

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            // create an Intent to take you over to a new DetailActivity
            Intent detailIntent = new Intent(this, AboutPage.class);

            // TODO: add any other data you'd like as Extras

            // start the next Activity using your prepared Intent
            startActivity(detailIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Child getChildfromPos(int grPosition, int chPosition) {
        return (Child) ExpAdapter.getChild(grPosition, chPosition);
    }

    public ExpandListAdapter getExpAdapter() {
        return ExpAdapter;
    }

    public static void addFragmentOnlyOnce(FragmentManager fragmentManager, Fragment fragment, String tag) {
        // Make sure the current transaction finishes first
        fragmentManager.executePendingTransactions();

        // If there is no fragment yet with this tag...
        if (fragmentManager.findFragmentByTag(tag) == null) {
            // Add it
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(fragment, tag);
            transaction.commit();
        }
    }

    public void generateSummary(View v) {

        switch (v.getId()) {
            case R.id.generate_button:
                Intent summaryIntent = new Intent(this, GenerateSummary.class);
                summaryIntent.putExtra("expAdapts", ExpListItems);
                startActivity(summaryIntent);
                break;
            default:
                Toast.makeText(this, "Shouldn't Happen", Toast.LENGTH_LONG).show();
        }
    }
}