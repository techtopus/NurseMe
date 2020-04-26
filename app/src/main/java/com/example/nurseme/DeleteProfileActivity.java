package com.example.nurseme;

import android.content.Intent;
import android.os.Handler;
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
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeleteProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_profile);
        mAuth = FirebaseAuth.getInstance();
        e = findViewById(R.id.editText10);
    }

    public void delete(View v) {
        if (mAuth.getCurrentUser().getEmail().contains("nurse")) {
            nursedelete(mAuth.getCurrentUser().getEmail());
        } else
            userdelete(mAuth.getCurrentUser().getEmail());
    }


    public void nursedelete(String email){
        int t= accountdeletion(email);
        if(t==0) {
            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
            Query query2 = reference2.child("NursePersonalInfo").orderByChild("email").equalTo(email);
            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            dataSnapshot1.getRef().removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    //start your activity here
                    startActivity(new Intent(DeleteProfileActivity.this, LoginActivity.class));
                }

            }, 3000L);

        }

    }
    public void userdelete(String email){
        int t= accountdeletion(email);
        if(t==0) {
            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
            Query query2 = reference2.child("Relatives").orderByChild("emailid").equalTo(email);
            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            dataSnapshot1.getRef().removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    //start your activity here
                    startActivity(new Intent(DeleteProfileActivity.this, LoginActivity.class));
                }

            }, 3000L);
            startActivity(new Intent(DeleteProfileActivity.this, LoginActivity.class));
        }
    }
    public int accountdeletion(String email)
    {
        final int[] temp = new int[1];
        //account deletion
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, e.getText().toString());

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
//                                            Log.d(TAG, "User account deleted.");

                                            Toast.makeText(DeleteProfileActivity.this, "Account successfullyy deleted!!", Toast.LENGTH_SHORT).show();
                                            temp[0] = 0;
                                             }
                                        else
                                        {
                                            e.setError("Enter our correct password");
                                            temp[0] = 1;
                                        }
                                    }
                                });

                    }
                });
        return temp[0];
    }
}
