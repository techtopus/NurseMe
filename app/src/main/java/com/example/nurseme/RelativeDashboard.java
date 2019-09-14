package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

public class RelativeDashboard extends AppCompatActivity {
FirebaseAuth mAuth;
ProgressBar pg;
Button add_btn;
CardView add_patient_card,search_nurse_card,remainder_card,contacts_card,payment_card,health_card,account_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_dashboard);
       // add_btn=findViewById(R.id.add_btn);
        mAuth=FirebaseAuth.getInstance();
        pg=findViewById(R.id.progressBar);
        add_patient_card=findViewById(R.id.add_patient_card_view);
        account_card=findViewById(R.id.account_card_view);
        search_nurse_card=findViewById(R.id.search_card_view);
        remainder_card=findViewById(R.id.reminders_card_view);
        contacts_card=findViewById(R.id.contacts_card_view);
        payment_card=findViewById(R.id.payment_card_view);
        health_card=findViewById(R.id.health_card_view);
         DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
         Query query2 = reference2.child("Patient").orderByChild("relativename").equalTo(mAuth.getCurrentUser().getUid());
         query2.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
         if ( dataSnapshot.exists()) {
         //add_btn.setVisibility(View.GONE);
             pg.setVisibility(View.GONE);

         search_nurse_card.setVisibility(View.VISIBLE);
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




}
