package com.example.nurseme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GenerateReport extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<reportclass> listItems;
    ProgressBar p;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.hasFixedSize();
        p=findViewById(R.id.progressBar5);
   txt=findViewById(R.id.textView133);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        checkPending("high");
    }
    public void checkPending(final String sev)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("report").orderByChild("status").equalTo("nil");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    p.setVisibility(View.GONE);
                   // Toast.makeText(GenerateReport.this, "elle", Toast.LENGTH_SHORT).show();
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            reportclass r = dataSnapshot1.getValue(reportclass.class);

                            if(r.getSeverity().equals(sev))
                            {
                                listItems.add(r);
                            }
                        }


                    } catch (Exception e) {
                        Toast.makeText(GenerateReport.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    if(sev.equals("high"))
                        checkPending("medium");
                    else if(sev.equals("medium"))
                        checkPending("low");

                    adapter=new ReportsAdapterClass(listItems,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                }
                else
        {
            p.setVisibility(View.GONE);
            txt.setVisibility(View.VISIBLE);
          //  img.setVisibility(View.VISIBLE);
        }
                //Toast.makeText(GenerateReport.this, "hi", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,AdminDashboard.class));
    }
}
