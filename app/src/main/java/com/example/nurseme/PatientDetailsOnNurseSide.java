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

public class PatientDetailsOnNurseSide extends AppCompatActivity {
TextView name,age,gender,service,nursing,description;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_on_nurse_side);
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        service=findViewById(R.id.service);
        nursing=findViewById(R.id.nursing);
        description=findViewById(R.id.description);
        mAuth=FirebaseAuth.getInstance();
        gender.setText("tatat");
        iitialise();
    }
    public void iitialise()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("contract").orderByChild("nurseemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            ContractClass n = dataSnapshot1.getValue(ContractClass.class);

                            Toast.makeText(PatientDetailsOnNurseSide.this, "hi", Toast.LENGTH_SHORT).show();
                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                            Query query2 = reference2.child("Patient").orderByChild("email").equalTo(n.getPatientemail());
                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        try {
                                            for (DataSnapshot dataSnapshot12 : dataSnapshot.getChildren()) {
                                                Patient npi = dataSnapshot12.getValue(Patient.class);
                                                name.setText(npi.getName());
                                                age.setText(String.valueOf( npi.getAge()));
                                                Toast.makeText(PatientDetailsOnNurseSide.this, "hello", Toast.LENGTH_SHORT).show();
                                                gender.setText(npi.getGender());
                                                service.setText(npi.getServicestype());
                                                nursing.setText(npi.getNursingtype());
                                                description.setText(npi.getDesc());
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(PatientDetailsOnNurseSide.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    } catch (Exception e) {
                        Toast.makeText(PatientDetailsOnNurseSide.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
