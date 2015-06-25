package com.blueinklabs.taxassistantpakistan;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DecimalFormat;

/**
 * Created by Dell on 6/22/2015.
 */
public class SimpleTaxCalculator extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tell the activity which XML layout is right
        setContentView(R.layout.simple_tax_calculator);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        // Enable the "Up" button for more navigation options
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Analytics
        // Get tracker.
        Tracker t = ((AnalyticsSampleApp) getApplication()).getTracker(
                AnalyticsSampleApp.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName("SimpleTaxCalculator");
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());


        //Ads
        if (!(BuildConfig.FLAVOR == "paidversion")) {
            AdView mAdView = (AdView) findViewById(R.id.adView_calculatorpage);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(MainActivity.myDeviceID)
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
        }
        if (BuildConfig.FLAVOR == "paidversion") {
            AdView mAdView = (AdView) findViewById(R.id.adView_calculatorpage);
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
            Intent detailIntent = new Intent(this, AboutPage.class);

            // TODO: add any other data you'd like as Extras

            // start the next Activity using your prepared Intent
            startActivity(detailIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        EditText tempEdit = (EditText) findViewById(R.id.editText);
        Double taxableSalary = Double.parseDouble(tempEdit.getText().toString());
        taxableSalary = taxableSalary * 12;
        Double grossTaxonSalary = (double) 0;
        Resources res = getResources();
        DecimalFormat formatter = new DecimalFormat("#,###");
        TextView tempTextView;

        if (taxableSalary.intValue() == 0) {
            Toast.makeText(this, "Enter a monthly salary", Toast.LENGTH_SHORT).show();
            grossTaxonSalary = (double) 0;
        } else {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            if (taxableSalary <= 400000) {
                grossTaxonSalary = (double) 0;
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
            int id = res.getIdentifier("textView42", "id", getPackageName());
            tempTextView = (TextView) findViewById(id);
            tempTextView.setText(formatter.format(grossTaxonSalary));
            id = res.getIdentifier("textView32", "id", getPackageName());
            tempTextView = (TextView) findViewById(id);
            tempTextView.setText(formatter.format(grossTaxonSalary / 12));
            id = res.getIdentifier("textView31", "id", getPackageName());
            tempTextView = (TextView) findViewById(id);
            tempTextView.setText(formatter.format(taxableSalary / 12));
            id = res.getIdentifier("textView41", "id", getPackageName());
            tempTextView = (TextView) findViewById(id);
            tempTextView.setText(formatter.format(taxableSalary));
            id = res.getIdentifier("textView33", "id", getPackageName());
            tempTextView = (TextView) findViewById(id);
            tempTextView.setText(formatter.format((taxableSalary / 12) - (grossTaxonSalary / 12)));
            id = res.getIdentifier("textView43", "id", getPackageName());
            tempTextView = (TextView) findViewById(id);
            tempTextView.setText(formatter.format(taxableSalary - grossTaxonSalary));

            LinearLayout tempLinear = (LinearLayout) findViewById(R.id.main_linear_layout);
            tempLinear.setVisibility(View.VISIBLE);
        }
    }
}
