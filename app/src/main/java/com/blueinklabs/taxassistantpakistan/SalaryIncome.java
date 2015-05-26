package com.blueinklabs.taxassistantpakistan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

public class SalaryIncome extends AppCompatActivity {
    private Toolbar toolbar;
    private ExpandListAdapter ExpAdapter;
    private ArrayList<Group> ExpListItems;
    private ExpandableListView ExpandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tell the activity which XML layout is right
        setContentView(R.layout.salary_income);
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
        ExpAdapter = new ExpandListAdapter(SalaryIncome.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);



    }

    public ArrayList<Group> SetStandardGroups() {
        String group_names[] = {"GROUP A", "GROUP B"};

        String country_names[] = {"Brazil", "Mexico", "Croatia", "Cameroon",
                "Netherlands", "Chile", "United States", "United Kingdom"};

        int Images[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

        ArrayList<Group> list = new ArrayList<Group>();

        ArrayList<Child> ch_list;

        int size = 4;
        int j = 0;

        for (String group_name : group_names) {
            Group gru = new Group();
            gru.setName(group_name);

            ch_list = new ArrayList<Child>();
            for (; j < size; j++) {
                Child ch = new Child();
                ch.setName(country_names[j]);
                ch.setImage(Images[j]);
                ch_list.add(ch);
            }
            gru.setItems(ch_list);
            list.add(gru);

            size = size + 4;
        }

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

}
