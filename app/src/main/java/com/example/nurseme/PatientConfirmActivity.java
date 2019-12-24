package com.example.nurseme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class PatientConfirmActivity extends AppCompatActivity {
String email;
TextView name,gender,age,service,nursing,description;
FirebaseAuth mAuth;
Button confirm,reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_confirm);
        mAuth=FirebaseAuth.getInstance();
        email=getIntent().getStringExtra("patientemail");
        loadvalues(email);
    }
    public void loadvalues(String email)
    {
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        service=findViewById(R.id.service);
        nursing=findViewById(R.id.nursing);
        description=findViewById(R.id.description);
        confirm=findViewById(R.id.confirm);
        reject=findViewById(R.id.reject);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Patient").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Patient patient = dataSnapshot1.getValue(Patient.class);
                            name.setText(patient.getName().toString());
                            age.setText(String.valueOf(patient.getAge()));
                            gender.setText(patient.getGender());
                            service.setText(patient.getServicestype());
                            nursing.setText(patient.getNursingtype());
                            description.setText(patient.getDesc());
                        }
                    }catch(Exception e)
                    {
                        Toast.makeText(PatientConfirmActivity.this, "e.getMessage()", Toast.LENGTH_SHORT).show();
                    }
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void confirmclick(View v){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("NursePersonalInfo");
        ref.child(mAuth.getCurrentUser().getDisplayName()).child("status").setValue("working");

        DatabaseReference databasereference2;
        databasereference2= FirebaseDatabase.getInstance().getReference("Request");
        RequestClass r=new RequestClass(mAuth.getCurrentUser().getDisplayName(),
                getIntent().getStringExtra("patientemail"), mAuth.getCurrentUser().getEmail(),"1");
        String reemail;
        reemail=getIntent().getStringExtra("patientemail");
        int index=reemail.indexOf('@');
        String name=reemail.substring(0,index);
        int index2=mAuth.getCurrentUser().getEmail().indexOf('@');
        String name2=mAuth.getCurrentUser().getEmail().substring(0,index2);
        databasereference2.child(name+" TO "+name2).setValue(r);
       confirm.setVisibility(View.GONE);
       reject.setVisibility(View.GONE);
       String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        ContractClass c=new ContractClass( mAuth.getCurrentUser().getEmail(),getIntent().getStringExtra("patientemail"),
                date,"","working","","","","");
        DatabaseReference databasereference3;
        databasereference3= FirebaseDatabase.getInstance().getReference("contract");
        databasereference3.child(name+" TO "+name2).setValue(c);
        sendNotification( getIntent().getStringExtra("patientemail"));
        startActivity(new Intent(this,NurseDashboard.class));
        }
    public void rejectclick(View view)
    {DatabaseReference databasereference2;
        databasereference2= FirebaseDatabase.getInstance().getReference("Request");
        RequestClass r=new RequestClass(mAuth.getCurrentUser().getDisplayName(),
                getIntent().getStringExtra("patientemail"), mAuth.getCurrentUser().getEmail(),"0");
        String reemail;
        reemail=getIntent().getStringExtra("patientemail");
        int index=reemail.indexOf('@');
        String name=reemail.substring(0,index-1);
        int index2=mAuth.getCurrentUser().getEmail().indexOf('@');
        String name2=mAuth.getCurrentUser().getEmail().substring(0,index2-1);
        databasereference2.child(name+" TO "+name2).setValue(r);
        confirm.setVisibility(View.GONE);
reject.setVisibility(View.GONE);
        sendNotification2( getIntent().getStringExtra("patientemail"));

    }

    public void sendNotification(final String email) {

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
                                + "\"contents\": {\"en\": \"Your request have been successfully accepted :) \"}"
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
                        name.setText(e.getMessage().toString());
                    }


                }
            }
        });
    }
    public void sendNotification2(final String email) {

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
                                + "\"contents\": {\"en\": \"Your request have been Rejected :( \"}"
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
                        name.setText(e.getMessage().toString());
                    }


                }
            }
        });
    }


}
