package com.example.nurseme;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NurseRequestActivity extends AppCompatActivity {
String email;
TextView name,age,gender,locality,district,sah,nc,dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_request);
        email=getIntent().getStringExtra("nurseEmail");
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        locality=findViewById(R.id.locality);
        district=findViewById(R.id.district);
        sah=findViewById(R.id.stayathome);
        nc=findViewById(R.id.nightcare);
        dc=findViewById(R.id.daycare);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("NursePersonalInfo").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    NursePersonalInfo n = dataSnapshot1.getValue(NursePersonalInfo.class);
                name.setText(n.getName());
                age.setText(n.getAge());
                gender.setText(n.getGender());
                locality.setText(n.getLocality());
                district.setText(n.getDistrict());
                if(n.getStryathome()==1)
                {
                    sah.setVisibility(View.VISIBLE);
                }
                    if(n.getNightcare()==1)
                    {
                        nc.setVisibility(View.VISIBLE);
                    }
                    if(n.getDaycare()==1)
                    {
                        dc.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void requestclick(View v)
    {

    }
    public void gobackclick(View v)
    {

    }
}
