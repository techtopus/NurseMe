package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RelativeDashboard extends AppCompatActivity {
FirebaseAuth mAuth;
Button add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_dashboard);
        add_btn=findViewById(R.id.add_btn);
        mAuth=FirebaseAuth.getInstance();DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Relatives").orderByChild("emailid").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                     if (dataSnapshot.exists()) {
                                                         try {
                                                             for (DataSnapshot ca : dataSnapshot.getChildren()) {
                                                                 Relative r = ca.getValue(Relative.class);

                                                                 DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                                                                 Query query2 = reference2.child("Patient").orderByChild("relativename").equalTo(r.getName());
                                                                 query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                     @Override
                                                                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                         if ( dataSnapshot.exists()) {
                                                                         add_btn.setVisibility(View.GONE);
                                                                         }
                                                                     }
                                                                     @Override
                                                                     public void onCancelled(@NonNull DatabaseError databaseError) {


                                                                         }
                                                                 });

                                                             }
                                                         } catch (Exception e) {
                                                         }
                                                     }

                                                             }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             });
    }
    public void add(View v)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Relatives").orderByChild("emailid").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot ca : dataSnapshot.getChildren()) {
                            Relative r = ca.getValue(Relative.class);
                            Intent i = new Intent(RelativeDashboard.this,AddPatient.class);
                            i.putExtra("name",r.getName());
                           // Toast.makeText(RelativeDashboard.this, r.getName(), Toast.LENGTH_SHORT).show();
                            startActivity(i);

                        }
                    }catch(Exception e){}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
