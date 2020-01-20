package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.nio.channels.InterruptedByTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NurseDashboard extends AppCompatActivity {
    FirebaseAuth mAuth;
    CardView requests,but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_dashboard);
        mAuth=FirebaseAuth.getInstance();
        requests=findViewById(R.id.prescard);
        but=findViewById(R.id.pdetailscard);
       OneSignal.sendTag("User_ID",mAuth.getCurrentUser().getEmail());
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        checkstatus();
    }

    public void earnings(View v)
    {
        startActivity(new Intent(this,PaymentActivity.class));
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("contract").orderByChild("nurseemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            ContractClass c = dataSnapshot1.getValue(ContractClass.class);
                            if(c.getStatus().equals("working"))
                            {
                                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                                SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
                                Date d1=s.parse(date);
                                String date2=c.getStartdate();
                                Date d2= s.parse(date2);
                                String str=String.valueOf( (d1.getTime() - d2.getTime())/(24*60*60*1000)*500);

                                Toast.makeText(NurseDashboard.this, "Your earnings is ₹ "+str , Toast.LENGTH_SHORT).show();

                            }
                        }
                    }catch(Exception e){
                        Toast.makeText(NurseDashboard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

public void report(View v)
{
    startActivity(new Intent(this,ReportRelativeactivity.class));
}


    public void patientdetails(View v){
        startActivity(new Intent(this,PatientDetailsOnNurseSide.class));
    }
    public void checkstatus()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //Toast.makeText(PendingRequestActivity.this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

        Query query = reference.child("contract").orderByChild("nurseemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                  //  Toast.makeText(RelativeDashboard.this, "gone", Toast.LENGTH_SHORT).show();
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            ContractClass r = dataSnapshot1.getValue(ContractClass.class);

                            if (r.getStatus().toString().equals("working")) {
                                requests.setVisibility(View.GONE);
                                but.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    catch(Exception e){
                        Toast.makeText(NurseDashboard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

}
public void health(View view)
{
    startActivity(new Intent(this,HealthNurse.class));
}
public void emergency(View v)
{
    startActivity(new Intent(this,EmergencyContactNurse.class));
}
    public void requestclick(View view){
        startActivity(new Intent(this,PendingRequestActivity.class));
    }
    public void reminder(View v)
    {
        startActivity(new Intent(this,ReminderActivity.class));
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
