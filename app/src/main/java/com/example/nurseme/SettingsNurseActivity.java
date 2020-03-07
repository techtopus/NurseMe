package com.example.nurseme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingsNurseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_nurse);
    }
    public void resetpassword(View v){
        startActivity(new Intent(this,ResetPasswordActivity.class));
    }
    public void deleteprofile(View v){startActivity(new Intent(this,DeleteProfileActivity.class));}
}
