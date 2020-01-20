package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class deletenurse extends AppCompatActivity {
    EditText e;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletenurse);
        e=findViewById(R.id.editText3);
        mAuth=FirebaseAuth.getInstance();
    }
    public void search(View v)
    {if(!e.getText().toString().contains("nurse"))
    {
        Toast.makeText(this, "Enter a valid nurse ID", Toast.LENGTH_SHORT).show();
        return ;
    }else{
         DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("NursePersonalInfo").orderByChild("email").equalTo(e.getText().toString().trim());
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
                                              });
    }

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(e.getText().toString().trim(), "123456");
        mAuth.signInWithEmailAndPassword(e.getText().toString().trim(), "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                           // startActivity(new Intent(deletenurse.this, AdminDashboard.class));
                        } else {

                            Toast.makeText(deletenurse.this, "Some Error occured! Please try again!!", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
final FirebaseUser user=mAuth.getCurrentUser();
        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //Log.d(TAG, "User account deleted.");
                                            Toast.makeText(deletenurse.this, "Deleted successfull", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                });

        mAuth.signInWithEmailAndPassword("bba@admin.com", "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(deletenurse.this, AdminDashboard.class));
                        } else {

                            Toast.makeText(deletenurse.this, "Some Error occured! Please try again!!", Toast.LENGTH_SHORT).show();
                        }

                    }

                });

       Toast.makeText(this, "Nurse profile deleted successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,AdminDashboard.class));
    }
}
