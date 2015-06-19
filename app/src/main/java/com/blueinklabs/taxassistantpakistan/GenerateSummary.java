package com.blueinklabs.taxassistantpakistan;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

/**
 * Created by Dell on 6/18/2015.
 */
public class GenerateSummary extends AppCompatActivity {
    private Toolbar toolbar;
    private ArrayList<Group> eGroup;

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
        Toast.makeText(this, getChildfromPos(0, 0).getDisplayAmount(), Toast.LENGTH_SHORT).show();
        runSummaryRoutine();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void runSummaryRoutine() {
        TableRow tempRow;
        TextView tempTextView;
        Resources res = getResources();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < eGroup.get(i).getChildCount(); j++) {
                if (getChildfromPos(i, j).getComponentsSum().intValue() == 0) {
                    int id = res.getIdentifier("tableRow" + i + j, "id", getPackageName());
                    tempRow = (TableRow) findViewById(id);
                    tempRow.setVisibility(View.GONE);
                } else {
                    int id1 = res.getIdentifier("textView" + i + j + "01", "id", getPackageName());
                    tempTextView = (TextView) findViewById(id1);
                    tempTextView.setText(getChildfromPos(i, j).getDisplayAmountOnly());
                    //int id2 = res.getIdentifier("textView" + i + j + "02", "id", getPackageName());
                }
            }
            if (eGroup.get(i).isGroupEmpty()) {
                int id = res.getIdentifier("tableRowGroup" + i, "id", getPackageName());
                tempRow = (TableRow) findViewById(id);
                tempRow.setVisibility(View.GONE);
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
