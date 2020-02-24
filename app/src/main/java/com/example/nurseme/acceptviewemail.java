package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class acceptviewemail extends AppCompatActivity {
EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptviewemail);
        e=findViewById(R.id.editText3);
    }
    public void search(View v)
    {if(!e.getText().toString().contains("nurse"))
    {
        Toast.makeText(this, "Enter a valid nurse ID", Toast.LENGTH_SHORT).show();
        return ;
    }else{
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("NursePersonalInfo").orderByChild("email").equalTo(e.getText().toString());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            NursePersonalInfo npi = dataSnapshot1.getValue(NursePersonalInfo.class);
                            Intent i = new Intent(acceptviewemail.this, ViewNurse.class);
                            i.putExtra("email", e.getText().toString().trim());
                            startActivity(i);
                        }
                    } catch (Exception e) {
                    }
                }
            else {
                    Toast.makeText(acceptviewemail.this, "Nurse Id doesnt exists", Toast.LENGTH_SHORT).show();
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }}}


