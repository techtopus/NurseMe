package com.example.nurseme;

import android.content.Intent;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
//import com.onesignal.OneSignal;

public class AdminDashboard extends AppCompatActivity {
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        mAuth=FirebaseAuth.getInstance();
       // OneSignal.sendTag("User_ID",mAuth.getCurrentUser().getEmail());
    }
    public void addnurse(View v)
    {
        Intent i =new Intent(this,SignupActivity.class);
        i.putExtra("type","nurse");
        startActivity(i);
    }
    public void updatenurse(View v)
    {
        startActivity(new Intent(this,updatenurse.class));
    }
    public void delete(View v)
    {
     startActivity(new Intent(this,deletenurse.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.menu ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;

        }
        return true;
    }

}
