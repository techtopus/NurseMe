package com.example.nurseme;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PendingRequestActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<PendingRequestsRecyclerClass> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request);
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();


        checkPending();
    }

    public void checkPending() {
        //listItems.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
       Toast.makeText(PendingRequestActivity.this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

        Query query = reference.child("Request").orderByChild("nurseemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            RequestClass r = dataSnapshot1.getValue(RequestClass.class);

                            if (r.getStatus().toString().equals("-1")) {
                                Toast.makeText(PendingRequestActivity.this, "hii", Toast.LENGTH_SHORT).show();

                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                                Query query = reference2.child("Patient").orderByChild("email").equalTo(r.getPatientemail());
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            try {
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    Patient patient = dataSnapshot1.getValue(Patient.class);
                                                    PendingRequestsRecyclerClass p = new
                                                            PendingRequestsRecyclerClass(patient.getName(), patient.getNursingtype(), patient.getEmail());

                                                    listItems.add(p);
                                                    Toast.makeText(PendingRequestActivity.this, p.getUsername(), Toast.LENGTH_SHORT).show();
                                                }
                                                adapter=new PendingRequestsAdapterClass(listItems,getApplicationContext());
                                                recyclerView.setAdapter(adapter);
                                            } catch (Exception e) {
                                                Toast.makeText(PendingRequestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                //Toast.makeText(PendingRequestActivity.this, r.getPatientemail(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(PendingRequestActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                 //   Toast.makeText(PendingRequestActivity.this, "working", Toast.LENGTH_SHORT).show();
                }}


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}

