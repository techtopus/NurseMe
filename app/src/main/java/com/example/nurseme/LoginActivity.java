package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText username,password;
    Button loginbtn;
    TextView invalidtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.username_txtbox);
        password=findViewById(R.id.password_txtbox);
        loginbtn=findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();
        invalidtxt=findViewById(R.id.invalid_txtview);
        }

        public void forgotpassword(View v)
        {String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String usernamestr= String.valueOf(username.getText()).trim();
            if (usernamestr.matches(emailPattern) && usernamestr.length() > 0) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(usernamestr)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Log.d(TAG, "Email sent.");
                                Toast.makeText(LoginActivity.this, "A reset passwordlink is sent to the entered Email_ID", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
            else
            {
                Toast.makeText(this, "Enter a valid Email_ID", Toast.LENGTH_SHORT).show();
            }
        }

    public void login(View view)
    {
        final String usernamestr,passwordstr;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        passwordstr=password.getText().toString().trim();
        usernamestr= String.valueOf(username.getText()).trim();
        if (usernamestr.matches(emailPattern) && usernamestr.length() > 0){

            if(passwordstr.length()>2)
            {
                mAuth.signInWithEmailAndPassword(usernamestr, passwordstr)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();

                                } else {
                                    if(task.getException() instanceof FirebaseAuthInvalidUserException)
                                    {                    Toast.makeText(LoginActivity.this, "Oops! You are not a registered user...\n you will be redirected to sign up page in 5 seconds!!", Toast.LENGTH_SHORT).show();
                                        new Timer().schedule(new TimerTask(){
                                            public void run() {
                                                Intent i=new Intent(LoginActivity.this,SignupActivity.class);
                                                i.putExtra("email",usernamestr);
                                                startActivity(i);
                                            }
                                        }, 5000);
                                    }
                                    else if(task.getException() instanceof FirebaseAuthWeakPasswordException){
                                        {
                                            Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                                            password.setError("Incorrect Password!");
                                            password.setText("");
                                            password.requestFocus();
                                        }}
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, "Some Error occured! Please try again!!", Toast.LENGTH_SHORT).show();
                                    }
                                    invalidtxt.setVisibility(View.VISIBLE);
                                }
                            }
                        });


            }
            else
            {
                password.setError("Password Mismatch!");
                password.requestFocus();
            }

        }else
        {
            username.setError("Invalid Email!");
            username.requestFocus();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    public void register(View v)
    {
        startActivity(new Intent(this,SignupActivity.class));
    }
}
