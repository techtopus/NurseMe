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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class FinalTerminationActiviy extends AppCompatActivity {
TextView t,t2;
FirebaseAuth mAuth;
String reason,review,rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_termination_activiy);
mAuth=FirebaseAuth.getInstance();
t=findViewById(R.id.textView134);
t2=findViewById(R.id.textView135);
reason=getIntent().getStringExtra("reason");
rating=getIntent().getStringExtra("rating");
review=getIntent().getStringExtra("review");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("contract").orderByChild("patientemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                     if (dataSnapshot.exists()) {
                                                         try {
                                                             for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                 ContractClass c = dataSnapshot1.getValue(ContractClass.class);
if(c.getStatus().equals("working")) {
    int yt = calculateamount(c.getStartdate());
    //Toast.makeText(FinalTerminationActiviy.this, String.valueOf( (d1.getTime() - d2.getTime())/(24*60*60*1000)*500), Toast.LENGTH_SHORT).show();
} }
                                                         } catch (Exception e) {
                                                             Toast.makeText(FinalTerminationActiviy.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             });


    }
    @SuppressLint("NewApi")
    public int calculateamount(String p) throws ParseException {

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        SimpleDateFormat s=new SimpleDateFormat("dd-MM-yyyy");
        Date d1=s.parse(date);
        String date2=p;
        Date d2= s.parse(date2);
        int days=(int) ((d1.getTime() - d2.getTime())/(24*60*60*1000));
       long salary= days*500;
        if(salary<15000){
            salary=15000;
        }
t.setText("");
        t2.setText("");
        t.setText(" ₹ . ");
        t.append(String.valueOf( salary));
        t2.append((String.valueOf( days)));
        return (int) salary;
       // Toast.makeText(this, String.valueOf( (d1.getTime() - d2.getTime())/(24*60*60*1000)*500), Toast.LENGTH_SHORT).show();
        //e2.setText(String.valueOf( (d1.getTime() - d2.getTime())/(24*60*60*1000)*500));

    }
    public void confirm(View v)
    {  final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = null;
        query = reference.child("contract").orderByChild("patientemail").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            ContractClass c = dataSnapshot1.getValue(ContractClass.class);
                            if(c.getStatus().equals("working")){
                            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            int index = c.getPatientemail().indexOf('@');
                            String name = c.getPatientemail().substring(0, index);
                            int index2 = c.getNurseemail().indexOf('@');
                            String name2 = c.getNurseemail().substring(0, index2);
                            ContractClass cnew = new ContractClass(c.getNurseemail(), c.getPatientemail(),
                                    c.getStartdate(), date, "TERMINATED", reason, rating, review, String.valueOf(
                                            calculateamount(c.getStartdate())));
                            DatabaseReference databasereference3;
                            databasereference3 = FirebaseDatabase.getInstance().getReference("contract");
                            databasereference3.child(name + " TO " + name2).setValue(cnew);

                            sendNotification(c.getPatientemail(),(calculateamount(c.getStartdate())));
                            sendNotification(c.getNurseemail(),(calculateamount(c.getStartdate())));

                            changestatus(c.getNurseemail());
                            Toast.makeText(FinalTerminationActiviy.this, "The contract has been cleared!\n PRESS CLEARED BUTTON!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FinalTerminationActiviy.this,RelativeDashboard.class));
                        }}
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void changestatus(String email){
        // Toast.makeText(this, "entered", Toast.LENGTH_SHORT).show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("NursePersonalInfo").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            NursePersonalInfo c = dataSnapshot1.getValue(NursePersonalInfo.class);
                            DatabaseReference reff;
                            reff= FirebaseDatabase.getInstance().getReference();
                            reff.child("NursePersonalInfo").child(c.getName()).child("status").setValue("nil");
                            // Toast.makeText(ViewReport.this, c.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void sendNotification(final String email, final int amt){
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
                                + "\"contents\": {\"en\": \"YOUR Contract has been TERMINATED!! Amount is "+amt+" \"}"
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
