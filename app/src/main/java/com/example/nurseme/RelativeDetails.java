package com.example.nurseme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RelativeDetails extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText name,contactno,contactno2;
    String namestr,nostr,no2str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_details);
        mAuth = FirebaseAuth.getInstance();
        contactno=findViewById(R.id.contactno_txtbox);
        contactno2=findViewById(R.id.contactno2_txtbox);
        name=findViewById(R.id.name_txtbox);
        }

        public void update(View v)
        {
            namestr=name.getText().toString().trim();
            nostr=contactno.getText().toString().trim();
            no2str=contactno2.getText().toString().trim();

            if(namestr.equals(""))
        {
            name.setError("Enter Name!");
            name.requestFocus();
            return;
        }


            if( nostr.length()!=10)
            {
                contactno.setError("Enter valid mobile number!");
                contactno.requestFocus();
                return;
            }
            if( no2str.length()!=10)
            {
                contactno2.setError("Enter valid mobile number!");
                contactno2.requestFocus();
                return;
            }

            String custid=mAuth.getCurrentUser().getUid();
            DatabaseReference databasereference2= FirebaseDatabase.getInstance().getReference("users");
            String id = databasereference2.push().getKey();
            Relative r=new Relative(namestr,nostr,no2str,mAuth.getCurrentUser().getEmail());
            databasereference2.child(namestr).setValue(r);
            Toast.makeText(this, "Updated successfully!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,RelativeProfile.class));

        }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null)
        { finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
