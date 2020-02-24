package com.example.nurseme;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class ViewReport extends AppCompatActivity {
TextView t,t2,t3,t4;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        t=findViewById(R.id.textView127);
        t2=findViewById(R.id.textView125);
        t3=findViewById(R.id.textView132);
        t4=findViewById(R.id.textView130);
         email=getIntent().getStringExtra("patientemail");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("report").orderByChild("complaintsender").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            reportclass c = dataSnapshot1.getValue(reportclass.class);
                            t.setText(c.getSeverity());
                            t2.setText(c.getComplaint());
                            t3.setText(c.getDate());
                            t4.setText(c.getComplaitrecipient());
                        }
                    } catch (Exception e) {
                        Toast.makeText(ViewReport.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void cleared(View v)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("report").orderByChild("complaintsender").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            reportclass c = dataSnapshot1.getValue(reportclass.class);
                            if(!email.contains("nurse")){
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("report");
                                String namestr = email;
                                int ind = namestr.indexOf('@');
                                namestr = namestr.substring(0, ind);
                                ref.child("Relative : " + namestr).setValue(new
                                        reportclass(c.getComplaint(), c.getSeverity(),c.getComplaitrecipient(), c.getDate(), "cleared",
                                      c.getComplaintsender()));
                                Toast.makeText(ViewReport.this, "Issue has been cleared", Toast.LENGTH_SHORT).show();
                                sendNottification(email);
                                startActivity(new Intent(ViewReport.this,GenerateReport.class));
                            }
                            else
                            { DatabaseReference ref = FirebaseDatabase.getInstance().getReference("report");
                                String namestr = email;
                                int ind = namestr.indexOf('@');
                                namestr = namestr.substring(0, ind);
                                ref.child("Nurse : " + namestr).setValue(new
                                        reportclass(c.getComplaint(), c.getSeverity(),c.getComplaitrecipient(), c.getDate(), "cleared",
                                        c.getComplaintsender()));
                                Toast.makeText(ViewReport.this, "Issue has been cleared", Toast.LENGTH_SHORT).show();
                                sendNottification(email);
                                startActivity(new Intent(ViewReport.this,GenerateReport.class));
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(ViewReport.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void callrelative(View v) {
        if (email.contains("nurse")) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child("contract").orderByChild("nurseemail").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        try {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                ContractClass c = dataSnapshot1.getValue(ContractClass.class);
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                Query query = reference.child("Relatives").orderByChild("emailid").equalTo(c.getPatientemail());
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            try {
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    Relative rr = dataSnapshot1.getValue(Relative.class);
                                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                                    intent.setData(Uri.parse("tel:" + rr.getContactno()));
                                                    startActivity(intent);
                                                }
                                            } catch (Exception e) {
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        } catch (Exception e) {
                            Toast.makeText(ViewReport.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child("Relatives").orderByChild("emailid").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        try {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                Relative rr = dataSnapshot1.getValue(Relative.class);
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + rr.getContactno()));
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }}
        public void callnurse(View v) {
if (email.contains("nurse")) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("NursePersonalInfo").orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            try {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    NursePersonalInfo rr = dataSnapshot1.getValue(NursePersonalInfo.class);
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:" + rr.getPhoneno()));
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            else
            {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("contract").orderByChild("patientemail").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            try {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    ContractClass c = dataSnapshot1.getValue(ContractClass.class);
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                    Query query = reference.child("NursePersonalInfo").orderByChild("email").equalTo(c.getNurseemail());
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                try {
                                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                        NursePersonalInfo rr = dataSnapshot1.getValue(NursePersonalInfo.class);
                                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                                        intent.setData(Uri.parse("tel:" + rr.getPhoneno()));
                                                        startActivity(intent);
                                                    }
                                                } catch (Exception e) {
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            } catch (Exception e) {
                                Toast.makeText(ViewReport.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,GenerateReport.class));
    }
    public void sendNottification(final String email){
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
                                + "\"contents\": {\"en\": \"YOUR ISSUES HAS BEEN CLEARED! \"}"
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
