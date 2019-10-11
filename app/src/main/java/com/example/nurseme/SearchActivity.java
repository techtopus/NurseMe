package com.example.nurseme;

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

public class SearchActivity extends AppCompatActivity {
EditText location,district;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        location=findViewById(R.id.editText2);
        district=findViewById(R.id.editText);
    }
    public void locationsearch(View v)
    {
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("NursePersonalInfo").orderByChild("locality").equalTo(location.getText().toString());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        NursePersonalInfo n=dataSnapshot1.getValue(NursePersonalInfo.class);
                        Toast.makeText(SearchActivity.this, "1."+n.getName(), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(SearchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void districtsearch(View v){
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("NursePersonalInfo").orderByChild("district").equalTo(district.getText().toString());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        NursePersonalInfo n=dataSnapshot1.getValue(NursePersonalInfo.class);
                        Toast.makeText(SearchActivity.this, "2."+n.getName(), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(SearchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
