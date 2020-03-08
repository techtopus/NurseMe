package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NurseDetailsOnPatientSide extends AppCompatActivity {
    TextView name, gender, locality, age, district;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_details_on_patient_side);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        locality = findViewById(R.id.locality);
        age = findViewById(R.id.age);
        district = findViewById(R.id.district);
        mAuth=FirebaseAuth.getInstance();
        iitialise();
    }

    public void iitialise()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("contract").orderByChild("patientemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            ContractClass n = dataSnapshot1.getValue(ContractClass.class);

                            Toast.makeText(NurseDetailsOnPatientSide.this, "hi", Toast.LENGTH_SHORT).show();
                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                            Query query2 = reference2.child("NursePersonalInfo").orderByChild("email").equalTo(n.getNurseemail());
                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        try {
                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                NursePersonalInfo npi = dataSnapshot1.getValue(NursePersonalInfo.class);
                                                name.setText(npi.getName());
                                                age.setText(npi.getAge());
                                                Toast.makeText(NurseDetailsOnPatientSide.this, "hello", Toast.LENGTH_SHORT).show();
                                                gender.setText(npi.getGender());
                                                locality.setText(npi.getLocality());
                                                district.setText(npi.getDistrict());
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
                    } catch (Exception e) {
                        Toast.makeText(NurseDetailsOnPatientSide.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void terminate(View v)
    {
        startActivity(new Intent(this,ReasonActivity.class));
    }
}
