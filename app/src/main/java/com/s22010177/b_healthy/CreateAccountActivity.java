package com.s22010177.b_healthy;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {
    DatabaseHelper myDb; // Declaring DatabaseHelper object
    EditText newUsername, newPassword, confirmPassword; // Declaring input fields
    ImageView showHideNewPasswordIcon, showHideConfirmPasswordIcon; // Declaring Icons
    boolean isNewPasswordVisible = false; // Set new password hide by default
    boolean isConfirmPasswordVisible = false; // Set confirm password hide by default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initializing DatabaseHelper object
        myDb = new DatabaseHelper(this);

        // Initializing input fields
        newUsername = findViewById(R.id.txtNewUsername);
        newPassword = findViewById(R.id.txtNewPassword);
        confirmPassword = findViewById(R.id.txtConfirmPassword);

        // Initializing the show/hide icon for new_password and confirm_password
        showHideNewPasswordIcon = findViewById(R.id.showHideNewPasswordIcon);
        showHideConfirmPasswordIcon = findViewById(R.id.showHideConfirmPasswordIcon);

        // Setting onClickListener for show/hide_new_password icon
        showHideNewPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewPasswordVisible) {  // Hide Password
                    newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideNewPasswordIcon.setImageResource(R.drawable.eye_closed_icon);
                }
                else {  // Show Password
                    newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideNewPasswordIcon.setImageResource(R.drawable.eye_opened_icon);
                }
                isNewPasswordVisible = !isNewPasswordVisible;
                newPassword.setSelection(newPassword.length());  // Moving the cursor to the end of the text
            }
        });

        // Setting onClickListener for show/hide_confirm_password icon
        showHideConfirmPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConfirmPasswordVisible) {  // Hide Password
                    confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideConfirmPasswordIcon.setImageResource(R.drawable.eye_closed_icon);
                } else {  // Show Password
                    confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideConfirmPasswordIcon.setImageResource(R.drawable.eye_opened_icon);
                }
                isConfirmPasswordVisible = !isConfirmPasswordVisible;
                confirmPassword.setSelection(confirmPassword.length());  // Moving the cursor to the end of the text
            }
        });
    }

    // Method to insert new user data
    public void addUser(View view) {
        String UN = newUsername.getText().toString().trim();
        String PW = newPassword.getText().toString().trim();
        String CPW = confirmPassword.getText().toString().trim();

        // Check if the username, password, or confirm password fields are empty
        if (UN.isEmpty() || PW.isEmpty() || CPW.isEmpty()) {
            Toast.makeText(CreateAccountActivity.this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the username is at least 4 characters long
        if (UN.length() < 4) {
            Toast.makeText(CreateAccountActivity.this, "Username Must Contain at Least 4 Characters",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the password is at least 4 characters long
        if (PW.length() < 4) {
            Toast.makeText(CreateAccountActivity.this, "Password Must Contain at Least 4 Characters",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the password and confirm password fields match
        if (!PW.equals(CPW)) {
            Toast.makeText(CreateAccountActivity.this, "Password is Not Matching. Please Check Again",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUsernameAlreadyExists = myDb.checkUsername(UN);

        if(isUsernameAlreadyExists){
            Toast.makeText(CreateAccountActivity.this, "The Username Already Exists, Please Create another",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // If satisfying all the validations, inserting new user data
        boolean isInserted = myDb.insertUserData(UN, PW);

        if (isInserted) {
            Toast.makeText(CreateAccountActivity.this, "Your New Account Created Successfully",
                    Toast.LENGTH_SHORT).show();
            newUsername.setText("");
            newPassword.setText("");
            confirmPassword.setText("");
        }
        else {
            Toast.makeText(CreateAccountActivity.this, "Sorry, Something Went Wrong",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void GoToLogin(View view) {  // Method to navigate back to login activity
        finish();
    }
}