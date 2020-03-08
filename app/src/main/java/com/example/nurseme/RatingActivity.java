package com.example.nurseme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class RatingActivity extends AppCompatActivity {
RatingBar r;
EditText et;
String reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        r=findViewById(R.id.ratingBar);
        et=findViewById(R.id.editText12);
        reason=getIntent().getStringExtra("reason");

    }
    public void next(View v)
    {
        String rate= String.valueOf(r.getRating());
        rate+= " Stars";
        String review=et.getText().toString();
        if(et.getText().equals(""))
        {
            et.setError("Please Review your nurse");
        return;
        }
        Intent i=new Intent(this,FinalTerminationActiviy.class);
        i.putExtra("reason",reason);
        i.putExtra("rating",rate);
        i.putExtra("review",review);
        startActivity(i);


    }

}
