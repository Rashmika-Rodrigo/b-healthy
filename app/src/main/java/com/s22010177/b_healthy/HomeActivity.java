package com.s22010177.b_healthy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    // method to navigate HealthyTips activity
    public void GoToHealthyTips(View view) {
        Intent intent = new Intent(this, HealthyTipsActivity.class);
        startActivity(intent);
    }

    // method to navigate BMI activity
    public void GoToBMI(View view) {
        Intent intent = new Intent(this, BMIActivity.class);
        startActivity(intent);
    }

    // method to navigate Sleep activity
    public void GoToSleep(View view) {
        Intent intent = new Intent(this, SleepActivity.class);
        startActivity(intent);
    }

    // method to navigate Temperature activity
    public void GoToTemperature(View view) {
        Intent intent = new Intent(this, TempActivity.class);
        startActivity(intent);
    }

    // method to navigate StepCount activity
    public void GoToStepCount(View view) {
        Intent intent = new Intent(this, StepCountActivity.class);
        startActivity(intent);
    }

    // method to navigate Location activity
    public void GoToLocation(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    // method to navigate Notes activity
    public void GoToNotes(View view) {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }

    // method to Exit from the application
    public void Exit(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}