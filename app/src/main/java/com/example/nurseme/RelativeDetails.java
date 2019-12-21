package com.example.nurseme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class  RelativeDetails extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText name,contactno,contactno2;
    String namestr,nostr,no2str;
    ProgressBar pg;
    Button uploadbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_details);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar=findViewById(R.id.toolbar3);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pg=findViewById(R.id.progressBar4);
        uploadbtn=findViewById(R.id.update_btn);
        contactno=findViewById(R.id.contactno_txtbox);
        contactno2=findViewById(R.id.contactno2_txtbox);
        name=findViewById(R.id.district_txtbox);
        }

        public void update(View v)
        {
            pg.setVisibility(View.VISIBLE);
            uploadbtn.setVisibility(View.GONE);
            namestr=name.getText().toString().trim();
            nostr=contactno.getText().toString().trim();
            no2str=contactno2.getText().toString().trim();

            if(namestr.equals(""))
        {pg.setVisibility(View.GONE);
            uploadbtn.setVisibility(View.VISIBLE);
            name.setError("Enter Name!");
            name.requestFocus();
            return;
        }


            if( nostr.length()!=10)
            {pg.setVisibility(View.GONE);
                uploadbtn.setVisibility(View.VISIBLE);
                contactno.setError("Enter valid mobile number!");
                contactno.requestFocus();
                return;
            }
            if( no2str.length()!=10)
            {pg.setVisibility(View.GONE);
                uploadbtn.setVisibility(View.VISIBLE);
                contactno2.setError("Enter valid mobile number!");
                contactno2.requestFocus();
                return;
            }

            String custid=mAuth.getCurrentUser().getUid();
            DatabaseReference databasereference2= FirebaseDatabase.getInstance().getReference("Relatives");
            String id = databasereference2.push().getKey();
            Relative r=new Relative(namestr,nostr,no2str,mAuth.getCurrentUser().getEmail(),mAuth.getCurrentUser().getUid());
            pg.setVisibility(View.GONE);
            databasereference2.child(namestr).setValue(r);
            Toast.makeText(this, "Updated successfully!!", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(this,RelativeDashboard.class);
                        i.putExtra("name",namestr);
                        startActivity(i);
        }

    @Override
    protected void onStart() {
        super.onStart();
        uploadbtn.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        if(mAuth.getCurrentUser()==null)
        { finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
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
}

