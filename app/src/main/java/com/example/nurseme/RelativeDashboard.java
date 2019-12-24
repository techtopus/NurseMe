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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.security.spec.ECField;

public class RelativeDashboard extends AppCompatActivity {
FirebaseAuth mAuth;
ProgressBar pg;
Button add_btn;
CardView add_patient_card,search_nurse_card,remainder_card,contacts_card,payment_card,health_card,account_card,but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_dashboard);
       // add_btn=findViewById(R.id.add_btn);
        mAuth=FirebaseAuth.getInstance();
       OneSignal.sendTag("User_ID",mAuth.getCurrentUser().getEmail());
        pg=findViewById(R.id.progressBar);
        Toolbar toolbar=findViewById(R.id.toolbar);
        but=findViewById(R.id.nursedetails_card_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        add_patient_card=findViewById(R.id.add_patient_card_view);
        account_card=findViewById(R.id.callnurse_card);
        search_nurse_card=findViewById(R.id.search_card_view);
        remainder_card=findViewById(R.id.reminders_card_view);
        contacts_card=findViewById(R.id.contacts_card_view);
        payment_card=findViewById(R.id.payment_card_view);
        health_card=findViewById(R.id.health_card_view);

        checkpatient();
        }

        public void checkpatient()
        {
            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
            Query query2 = reference2.child("Patient").orderByChild("relativename").equalTo(mAuth.getCurrentUser().getUid());
            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if ( dataSnapshot.exists()) {
                        //add_btn.setVisibility(View.GONE);
                        pg.setVisibility(View.GONE);

                        search_nurse_card.setVisibility(View.VISIBLE);
                        checkstatus();
                        remainder_card.setVisibility(View.VISIBLE);
                        payment_card.setVisibility(View.VISIBLE);
                        contacts_card.setVisibility(View.VISIBLE);
                        health_card.setVisibility(View.VISIBLE);
                        account_card.setVisibility(View.VISIBLE);
                    }
                    else{
                        pg.setVisibility(View.GONE);
                        add_patient_card.setVisibility(View.VISIBLE);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    public void add(View v)
    {                       Intent i = new Intent(RelativeDashboard.this,AddPatient.class);
                           // Toast.makeText(RelativeDashboard.this, r.getName(), Toast.LENGTH_SHORT).show();
                            startActivity(i);
}
public void emergency(View v){
        startActivity(new Intent(this,EmergenyContact.class));
}
public void searchactivity(View v)
{
    startActivity(new Intent(this,SearchActivity.class));
}
    public void reminderactivity(View v)
    {
        startActivity(new Intent(this,ReminderActivity.class));
    }
    public void paymentactivity(View v)
    {
        startActivity(new Intent(this,PaymentActivity.class));
    }
    public void healthactivity(View v)
    {
        startActivity(new Intent(this,HealthActivity.class));
    }
    public void accountactivity(View v)
    {
        startActivity(new Intent(this,AccountActivity.class));
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

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null)
        { finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(mAuth.getCurrentUser().getEmail().contains("nurse"))
        { finish();
            startActivity(new Intent(this, NurseDashboard.class));
        }
        checkstatus();
    }
    public void checkstatus(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //Toast.makeText(PendingRequestActivity.this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

        Query query = reference.child("contract").orderByChild("patientemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(RelativeDashboard.this, "gone", Toast.LENGTH_SHORT).show();
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            ContractClass r = dataSnapshot1.getValue(ContractClass.class);

                            if (r.getStatus().toString().equals("working")) {
                                search_nurse_card.setVisibility(View.GONE);
                                but.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    catch(Exception e){
                        Toast.makeText(RelativeDashboard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void nursedetails(View view)
    {
        startActivity(new Intent(this,NurseDetailsOnPatientSide.class));
    }

}
