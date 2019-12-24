package com.example.nurseme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class add_nurse extends AppCompatActivity {
    private static final int CHOOSE_IMAAGE = 0;
    private Uri uriprofileimg;
    Spinner sp;
    String profileImgUrl;
    EditText nametxt,agetxt,phonenotxt,localitytxt,districttxt;
    ImageView img;
RadioButton male,female;
    FirebaseAuth mAuth;
    CheckBox c1,c2,c3;
    int p1=0,p2=0,p3=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nurse);
        mAuth=FirebaseAuth.getInstance();
        img=findViewById(R.id.ic_paymentactive);
        sp=findViewById(R.id.spinner);
        c1=findViewById(R.id.daycare_box);
        c2=findViewById(R.id.nightcare_box);
        c3=findViewById(R.id.stayathome_box);
        nametxt=findViewById(R.id.name_txtbox);
        agetxt=findViewById(R.id.age_txtbox);
        //districttxt=findViewById(R.id.district_txtbox);
        phonenotxt=findViewById(R.id.contactno_txtbox);
        localitytxt=findViewById(R.id.locality_txtbox);
        male=findViewById(R.id.male_radiobtn);
        female=findViewById(R.id.female_radiobtn);
        String[] districts=getResources().getStringArray(R.array.districts);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,districts);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
    }
    public void update(View v) {
        try{
        if(nametxt.getText().toString().length()==0){
            nametxt.setError("Enter Name");
            return;
        }
        if(agetxt.getText().toString().length()==0 || agetxt.getText().toString().length()>2){
            agetxt.setError("Enter valid Age");
            return;
        }
        if(localitytxt.getText().toString().length()==0){
            localitytxt.setError("Enter Locality");
            return;
        }
        if(phonenotxt.getText().toString().length()<10 || phonenotxt.getText().length()>10){
            phonenotxt.setError("Enter valid Phone No");
            return;
        }


          final String name,age,phoneno,locality,gender,district;
        name=nametxt.getText().toString();
        age=agetxt.getText().toString();
        phoneno=phonenotxt.getText().toString();
        locality=localitytxt.getText().toString();
        district=sp.getSelectedItem().toString();
        if(district.equals("District")){
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

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null ) {



            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        DatabaseReference databasereference2 = FirebaseDatabase.getInstance().getReference("NursePersonalInfo");
                        String id = databasereference2.push().getKey();


                        NursePersonalInfo u = new NursePersonalInfo(mAuth.getCurrentUser().getUid(),name,age,phoneno,locality.toLowerCase(),district,gender,
                                mAuth.getCurrentUser().getEmail(),"nil",p1,p2,p3);

                        databasereference2.child(name).setValue(u);


                        Toast.makeText(add_nurse.this, "Your Profile updated successfully!!", Toast.LENGTH_SHORT).show();
                        } else {
                        Toast.makeText(add_nurse.this, "Oops!Some error occured!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            FirebaseAuth.getInstance().signOut();
            finish();

            mAuth.signInWithEmailAndPassword("bba@admin.com", "123456")
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                startActivity(new Intent(add_nurse.this, AdminDashboard.class));
                            } else {

                                Toast.makeText(add_nurse.this, "Some Error occured! Please try again!!", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
        }     }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }}



}
