package com.s22010177.b_healthy;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SleepActivity extends AppCompatActivity {
    EditText sleepTime, wokeUpTime;
    TextView result;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        sleepTime = findViewById(R.id.txtSleepTime);
        wokeUpTime = findViewById(R.id.txtWokeUpTime);
    }

    public void CalculateSleep(View view) {
        String ST = sleepTime.getText().toString().trim();
        String  WT = wokeUpTime.getText().toString().trim();
        result = findViewById(R.id.txtSleepDescription);

        try {
            String difference = findDifference(ST, WT); // Calling function
            result.setText("Your Sleep Time : " + "\n" + difference + "\n\n" + msg);
        }
        catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Sorry, Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to calculate the time gap
    public String findDifference(String start_time, String end_time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d1 = sdf.parse(start_time);
        Date d2 = sdf.parse(end_time);

        if (d2.before(d1)) {
            d2.setTime(d2.getTime() + TimeUnit.DAYS.toMillis(1));
        }

        long difference_In_Time = d2.getTime() - d1.getTime();

        long minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;

        if (hours < 5) {
            msg = "You have had a very short sleep. Try to sleep at least 8 hours for a better health..";
        }
        else if (hours >= 5 && hours < 7) {
            msg = "You have had a short sleep. Try to sleep at least 8 hours..";
        }
        else if (hours >= 7 && hours <= 9) {
            msg = "You have had a good sleep. Keep it up!..";
        }
        else {
            msg = "You have had a long sleep. Oversleeping can have negative effects..";
        }

        return  hours + " hours, " +
                minutes + " minutes";
    }

    public void NavigateBack(View view) {
        finish();
    }
}