package com.example.nurseme;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EmergencyContactNurse extends AppCompatActivity {
    EditText hospno;
    ImageView btn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact_nurse);
        mAuth=FirebaseAuth.getInstance();
        hospno=findViewById(R.id.hospno_txtbox);
        btn=findViewById(R.id.ok_btn);

    }
    public void nurse(View v)
    {
        Toast.makeText(this, "Calling Relative......", Toast.LENGTH_SHORT).show();
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("contract").orderByChild("nurseemail").equalTo(mAuth.getCurrentUser().getEmail());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ( dataSnapshot.exists()) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ContractClass t = dataSnapshot1.getValue(ContractClass.class);

                        if(t.getStatus().equals("working")){
                            //Toast.makeText(EmergenyContact.this, "tata", Toast.LENGTH_SHORT).show();
                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                            Query query2 = reference2.child("Relatives").orderByChild("emailid").equalTo(t.getPatientemail());
                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if ( dataSnapshot.exists()) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            Relative t = dataSnapshot1.getValue(Relative.class);
                                            Intent intent = new Intent(Intent.ACTION_DIAL);
                                            intent.setData(Uri.parse("tel:" + t.getContactno()));
                                            startActivity(intent);

                                        }
                                    }}
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(EmergencyContactNurse.this, "You dont have any Relative connected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void ambulance(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "103"));
        startActivity(intent);
    }
    public void hospital(View v){
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("hospital").orderByChild("relativeid").equalTo(mAuth.getCurrentUser().getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ( dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        hospital t = dataSnapshot1.getValue(hospital.class);

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" +t.getNo()));
                        startActivity(intent);
                    }
                }
                else{
                    hospno.setVisibility(View.VISIBLE);
                    btn.setVisibility(View.VISIBLE);


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void change(View v)
    {DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("hospital").orderByChild("relativeid").equalTo(mAuth.getCurrentUser().getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ( dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        hospital t = dataSnapshot1.getValue(hospital.class);

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" +t.getNo()));
                        startActivity(intent);
                    }
                }
                else{
                    hospno.setVisibility(View.VISIBLE);
                    btn.setVisibility(View.VISIBLE);


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        hospno.setVisibility(View.VISIBLE);
        btn.setVisibility(View.VISIBLE);
    }

    public void call(View view)
    {DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("hospital").orderByChild("relativeid").equalTo(mAuth.getCurrentUser().getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ( dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        dataSnapshot1.getRef().removeValue();
                    }
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        String no=hospno.getText().toString();
        if(no.length()==10) {
            hospital h = new hospital(mAuth.getCurrentUser().getUid(), no);
            hospno.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
            DatabaseReference databasereference2= FirebaseDatabase.getInstance().getReference("hospital");
            String id = databasereference2.push().getKey();
            databasereference2.child(mAuth.getCurrentUser().getUid()).setValue(h);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" +no));
            startActivity(intent);

        }else{
            hospno.setError("Enter valid telephone no");
            return;
        }

    }

}
