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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public void cancel(View v)
    {        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = null;
        if(email.contains("nurse")) {
            query = reference.child("contract").orderByChild("nurseemail").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        try {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                ContractClass c = dataSnapshot1.getValue(ContractClass.class);
                                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                int index = c.getPatientemail().indexOf('@');
                                String name = c.getPatientemail().substring(0, index);
                                int index2 = c.getNurseemail().indexOf('@');
                                String name2 = c.getNurseemail().substring(0, index2);
                                ContractClass cnew = new ContractClass(c.getNurseemail(), c.getPatientemail(),
                                        c.getStartdate(), date, "Cancelled By Admin", "", "", "", "");
                                DatabaseReference databasereference3;
                                databasereference3 = FirebaseDatabase.getInstance().getReference("contract");
                                databasereference3.child(name + " TO " + name2).setValue(cnew);
                                int amt=calculateamount(cnew.startdate,cnew.enddate);
                                sendNotification(c.getPatientemail(),amt);
                                sendNotification(c.getNurseemail(),amt);
                                sendemail(cnew.getPatientemail(),amt);
                                changestatus(c.getNurseemail());
                                Toast.makeText(ViewReport.this, "The contract has been cleared!\n "+amt+"\n PRESS CLEARED BUTTON!!", Toast.LENGTH_SHORT).show();
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
        {query = reference.child("contract").orderByChild("patientemail").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        try {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                ContractClass c = dataSnapshot1.getValue(ContractClass.class);
                                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                int index = c.getPatientemail().indexOf('@');
                                String name = c.getPatientemail().substring(0, index);
                                int index2 = c.getNurseemail().indexOf('@');
                                String name2 = c.getNurseemail().substring(0, index2);
                                ContractClass cnew = new ContractClass(c.getNurseemail(), c.getPatientemail(),
                                        c.getStartdate(), date, "Cancelled By Admin", "", "", "", "");
                                DatabaseReference databasereference3;
                                databasereference3 = FirebaseDatabase.getInstance().getReference("contract");
                                databasereference3.child(name + " TO " + name2).setValue(cnew);
                                int amt=calculateamount(cnew.startdate,cnew.enddate);
                                sendNotification(c.getPatientemail(),amt);
                                sendNotification(c.getNurseemail(),amt);
                                sendemail(cnew.getPatientemail(),amt);
                                changestatus(c.getNurseemail());
                                Toast.makeText(ViewReport.this, "The contract has been cleared!\n PRESS CLEARED BUTTON!!", Toast.LENGTH_SHORT).show();
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
    }
    public void sendemail(String email,int amt){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Termination of contract!!!! Pay the pending Amount Immediately");
        i.putExtra(Intent.EXTRA_TEXT   , "Respected sir, \n\n You are subjected to pay the home nurse the amouunt of $"+amt+
                " . By today evening or else you will e sujected to further actions as per Nurse Me polic \n\n Thank You! ");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
           // Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
    public int calculateamount(String p,String q) throws ParseException {

        SimpleDateFormat s=new SimpleDateFormat("dd-MM-yyyy");
        Date d1= null;
        try {
            d1 = s.parse(q);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date2=p;
        Date d2= s.parse(date2);

        return (int) ((d1.getTime() - d2.getTime())/(24*60*60*1000)*500);

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
}
