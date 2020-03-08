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
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        e=findViewById(R.id.editText9);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void reset(View v){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (e.getText().toString().matches(emailPattern) && e.getText().toString().length() > 0){

            FirebaseAuth.getInstance().sendPasswordResetEmail(e.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "Reset link sent sucessfull to our Email-ID!", Toast.LENGTH_SHORT).show();
                                // Log.d(TAG, "Email sent.");

                            }
                            else{
                                Toast.makeText(ResetPasswordActivity.this, "Enter Email-ID registered with your Account", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
        else
            e.setError("Enter a valid Email-ID");
    }
}
