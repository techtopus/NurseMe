package com.example.nurseme;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HealthActivity extends AppCompatActivity {
TextView t,t2;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        t2=findViewById(R.id.textView55);
        t=findViewById(R.id.textView56);
        mAuth= FirebaseAuth.getInstance();
        loadvalues();
    }
    @Override
    protected void onStart() {
        super.onStart();
        loadvalues();
    }

    public void loadvalues() {
     DatabaseReference reference2r = FirebaseDatabase.getInstance().getReference("Health Data").child("Sugar");
   Query query2r = reference2r.orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail());
    query2r.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            try {
                                                for (DataSnapshot dataSnapshot5 : dataSnapshot.getChildren()) {
                                                    sugasrclass2 cm = dataSnapshot5.getValue(sugasrclass2.class);
                                                    t2.setText(String.valueOf(cm.getBeforeavg()));
                                                    t2.append(" / ");
                                                    t2.append(String.valueOf(cm.getAfteravg()));

                                                    // Toast.makeText(BPactivity.this,  cm.getEmail(), Toast.LENGTH_SHORT).show();


                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(HealthActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Health Data").child("Blood Pressure");
                                Query query2 = reference2.orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail());
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            try {
                                                for (DataSnapshot dataSnapshot5 : dataSnapshot.getChildren()) {
                                                    BloodPressure2 cm = dataSnapshot5.getValue(BloodPressure2.class);
                                                    t.setText(String.valueOf(cm.getSavg()));
                                                    t.append(" / ");
                                                    t.append(String.valueOf(cm.getDavg()));

                                                    // Toast.makeText(BPactivity.this,  cm.getEmail(), Toast.LENGTH_SHORT).show();


                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(HealthActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }

