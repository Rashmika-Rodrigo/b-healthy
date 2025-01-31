package com.s22010177.b_healthy;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NotesActivity extends AppCompatActivity {
    DatabaseHelper myDb; // Declaring DatabaseHelper object
    EditText noteId, date, bmi, step, sleep; // Declaring TextInput fields
    int userId; // Variable to store logged-in user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        // Initializing database
        myDb = new DatabaseHelper(this);

        // Initializing TextInput fields
        noteId = findViewById(R.id.editTextNoteID);
        date = findViewById(R.id.editTextDate);
        bmi = findViewById(R.id.editTextBMI);
        step = findViewById(R.id.editTextSteps);
        sleep = findViewById(R.id.editTextSleepHours);

        // Retrieve logged-in user's ID
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
    }

    // Method to insert new notes
    public void AddNote(View view) {
        String noteDate = date.getText().toString().trim();
        String noteBmi = bmi.getText().toString().trim();
        String noteStep = step.getText().toString().trim();
        String noteSleep = sleep.getText().toString().trim();

        if (noteDate.isEmpty()) {
            Toast.makeText(NotesActivity.this, "Please Fill the Date", Toast.LENGTH_LONG).show();
            return;
        }

        // Insert note with the logged-in user's ID
        boolean isInserted = myDb.insertNote(noteDate, noteBmi, noteStep, noteSleep, (Integer.parseInt(String.valueOf(userId))));

        if (isInserted) {
            Toast.makeText(NotesActivity.this, "Your Note Was Added Successfully", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(NotesActivity.this, "Sorry, Something Went Wrong", Toast.LENGTH_LONG).show();
        }
    }

    // Method to view all notes
    public void ViewAll(View view) {
        Cursor results = myDb.viewAllNotes(Integer.parseInt(String.valueOf(userId))); // Retrieve notes for the logged-in user
        if (results.getCount() == 0) {
            showMessage("Error ! ", "No Data Available");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (results.moveToNext()) {
            buffer.append("Note_ID : " + results.getString(0) + "\n");
            buffer.append("Date : " + results.getString(1) + "\n");
            buffer.append("BMI Value : " + results.getString(2) + "\n");
            buffer.append("Step Count : " + results.getString(3) + "\n");
            buffer.append("Sleeping Hours : " + results.getString(4) + "\n\n");
        }
        showMessage("All Notes", buffer.toString());
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    // Method to update notes
    public void UpdateNote(View view) {
        boolean isUpdate = myDb.updateNote(noteId.getText().toString(), date.getText().toString(),
                bmi.getText().toString(), step.getText().toString(), sleep.getText().toString());
        if (isUpdate) {
            Toast.makeText(NotesActivity.this, "Note Updated Successfully", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(NotesActivity.this, "Sorry, Something Went Wrong", Toast.LENGTH_LONG).show();
        }
    }

    // Method to delete notes
    public void RemoveNote(View view) {
        Integer deletedRows = myDb.deleteData(noteId.getText().toString());
        if (deletedRows > 0) {
            Toast.makeText(NotesActivity.this, "Note Deleted Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(NotesActivity.this, "Sorry, Something Went Wrong", Toast.LENGTH_LONG).show();
        }
    }

    // Method to navigate back to home
    public void NavigateBack(View view) {
        finish();
    }
}