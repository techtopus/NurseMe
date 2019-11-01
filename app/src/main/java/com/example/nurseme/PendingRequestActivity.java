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

public class PendingRequestActivity extends AppCompatActivity {
TextView name;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request);
        mAuth=FirebaseAuth.getInstance();
        name=findViewById(R.id.name);
         DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Request").orderByChild("nurseemail").equalTo(mAuth.getCurrentUser().getEmail().trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            RequestClass r = dataSnapshot1.getValue(RequestClass.class);
                            if(Integer.valueOf(r.getStatus().toString())==-1) {
                                name.setText(r.getPatientemail());
                                Toast.makeText(PendingRequestActivity.this, r.getPatientemail(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(PendingRequestActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();                    }
                }
                        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
