package com.s22010177.b_healthy;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BMIActivity extends AppCompatActivity {
    EditText height, weight;
    TextView result;
    String status, msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        height = findViewById(R.id.txtHeight);
        weight = findViewById(R.id.txtWeight);
        result = findViewById(R.id.txtBMI);
    }


    public void CalculateBMI(View view) {
        String heightStr = height.getText().toString().trim();
        String weightStr = weight.getText().toString().trim();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please Enter Both Height and Weight", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isNumeric(heightStr) || !isNumeric(weightStr)) {
            Toast.makeText(this, "Please Enter Valid Numeric Values for Height and Weight", Toast.LENGTH_SHORT).show();
            return;
        }

        float heightInFeet = Float.parseFloat(heightStr);
        float weightInKg = Float.parseFloat(weightStr);

        if (heightInFeet <= 0 || weightInKg <= 0) {
            Toast.makeText(this, "Please Enter Valid Height and Weight", Toast.LENGTH_SHORT).show();
            return;
        }

        // Converting feet to meters
        double heightInMeters = heightInFeet * 0.3048;

        // BMI Calculation
        double bmi = weightInKg / (heightInMeters * heightInMeters);

        // Determine BMI status and message
        if (bmi < 18.5) {
            status = "Underweight";
            msg = "Take enough nutrients and calories to support your health. It's recommended to meet a healthcare professional for personalized guidance.";
        } else if (bmi < 25) {
            status = "Normal";
            msg = "Great job! You are maintaining a healthy lifestyle. Continue this with balanced nutrients and regular physical activities.";
        } else if (bmi < 30) {
            status = "Overweight";
            msg = "Try to make small changes in your diet and increase physical activities to lose weight. It's recommended to meet a healthcare professional for personalized guidance.";
        } else {
            status = "Obese";
            msg = "Prioritize your health and well-being. It's recommended to meet a healthcare professional as soon as possible to create a plan for weight management.";
        }

        result.setText(String.format("Your BMI: %.2f\n\nStatus: %s\n\n%s", bmi, status, msg)); // Display result
    }


    // Method to check if a string is a valid numeric value
    private boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void NavigateBack(View view) {
        finish();
    }
}