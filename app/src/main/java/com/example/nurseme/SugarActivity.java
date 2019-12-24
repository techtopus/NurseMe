package com.example.nurseme;

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

public class SugarActivity extends AppCompatActivity {
EditText before,after;
FirebaseAuth mAuth;
TextView yesturday,avg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);
        before=findViewById(R.id.before);
        mAuth=FirebaseAuth.getInstance();
        after=findViewById(R.id.after);
        yesturday=findViewById(R.id.yesturday2);
        avg=findViewById(R.id.avg2);
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
                                // Toast.makeText(BPactivity.this, ydate, Toast.LENGTH_SHORT).show();
                                DatabaseReference referenced = FirebaseDatabase.getInstance().
                                        getReference("Health Data").child("Sugar");
                                Query queryd = referenced.child(name).orderByChild("date").equalTo(ydate);
                                queryd.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            try {
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    sugarclass bpo = dataSnapshot1.getValue(sugarclass.class);
                                                    yesturday.setText(bpo.getBefore() + " / " + bpo.getAfter() + " Mm Hg");
                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(SugarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            yesturday.setText("Come Back Tommorrow");
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Health Data").child("Sugar");
                                Query query2 = reference2.orderByChild("email").equalTo(c.getPatientemail());
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            try {
                                                for (DataSnapshot dataSnapshot5 : dataSnapshot.getChildren())
                                                {
                                                    sugasrclass2 cm = dataSnapshot5.getValue(sugasrclass2.class);
                                                    avg.setText(String.valueOf(cm.getBeforeavg()));
                                                    avg.append(" / ");
                                                    avg.append(String.valueOf(cm.getAfteravg()));
                                                    avg.append(" mg/dl");
                                                    // Toast.makeText(BPactivity.this,  cm.getEmail(), Toast.LENGTH_SHORT).show();



                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(SugarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }}

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    }catch(Exception e){
                        Toast.makeText(SugarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    public void before(View v){

        if(before.getText().equals(""))
        {
            before.setError("Please enter a valid input");
        return;
        }

        if(after.getText().equals(""))
        {
            after.setError("Please enter a valid input");
            return;
        }
        final int afters= Integer.parseInt(after.getText().toString());
        final int befores= Integer.parseInt(before.getText().toString());
        String Condition=condition(befores,afters);
         final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
         final sugarclass sc=new sugarclass(befores,afters,Condition,date);

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
                            if (c.getStatus().equals("working")) {
                                int index=c.getPatientemail().indexOf('@');
                                final String name=c.getPatientemail().substring(0,index);
                                final DatabaseReference databasereference2 = FirebaseDatabase.getInstance()
                                        .getReference("Health Data").child("Sugar");

                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Health Data").child("Sugar");
                                Query query2 = reference2.orderByChild("email").equalTo(c.getPatientemail());
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            try {
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    sugasrclass2 cd = dataSnapshot1.getValue(sugasrclass2.class);
                                                    databasereference2.child(name).child(date).setValue(sc);
                                                    if(cd.getBeforeavg()==0)
                                                        databasereference2.child(name).child("beforeavg").setValue( befores);
                                                    else
                                                    databasereference2.child(name).child("beforeavg").setValue((cd.getBeforeavg() + befores) / 2);
                                                    if(cd.getAfteravg()==0)
                                                        databasereference2.child(name).child("afteravg").setValue(afters);
                                                    else
                                                    databasereference2.child(name).child("afteravg").setValue((cd.getAfteravg() + afters) / 2);
                                                    Toast.makeText(SugarActivity.this, "successfully uploaded", Toast.LENGTH_SHORT).show();
                                                    loadvalues();
                                                }
                                            }catch(Exception e)
                                            {
                                                Toast.makeText(SugarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SugarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public String condition(int before,int after)
    {
        if(before<100 &&after<140)
        {
            Toast.makeText(this, "Normal", Toast.LENGTH_SHORT).show();
            return "Normal";
        }
        else
            Toast.makeText(this, "High", Toast.LENGTH_SHORT).show();
        return "High";
    }

}
