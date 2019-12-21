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
import java.util.Scanner;

public class NurseRequestActivity extends AppCompatActivity {
String email;
FirebaseAuth mAuth;
Button request,requested;
TextView name,age,gender,locality,district,sah,nc,dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_request);
        email=getIntent().getStringExtra("nurseEmail");
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        request=findViewById(R.id.request);
        requested=findViewById(R.id.request2);
        gender=findViewById(R.id.gender);
        mAuth=FirebaseAuth.getInstance();
        locality=findViewById(R.id.locality);
        district=findViewById(R.id.district);
        sah=findViewById(R.id.stayathome);
        nc=findViewById(R.id.nightcare);
        dc=findViewById(R.id.daycare);

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        Query query2 = reference2.child("Request").orderByChild("patientemail").equalTo(mAuth.getCurrentUser().getEmail());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                     for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                         RequestClass n = dataSnapshot1.getValue(RequestClass.class);
                                                         if(n.getNurseemail().equals(email))
                                                         {
                                                             if(Integer.valueOf(n.getStatus())==-1){
                                                                 request.setVisibility(View.GONE);
                                                                 requested.setVisibility(View.VISIBLE);
                                                             }
                                                         }
                                                     }
                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             });

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("NursePersonalInfo").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    NursePersonalInfo n = dataSnapshot1.getValue(NursePersonalInfo.class);
                name.setText(n.getName());
                age.setText(n.getAge());
                gender.setText(n.getGender());
                locality.setText(n.getLocality());
                district.setText(n.getDistrict());
                if(n.getStryathome()==1)
                {
                    sah.setVisibility(View.VISIBLE);
                }
                    if(n.getNightcare()==1)
                    {
                        nc.setVisibility(View.VISIBLE);
                    }
                    if(n.getDaycare()==1)
                    {
                        dc.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void requestclick(View v)
    {
        DatabaseReference databasereference2;
        databasereference2= FirebaseDatabase.getInstance().getReference("Request");
        RequestClass r=new RequestClass(mAuth.getCurrentUser().getDisplayName(),mAuth.getCurrentUser().getEmail(),
                email,"-1");
        String reemail;
        reemail=email;
        int index=email.indexOf('@');
        String name=email.substring(0,index);
        int index2=mAuth.getCurrentUser().getEmail().indexOf('@');
        String name2=mAuth.getCurrentUser().getEmail().substring(0,index2);
        databasereference2.child(name2+" TO "+name).setValue(r);
        request.setVisibility(View.GONE);
        requested.setVisibility(View.VISIBLE);
        sendNotification(reemail);
        //Toast.makeText(this, "Request send successfully", Toast.LENGTH_SHORT).show();
    }
    public void gobackclick(View v)
    {
        startActivity(new Intent(this,SearchActivity.class));

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
                                + "\"contents\": {\"en\": \"You have got PATIENT REQUEST\"}"
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
