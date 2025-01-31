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

public class StepCountActivity extends AppCompatActivity implements SensorEventListener {
    TextView result; // declare text view to display step count and advice (output)
    String msg; // declare variable, msg
    SensorManager sensorManager; // declare sensor manager
    Sensor accelerometer; // declare sensor
    int stepCount = 0; // initialize the step count
    boolean isCounting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count);

        result = findViewById(R.id.txtStepCountDescription); // Initialize the TextView

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // Initialize the sensor manager

        if (sensorManager != null) {  // Check if the accelerometer sensor is available
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        else {
            Toast.makeText(this, "Sorry, Accelerometer Sensor is not Available in Your Device", Toast.LENGTH_SHORT).show();
        }
    }

    public void startCounting(View view) {  // Method to start step counting
        if (!isCounting) {
            isCounting = true;
            stepCount = 0;
            result.setText("Step Counting Started\n\n" + "Step Count : " + stepCount);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stopCounting(View view) {  // Method to stop step counting
        if (isCounting) {
            isCounting = false;
            sensorManager.unregisterListener(this);
            result.setText("Step Counting Stopped\n\n" + "Step Count : " + stepCount + "\n\n" + msg);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;   // Get the accelerometer values (x,y,z)
        float x = values[0];
        float y = values[1];
        float z = values[2];
        double magnitude = Math.sqrt(x * x + y * y + z * z);
        if (magnitude > 50) { // If the overall acceleration exceeds 100 (threshold), it is considered as a step
            stepCount++;
            result.setText("Step Counting Started\n\n" + "Step Count : " + stepCount);

            if(stepCount < 10000) {
                msg = "Not Bad! Aim for at Least 10,000 Steps Per Day for a Better Health.";
            }
            else if (stepCount >= 10000) {
                msg = "Great Job! You Have Met the Recommended Daily Step Count.";
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {  // not in use
    }

    @Override
    protected void onDestroy() { // Method to unregister the sensor listener, when paused
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    public void NavigateBack(View view) { // method to navigate back
        finish();
    }
}
