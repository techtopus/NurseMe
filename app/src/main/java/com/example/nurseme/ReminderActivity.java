package com.example.nurseme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
    }
    public void doctorsappoinment(View v)
    {
        startActivity(new Intent(this,DoctorAppoinment.class));
    }
    public void medicine(View v)
    {
        startActivity(new Intent(this,MedicineReminderactivity.class));
    }
}
