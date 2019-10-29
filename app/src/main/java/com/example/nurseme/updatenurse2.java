package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class updatenurse2 extends AppCompatActivity {
EditText name,age,locality,no;
Spinner s;
CheckBox c1,c2,c3;
FirebaseAuth mAuth;
    String  namevar,agevar,localityvar,districtvar,novar,gender;
int p1,p2,p3;
RadioButton male,female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatenurse2);
        mAuth=FirebaseAuth.getInstance();
        String e=getIntent().getStringExtra("email");
        name=findViewById(R.id.name_txtbox);
        age=findViewById(R.id.age_txtbox);
        locality=findViewById(R.id.locality_txtbox);
        no=findViewById(R.id.contactno_txtbox);
        s=findViewById(R.id.spinner);
        c1=findViewById(R.id.daycare_box);
        c2=findViewById(R.id.nightcare_box);
        c3=findViewById(R.id.stayathome_box);
        male=findViewById(R.id.male_radiobtn);
        female=findViewById(R.id.female_radiobtn);
        String[] districts=getResources().getStringArray(R.array.districts);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,districts);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        initialise(e);
    }
    public void update (View v)
    {
        if(name.getText().toString().length()==0){
            name.setError("Enter Name");
            return;
        }
        if(age.getText().toString().length()==0 || age.getText().toString().length()>2){
            age.setError("Enter valid Age");
            return;
        }
        if(locality.getText().toString().length()==0){
            locality.setError("Enter Locality");
            return;
        }
        if(no.getText().toString().length()<10 || no.getText().length()>10){
            no.setError("Enter valid Phone No");
            return;
        }

        namevar=name.getText().toString();
        agevar=age.getText().toString();
        novar=no.getText().toString();
        localityvar=locality.getText().toString();
        districtvar=s.getSelectedItem().toString();
        if(districtvar.equals("District")){
            Toast.makeText(this, "Select district", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!c1.isChecked()&& !c2.isChecked()&& !c3.isChecked())
        {
            Toast.makeText(this, "Please specify a suitable timing", Toast.LENGTH_SHORT).show();
            return;
        }
        if(c1.isChecked())
            p1=1;
        if(c2.isChecked())
            p2=1;
        if(c3.isChecked())
            p3=1;
        if(male.isChecked())
            gender="Male";
        else
            gender="Female";


       /* final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("NursePersonalInfo").orderByChild("email").equalTo(getIntent().getStringExtra("email"));
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                  @Override
                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                      if ( dataSnapshot.exists()) {
                                                          for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                dataSnapshot1.getRef().removeValue();
                                                          }
                                                      }
                                                  }

                                                  @Override
                                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                                  }
                                              });*/



        final DatabaseReference databasereference3 = FirebaseDatabase.getInstance().getReference("NursePersonalInfo");
        NursePersonalInfo u = new NursePersonalInfo(mAuth.getCurrentUser().getUid()
                ,namevar,agevar,novar,localityvar,districtvar,gender,getIntent().getStringExtra("email"),p1,p2,p3);

        databasereference3.child(namevar).setValue(u);




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
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        NursePersonalInfo n=dataSnapshot1.getValue(NursePersonalInfo.class);
                        name.setText(n.getName());
                        age.setText(n.getAge());
                        locality.setText(n.getLocality());
                        no.setText(n.getPhoneno());
                        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                .setDisplayName(n.getName())
                                .build();


                    }
                }
                else{
                    Toast.makeText(updatenurse2.this, "Please enter correct Email. ID", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(updatenurse2.this,updatenurse.class));
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
