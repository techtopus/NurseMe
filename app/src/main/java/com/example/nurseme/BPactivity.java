package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class BPactivity extends AppCompatActivity {
    EditText today,today2;
    TextView yesturday,avgtext;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpactivity);
        today = findViewById(R.id.bptoday);
        today2=findViewById(R.id.bptoday2);
        yesturday = findViewById(R.id.yesturday);
        avgtext = findViewById(R.id.avg);
        mAuth=FirebaseAuth.getInstance();
        try {
            loadvalues();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public void loadvalues() throws ParseException {
        final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        SimpleDateFormat s= new SimpleDateFormat("dd-MM-yyyy");
        Date date2=s.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date2);
        calendar.add(Calendar.DATE, -1);
        final String ydate= s.format(calendar.getTime());




                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("contract").orderByChild("nurseemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            final ContractClass c = dataSnapshot1.getValue(ContractClass.class);
                            if (c.getStatus().equals("working")) {
                                int index = c.getPatientemail().indexOf('@');
                                final String name = c.getPatientemail().substring(0, index);
                                Toast.makeText(BPactivity.this, ydate, Toast.LENGTH_SHORT).show();
                                DatabaseReference referenced = FirebaseDatabase.getInstance().
                                        getReference("Health Data").child("Blood Pressure");
                                Query queryd = referenced.child(name).orderByChild("date").equalTo(ydate);
                                queryd.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            try {
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    BloodPressure bpo = dataSnapshot1.getValue(BloodPressure.class);
                                                    yesturday.setText(bpo.getSystolic() + " / " + bpo.getDiastolic() + " Mm Hg");
                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(BPactivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else
                                        {
                                            yesturday.setText("Come Back Tommorrow");
                                        }

                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });





                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Health Data").child("Blood Pressure");
                                Query query2 = reference2.orderByChild("email").equalTo(c.getPatientemail());
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            try {
                                                for (DataSnapshot dataSnapshot5 : dataSnapshot.getChildren())
                                                {
                                                    BloodPressure2 cm = dataSnapshot5.getValue(BloodPressure2.class);
                                                    avgtext.setText(String.valueOf(cm.getSavg()));
                                                    avgtext.append(" / ");
avgtext.append(String.valueOf(cm.getDavg()));
                                                    avgtext.append(" MmHg");
                                                   // Toast.makeText(BPactivity.this,  cm.getEmail(), Toast.LENGTH_SHORT).show();



                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(BPactivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }}

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    }catch(Exception e){
                        Toast.makeText(BPactivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    public void check(View view){
        if(today.getText().equals("")){
            today.setError("Please enter a valid input");
            return;
        }
        if(today2.getText().equals("")){
            today2.setError("Please enter a valid input");
            return;
        }
        int todays=Integer.parseInt( today.getText().toString());
        int todays2=Integer.parseInt ( today2.getText().toString());
       condition(todays,todays2);

    }
    public String condition(int todays,int todays2){
        if(todays<=90||todays2<=60) {
            Toast.makeText(this, "Low Bp", Toast.LENGTH_SHORT).show();
            return "Low";
        }
        if(todays<=120||todays2<=80) {
            Toast.makeText(this, "Normal", Toast.LENGTH_SHORT).show();
            return "Normal";
        }
        if(todays>120||todays2>80) {
            Toast.makeText(this, "High BP", Toast.LENGTH_SHORT).show();
            return "High";
        }
        return "nil";
    }


    public void upload(View v){
        if(today.getText().equals("")){
            today.setError("Please enter a valid input");
            return;
        }
        if(today2.getText().equals("")){
            today2.setError("Please enter a valid input");
            return;
        }
       final int todays=Integer.parseInt( today.getText().toString());
         final int todays2=Integer.parseInt( today2.getText().toString());
        final DatabaseReference databasereference2 = FirebaseDatabase.getInstance().getReference("Health Data").child("Blood Pressure");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("contract").orderByChild("nurseemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                     if (dataSnapshot.exists()) {
                                                         try {
                                                             //Toast.makeText(BPactivity.this, "hello", Toast.LENGTH_SHORT).show();
                                                             for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                 final ContractClass c = dataSnapshot1.getValue(ContractClass.class);
                                                                 if(c.getStatus().equals("working"))
                                                                 {

                                                                     final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                                                                     final BloodPressure b=new BloodPressure( todays,  todays2,condition(todays,todays2),date);
                                                                     //
                                                                     // final BloodPressure b=new BloodPressure(todays,todays2,"hio");
                                                                     int index=c.getPatientemail().indexOf('@');
                                                                     final String name=c.getPatientemail().substring(0,index);



                                                                     DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Health Data").child("Blood Pressure");
                                                                     Query query2 = reference2.orderByChild("email").equalTo(c.getPatientemail());
                                                                     query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                              @Override
                                                                                                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                  if (dataSnapshot.exists()) {
                                                                                                                      try {
                                                                                                                          for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                                                                              BloodPressure2 cd = dataSnapshot1.getValue(BloodPressure2.class);
                                                                                                                             // Toast.makeText(BPactivity.this, "hi", Toast.LENGTH_SHORT).show();
                                                                                                                            if(cd.getSavg()==0)
                                                                                                                                databasereference2.child(name).child("savg").setValue( todays);
                                                                                                                            else
                                                                                                                            databasereference2.child(name).child("savg").setValue((cd.getSavg() + todays) / 2);
                                                                                                                            if(cd.getDavg()==0)
                                                                                                                                databasereference2.child(name).child("davg").setValue(todays2);
                                                                                                                            else
                                                                                                                                databasereference2.child(name).child("davg").setValue((cd.getDavg() + todays2) / 2);

                                                                                                                              databasereference2.child(name).child(date).setValue(b);
                                                                                                                              Toast.makeText(BPactivity.this, "successfully uploaded", Toast.LENGTH_SHORT).show();
                                                                                                                              loadvalues();
                                                                                                                          }
                                                                                                                      } catch (Exception e) {
                                                                                                                          Toast.makeText(BPactivity.this, "+"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                      }
                                                                                                                  }

                                                                                                              }

                                                                                                              @Override
                                                                                                              public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                              }
                                                                                                          });




                                                                 }
                                                             }
                                                         } catch (Exception e) {
                                                             Toast.makeText(BPactivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             });
        startActivity(new Intent(this,HealthNurse.class));



    }
}
