package com.s22010177.b_healthy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper myDb; // Declaring DatabaseHelper object
    EditText username, password; // Declaring input fields
    ImageView showHidePasswordIcon; // Declaring Icon for show/hide password
    boolean isPasswordVisible = false; // Set password hide by default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing DatabaseHelper object
        myDb = new DatabaseHelper(this);

        // Initializing input fields
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);

        // Initializing the show/hide password icon
        showHidePasswordIcon = findViewById(R.id.showHidePasswordIcon);

        // Setting onClickListener for show/hide password icon
        showHidePasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {  // Hide Password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHidePasswordIcon.setImageResource(R.drawable.eye_closed_icon);
                }
                else {  // Show Password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHidePasswordIcon.setImageResource(R.drawable.eye_opened_icon);
                }
                isPasswordVisible = !isPasswordVisible;
                password.setSelection(password.length());  // Moving the cursor to the end of the text
            }
        });
    }

    public void UserLogin(View view) {
        String UN = username.getText().toString().trim();
        String PW = password.getText().toString().trim();

        // Check if the username and password fields are empty
        if (UN.isEmpty() || PW.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the username and password are valid
        boolean isValidCredentials = myDb.checkCredentials(UN, PW);

        // If yes, get user ID and save it
        if (isValidCredentials) {
            int userId = myDb.getUserId(UN, PW);
            if (userId != -1) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("userId", userId);
                editor.apply();

                // Navigate to home activity
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                username.setText("");
                password.setText("");
            }
            else {
                Toast.makeText(LoginActivity.this, "Sorry, Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
        else { // If no, display error message
            Toast.makeText(LoginActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
        }
    }

    public void GoToCreateAccount(View view) { // Method to navigate create account activity
        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }
}