package com.blueinklabs.taxassistantpakistan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Toolbar toolbar;
    ImageView iv1;
    public final static String myDeviceID = "BAF123BA0D33D61FAFC39E5EA2476219";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        //Analytics
        //AnalyticsSampleApp asp = new AnalyticsSampleApp();
        // Get tracker.
        Tracker t = ((AnalyticsSampleApp) getApplication()).getTracker(
                AnalyticsSampleApp.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName("MainScreen");
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
        // This event will also be sent with the most recently set screen name.
        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder()
                .setCategory("MainCategory")
                .setAction("MainAction")
                .setLabel("MainLabel")
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
        if (!(BuildConfig.FLAVOR == "paidversion")) {
            AdView mAdView = (AdView) findViewById(R.id.adView_mainpage);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(MainActivity.myDeviceID)
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
        }
        if (BuildConfig.FLAVOR == "paidversion") {
            TextView tempTextNote = (TextView) findViewById(R.id.main_page_text_bottom);
            tempTextNote.setText("You are using PRO version which has no ads and saves data automatically!");
            AdView mAdView = (AdView) findViewById(R.id.adView_mainpage);
            mAdView.setVisibility(View.GONE);
        }

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.simple_calculator_button:
                Intent calculatorIntent = new Intent(this, SimpleTaxCalculator.class);
                startActivity(calculatorIntent);
                break;
            case R.id.complete_tax_button:
                Intent salaryIntent = new Intent(this, CompleteWorking.class);
                startActivity(salaryIntent);
                break;
            default:
                Toast.makeText(this, "Shouldn't Happen", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

