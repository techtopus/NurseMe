package com.example.nurseme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ReasonActivity extends AppCompatActivity {
EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);
        e=findViewById(R.id.editText11);
    }
    public void next(View v)
    {
        if(e.getText().equals(""))
        {
            e.setError("Please enter a reason for termination!!");
            return;
        }
        Intent i=new Intent(this,RatingActivity.class);
        i.putExtra("reason",e.getText().toString());
        startActivity(i);
    }
}
