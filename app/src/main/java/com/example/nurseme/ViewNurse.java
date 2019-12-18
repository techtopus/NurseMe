package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewNurse extends AppCompatActivity {
FirebaseAuth mAuth;
EditText name,age,locality,no;
RadioButton m,f;
CheckBox c1,c2,c3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nurse);
        mAuth= FirebaseAuth.getInstance();
        name=findViewById(R.id.name_txtbox);
        age=findViewById(R.id.age_txtbox);
        locality=findViewById(R.id.locality_txtbox);
        no=findViewById(R.id.contactno_txtbox);
        m=findViewById(R.id.male_radiobtn);
        f=findViewById(R.id.female_radiobtn);
        c1=findViewById(R.id.daycare_box);
        c2=findViewById(R.id.nightcare_box);
        c3=findViewById(R.id.stayathome_box);
        String e=getIntent().getStringExtra("email");
        initialise(e);
    }
    public void initialise(String email)
    {

        Toast.makeText(this, "hiii", Toast.LENGTH_SHORT).show();
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("NursePersonalInfo").orderByChild("email").equalTo(email);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ( dataSnapshot.exists()) {
                    String gender ;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        NursePersonalInfo n=dataSnapshot1.getValue(NursePersonalInfo.class);
                        name.setText(n.getName());
                        age.setText(n.getAge());
                        locality.setText(n.getLocality());
                        no.setText(n.getPhoneno());
                        gender =n.getGender().toString();
                        if(gender.equals("Female")){
                            f.setEnabled(true);
                        }
                        else
                            m.setEnabled(true);
                        if(n.getDaycare()==1){
                            c1.setChecked(true);
                        }
                        if(n.getNightcare()==1)
                        {
                            c2.setChecked(true);

                        }
                        if(n.getStryathome()==1)
                        {
                            c3.setChecked(true);
                        }

                    }
                }
                else{
                    Toast.makeText(ViewNurse.this, "Please enter correct Email. ID", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ViewNurse.this,updatenurse.class));
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
public void back(View v)
{
    startActivity(new Intent(this,AdminDashboard.class));
}
}
