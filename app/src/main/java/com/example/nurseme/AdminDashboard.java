package com.example.nurseme;

import android.content.Intent;
import android.os.VibrationEffect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.onesignal.OneSignal;

public class AdminDashboard extends AppCompatActivity {
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        mAuth=FirebaseAuth.getInstance();
            OneSignal.sendTag("User_ID",mAuth.getCurrentUser().getEmail());
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    public void reports(View v){
        startActivity(new Intent(this,GenerateReport.class));
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
    }public void viewfn(View v)
    {
        startActivity(new Intent(this,acceptviewemail.class));
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

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()==null)
        { finish();
            mAuth.signInWithEmailAndPassword("bba@admin.com", "123456")
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                              //  startActivity(new Intent(a.this, AdminDashboard.class));
                            } else {

                              //  Toast.makeText(deletenurse.this, "Some Error occured! Please try again!!", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
