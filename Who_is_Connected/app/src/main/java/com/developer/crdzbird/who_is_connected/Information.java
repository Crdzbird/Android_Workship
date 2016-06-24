package com.developer.crdzbird.who_is_connected;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Information extends AppCompatActivity {

    private Button btnAbout;
    private Button btnChangeLog;
    private Button btnRateThisApp;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


        btnAbout = (Button) findViewById(R.id.information_about_button);
        btnChangeLog = (Button) findViewById(R.id.information_updates_log);
        btnRateThisApp = (Button) findViewById(R.id.information_rate_this_app_btn);


        btnAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                showLicense();

            }
        });

        btnChangeLog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                showChangeLog();

            }
        });


        btnRateThisApp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
                if (Build.VERSION.SDK_INT >= 21)
                {
                    flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
                }
                else
                {
                    flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
                }
                goToMarket.addFlags(flags);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }

            }
        });



    }

    private void showLicense() {
        License license = new License(this);
        View mLicense = license.getView();
        mLicense.refreshDrawableState();
        new AlertDialog.Builder(Information.this)
                .setView(mLicense)
                .show();
    }

    private void showChangeLog() {
        ChangeLog changelog = new ChangeLog(this);
        View mChangeLog = changelog.getView();
        mChangeLog.refreshDrawableState();
        new AlertDialog.Builder(Information.this)
                .setView(mChangeLog)
                .show();
    }

}
