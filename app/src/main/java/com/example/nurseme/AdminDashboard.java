package com.example.nurseme;

import android.content.Intent;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
    }
    public void addnurse(View v)
    {
        Intent i =new Intent(this,add_nurse.class);
        i.putExtra("type","nurse");
        startActivity(i);
    }
}
