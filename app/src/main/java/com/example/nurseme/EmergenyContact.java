package com.example.nurseme;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class EmergenyContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergeny_contact);
    }
    public void nurse(View v)
    {
        Toast.makeText(this, "Calling nurse......", Toast.LENGTH_SHORT).show();
    }
    public void ambulance(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "103"));
        startActivity(intent);
    }
    public void hospital(View v){

    }
}
