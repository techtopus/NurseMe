package com.example.nurseme;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class BpGraphActivity extends AppCompatActivity {
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList lineEntries;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp_graph);

        lineChart = findViewById(R.id.lineChart);
        getEntries();
        lineDataSet = new LineDataSet(lineEntries, "Blood Pressure");
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(18f);
        mAuth=FirebaseAuth.getInstance();

    }
    private void getEntries() {
        lineEntries = new ArrayList<>();
        lineEntries.add(new Entry( 0,0));
        lineEntries.add(new Entry(1, 1));
        lineEntries.add(new Entry(2, 1));
        lineEntries.add(new Entry(3, 2));
        lineEntries.add(new Entry(4, 3));
        lineEntries.add(new Entry(5, 4));

        DatabaseReference referencer = FirebaseDatabase.getInstance().getReference();
        Query queryr = referencer.child("Health Data").child("Blood Pressure").child("abc");
        queryr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                             BloodPressure c = dataSnapshot1.getValue(BloodPressure.class);
                            Toast.makeText(BpGraphActivity.this,c.getCondition().toString(), Toast.LENGTH_SHORT).show();
                            }
                    }catch(Exception e){
                    Toast.makeText(BpGraphActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }}}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/*
        DatabaseReference referenced = FirebaseDatabase.getInstance().getReference();
        Query queryd = referenced.child("contract").orderByChild("nurseemail").equalTo(mAuth.getCurrentUser().getEmail());
        queryd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            final ContractClass c = dataSnapshot1.getValue(ContractClass.class);
                            if (c.getStatus().equals("working")) {

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                Query query = reference.child("Health Data").orderByChild("email").equalTo(c.getPatientemail());
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }}catch(Exception e){
                            Toast.makeText(BpGraphActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/

    }
}
