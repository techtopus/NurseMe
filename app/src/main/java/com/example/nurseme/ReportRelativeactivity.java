package com.example.nurseme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class ReportRelativeactivity extends AppCompatActivity {
EditText e;
RadioButton low,med,high;
String severity;
FirebaseAuth mAuth;
RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_relativeactivity);
        e=findViewById(R.id.editText);
        low=findViewById(R.id.radioButton);
        med=findViewById(R.id.radioButton2);
        high=findViewById(R.id.radioButton3);
        mAuth=FirebaseAuth.getInstance();
    rg=findViewById(R.id.radioGroup);
    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId==R.id.radioButton)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    e.setBackgroundColor(R.color.palegreen);
                    severity="low";
                    Toast.makeText(ReportRelativeactivity.this, "low", Toast.LENGTH_SHORT).show();
                }
            }
            if(checkedId==R.id.radioButton2)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    e.setBackgroundColor(R.color.paleblue);
                    severity="low";
                    Toast.makeText(ReportRelativeactivity.this, "low", Toast.LENGTH_SHORT).show();
                }
            }
            if(checkedId==R.id.radioButton3)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    e.setBackgroundColor(R.color.palered);
                    severity="low";
                    Toast.makeText(ReportRelativeactivity.this, "low", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });
    }

@SuppressLint("ResourceAsColor")
public void reportfn(View v){
        if(e.getText().toString().equals(""))
        {
            e.setError("please enter text");
            return;
        }
        if(low.isChecked()||med.isChecked()||high.isChecked())
        {
            if(low.isChecked())
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    e.setBackgroundColor(R.color.palegreen);
                    severity="low";
                    Toast.makeText(this, "low", Toast.LENGTH_SHORT).show();
                }
            }
            if(med.isChecked())
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    e.setBackgroundColor(R.color.paleblue);
                    severity="med";
                    Toast.makeText(this, "med", Toast.LENGTH_SHORT).show();
                }
            }
            if(high.isChecked())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                e.setBackgroundColor(R.color.palered);
                severity="high";
                Toast.makeText(this, "high", Toast.LENGTH_SHORT).show();
            }
        } DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            //Toast.makeText(PendingRequestActivity.this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

            Query query = reference.child("contract").orderByChild("patientemail").equalTo(mAuth.getCurrentUser().getEmail());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                       // Toast.makeText(RelativeDashboard.this, "gone", Toast.LENGTH_SHORT).show();
                        try {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                ContractClass r = dataSnapshot1.getValue(ContractClass.class);
                                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("report");
                                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                                ref.child("Relative : "+mAuth.getCurrentUser().getDisplayName()).setValue(new
                                        reportclass(e.getText().toString(),severity,r.getNurseemail(),date,"nil",
                                        mAuth.getCurrentUser().getEmail()));

                            }
                        } catch (Exception e) {
                            Toast.makeText(ReportRelativeactivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Toast.makeText(this, "Report send successfully !", Toast.LENGTH_SHORT).show();
            sendNotifiction("bba@admin.com");
        startActivity(new Intent(this,RelativeDashboard.class));
        }
    else
        {
            Toast.makeText(this, "Please select the severity", Toast.LENGTH_SHORT).show();
            return;
        }

}
    public void sendNotifiction (final String email) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT= Build.VERSION.SDK_INT;
                if(SDK_INT>8)
                {
                    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);



                    try {
                        String jsonResponse;


                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic M2QzOGY5MmMtOTMyZi00ODU0LWFmNjItYmI5YjFjZTEwYzAz");
                        con.setRequestMethod("POST");
                        //  Toast.makeText(NurseRequestActivity.this, email, Toast.LENGTH_SHORT).show();
                        String strJsonBody = "{"
                                + "\"app_id\": \"2e20bdb3-de39-46ae-a545-bcf4d9ce9542\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" +email  + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"A NEW ISSUE HAS BEEN RAISED! \"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Exception e) {

                        //  Toast.makeText(NurseRequestActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                       // name.setText(e.getMessage().toString());
                    }


                }
            }
        });
    }
}
