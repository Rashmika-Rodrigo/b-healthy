package com.s22010177.b_healthy;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TempActivity extends AppCompatActivity implements SensorEventListener {
    TextView tempTextView; // Declare TextView to display temperature and advice (output)
    String status, msg; // Declare variables, status and msg
    SensorManager sensorManager; // Declare SensorManager
    Sensor ambientTemperature; // Declare sensor
    boolean tempSensorIsAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        tempTextView = findViewById(R.id.txtTempDescription); // Initialize the TextView

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // Initialize the sensor manager

        if (sensorManager != null) {
            ambientTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            tempSensorIsAvailable = true;
        }
        else {
            Toast.makeText(this, "Sorry, Ambient Temperature Sensor is not Available in Your Device",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Get the current temperature from the sensor event
        float temperature = event.values[0];

        // Determine the status and message based on the temperature
        if (temperature < 10) {
            status = "Very Cold";
            msg = "Wear fully covered clothes, eat and drink hot, Avoid bathing in the evenings, stay indoors as much " +
                    "as possible..";
        }
        else if (temperature >= 10 && temperature < 22) {
            status = "Cold";
            msg = "Eat and drink hot, Avoid bathing in the evenings..";
        }
        else if (temperature >= 22 && temperature < 30) {
            status = "Moderate";
            msg = "It's a nice day! Wear comfortable clothes, Enjoy your day-today activities..";
        }
        else if (temperature >= 30 && temperature < 35) {
            status = "Warm";
            msg = "Stay hydrated, Dress comfortably, Try to avoid outdoor activities.";
        }
        else {
            status = "Very Warm";
            msg = "Stay hydrated, Wear very light clothes, Avoid exposing to the sun, Stay indoors as much as possible.";
        }

        tempTextView.setText("Temperature : " + temperature + "°C" + "\n\n" + "Status : " + status + "\n\n" + msg);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not using
    }

    @Override
    protected void onResume() {   // Method to Register the sensor listener, when resumed
        super.onResume();
        if (tempSensorIsAvailable) {
            sensorManager.registerListener(this, ambientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {  // Method to unregister the sensor listener, when paused
        super.onPause();
        if (tempSensorIsAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

    // Method to navigate back
    public void NavigateBack(View view) {
        finish();
    }
}