package com.example.nurseme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class acceptviewemail extends AppCompatActivity {
EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptviewemail);
        e=findViewById(R.id.editText3);
    }
    public void search(View v)
    {if(!e.getText().toString().contains("nurse"))
    {
        Toast.makeText(this, "Enter a valid nurse ID", Toast.LENGTH_SHORT).show();
        return ;
    }else{
        Intent i= new Intent(this,ViewNurse.class);
        i.putExtra("email",e.getText().toString().trim());
        startActivity(i);
    }}}


