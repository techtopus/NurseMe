 package com.example.nurseme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

 public class AddPatient extends AppCompatActivity {
     EditText name,age,description;
     RadioGroup rg;
     RadioButton male,female,sex;
     private FirebaseAuth mAuth;
     TextView c1,c2;
     Spinner nursetype,servicetype;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_add_patient);
         mAuth = FirebaseAuth.getInstance();
         Toolbar toolbar=findViewById(R.id.toolbar2);

         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayShowTitleEnabled(false);
         name=findViewById(R.id.name_txtbox);
         age=findViewById(R.id.age_txtbox);
         rg=findViewById(R.id.sex_radiogrp);
         description=findViewById(R.id.description_txtbox);
         male=findViewById(R.id.male_radiobtn);
         c1=findViewById(R.id.caution1_textview);
         c2=findViewById(R.id.caution1_textview2);
         female=findViewById(R.id.female_radiobtn);
         male.setEnabled(true);
            nursetype=findViewById(R.id.nursetype_spinner);
            servicetype=findViewById(R.id.servicetype_spinner);
        // age_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
     }
     public void add(View view)
     {
         String namestr,descriptionstr,gender,nursetypestr,servicetypestr;
     int agenum;
     namestr=name.getText().toString().trim();
     if(age.getText().toString().equals(""))
         agenum=-1;
     else
         agenum= Integer.parseInt(age.getText().toString());
         // get selected radio button from radioGroup
         int selectedId = rg.getCheckedRadioButtonId();
// find the radiobutton by returned id
         sex = (RadioButton) findViewById(selectedId);
        gender= (String) sex.getText();
        nursetypestr= (String) nursetype.getSelectedItem();
        servicetypestr= (String) servicetype.getSelectedItem();
        descriptionstr=description.getText().toString().trim();
         if(namestr.equals(""))
         {
             name.setError("Enter Name!");
             name.requestFocus();
             return;
         }
         if(age.getText().equals("")) {
             age.setError("Enter Age!");

             age.requestFocus();
             return;
         }
         if(nursetypestr.equals("Select Nursing Type"))

         {
             c1.setVisibility(View.VISIBLE);
         //    Toast.makeText(this, "Select Nursing Type", Toast.LENGTH_SHORT).show();
             return;
         }
         else
             c1.setVisibility(View.GONE);

         if(servicetypestr.equals("Select Service Type"))
         {
             c2.setVisibility(View.VISIBLE);
           //  Toast.makeText(this, "Select Service Type", Toast.LENGTH_SHORT).show();
             return;
         }
         else
             c2.setVisibility(View.GONE);
        // Toast.makeText(this, names, Toast.LENGTH_SHORT).show();
         Patient p=new Patient(mAuth.getCurrentUser().getUid(),namestr,agenum,gender,nursetypestr,servicetypestr,descriptionstr,mAuth.getCurrentUser().getEmail());

         DatabaseReference databasereference2= FirebaseDatabase.getInstance().getReference("Patient");
         String id = databasereference2.push().getKey();
                 databasereference2.child(namestr).setValue(p);

                 //create medicine table
         medicineclass x=new medicineclass(mAuth.getCurrentUser().getUid(),"","","","","","");
         DatabaseReference databasereference3= FirebaseDatabase.getInstance().getReference("medicine");
         String id2 = databasereference3.push().getKey();
         databasereference3.child(mAuth.getCurrentUser().getUid()).setValue(x);

         //create health table
         int index=mAuth.getCurrentUser().getEmail().indexOf('@');
         String name=mAuth.getCurrentUser().getEmail().substring(0,index);
         DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Health Data").child("Blood Pressure").child(name);
                 reference.setValue(new BloodPressure2(mAuth.getCurrentUser().getEmail(),0,0));

          String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


                 reference.child(date).setValue(new BloodPressure(0,0,"",""));
        //create sugar table
         DatabaseReference referenced=FirebaseDatabase.getInstance().getReference("Health Data").child("Sugar").child(name);
         referenced.setValue(new sugasrclass2(0,0,mAuth.getCurrentUser().getEmail()));
         referenced.child(date).setValue(new sugarclass(0,0,"",""));


         //create hospital class
         int indexd=mAuth.getCurrentUser().getEmail().indexOf('@');
         String named=mAuth.getCurrentUser().getEmail().substring(0,indexd);
         DatabaseReference ref=FirebaseDatabase.getInstance().getReference("hospital");
         ref.child(named).setValue(new hospital(mAuth.getCurrentUser().getUid(),""));

         //Toast.makeText(this, "Updated successfully!!", Toast.LENGTH_SHORT).show();
         startActivity(new Intent(this,RelativeDashboard.class));

     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         MenuInflater inflator=getMenuInflater();
         inflator.inflate(R.menu.menu ,menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId())
         {
             case R.id.logout:
                 FirebaseAuth.getInstance().signOut();
                 finish();
                 startActivity(new Intent(this, LoginActivity.class));
                 break;

         }
         return true;
     }

     @Override
     public void onBackPressed() {
         super.onBackPressed();
         Intent homeIntent = new Intent(Intent.ACTION_MAIN);
         homeIntent.addCategory( Intent.CATEGORY_HOME );
         homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(homeIntent);
     }
 }

