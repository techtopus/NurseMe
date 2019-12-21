package com.example.nurseme;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {
EditText e;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        e=findViewById(R.id.editText8);
        mAuth=FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("contract").orderByChild("patientemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            ContractClass c = dataSnapshot1.getValue(ContractClass.class);
                            if(c.getStatus().equals("working"))
                            {
                                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                                SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
                              Date d1=s.parse(date);
                                String date2=c.getStartdate();
                                Date d2= s.parse(date2);
                                e.setText(" ₹ . ");
                                e.append(String.valueOf( (d1.getTime() - d2.getTime())/(24*60*60*1000)*500));
                            }
                        }
                    }catch(Exception e){
                        Toast.makeText(PaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    



}
