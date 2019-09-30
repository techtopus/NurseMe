package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Timer;
import java.util.TimerTask;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailvar,passvar,repassvar;
    Button reg;
    String email,pass,repass;
    ProgressBar pg;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        reg=findViewById(R.id.register_btn);
        emailvar=findViewById(R.id.email_txtbox);
        pg=findViewById(R.id.progressBar3);
        passvar=findViewById(R.id.password_txtbox);
        repassvar=findViewById(R.id.password2_txtbox);
            if(getIntent().getStringExtra("email")!=null)
                emailvar.setText(getIntent().getStringExtra("email"));

    }
    boolean isEmail(String  text)
    {
        CharSequence email=text.toString();
        return(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    public void signup(View v)
    {
        reg.setVisibility(View.GONE);
        pg.setVisibility(View.VISIBLE);
        email = emailvar.getText().toString().trim();
        pass = passvar.getText().toString().trim();
        repass = repassvar.getText().toString().trim();


        if(isEmail(email)==false)
        {reg.setVisibility(View.VISIBLE);
            pg.setVisibility(View.GONE);
            emailvar.setError("Enter valid Email!");
            emailvar.requestFocus();
            return;
        }
        if(pass.length()<4)
        {reg.setVisibility(View.VISIBLE);
            pg.setVisibility(View.GONE);
            passvar.setError("Enter a valid password for Min 4 charecters!");
            passvar.requestFocus();
            return;
        }
        if(!repass.equals(pass)||pass.isEmpty())
        {reg.setVisibility(View.VISIBLE);
            pg.setVisibility(View.GONE);
            repassvar.setError("The Passwords doesn't match ..please type carefully");
            repassvar.requestFocus();
            return;

        }

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pg.setVisibility(View.GONE);
                            finish();
                            Toast.makeText(SignupActivity.this, "Succeffully registered", Toast.LENGTH_SHORT).show();
                            if(getIntent().getStringExtra("type")!=null)
                            {
                                if(getIntent().getStringExtra("type").equals("nurse"))
                                {
                                    Intent i=new Intent(SignupActivity.this,add_nurse.class);
                                    i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }
                                else if(getIntent().getStringExtra("type").equals("relative"))
                                {
                                    Intent i=new Intent(SignupActivity.this,RelativeDetails.class);
                                    i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }
                            }

                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(SignupActivity.this, "Oops! You are already registered user!!\nYou will be redirected to sign in page\n in 5 seconds!!", Toast.LENGTH_SHORT).show();
                                new Timer().schedule(new TimerTask(){
                                    public void run() {
                                        Intent i=new Intent(SignupActivity.this, LoginActivity.class);
                                        i.putExtra("email",email);
                                        startActivity(i);
                                    }
                                }, 5000);
                            }

                            else{
                                reg.setVisibility(View.VISIBLE);
                                pg.setVisibility(View.GONE);
                                Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }}


                    }

                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        pg.setVisibility(View.GONE);
        reg.setVisibility(View.VISIBLE);
/*
        if (mAuth.getCurrentUser() != null) {
            finish();
            if(getIntent().getStringExtra("type").equals("relative"))
            startActivity(new Intent(this, RelativeDetails.class));

    }*/}
    public void login(View v)
    {   finish();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
