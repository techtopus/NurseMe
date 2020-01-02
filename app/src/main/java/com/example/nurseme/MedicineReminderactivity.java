package com.example.nurseme;

import android.app.Dialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MedicineReminderactivity extends AppCompatActivity {
TextView before,after,schdle;
FirebaseAuth mAuth;
Spinner spinner;
EditText name;
int count;
RadioGroup rg;
RadioButton beforeradio,afterradio;
Dialog mydialogue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydialogue = new Dialog(MedicineReminderactivity.this);
        mydialogue.setContentView(R.layout.addmedicine_popup);
        setContentView(R.layout.activity_medicine_reminderactivity);
        before = findViewById(R.id.textView66);
        after = findViewById(R.id.textView68);
        mAuth = FirebaseAuth.getInstance();
        beforeradio = mydialogue.findViewById(R.id.before);
        afterradio = mydialogue.findViewById(R.id.after);
        rg = mydialogue.findViewById(R.id.rg);
        name = mydialogue.findViewById(R.id.editText7);
        spinner = findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Toast.makeText(MedicineReminderactivity.this, "entered", Toast.LENGTH_SHORT).show();
                if(spinner.getSelectedItem().equals("Morning")) {
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                    Query query2 = reference2.child("medicine").orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    medicineclass m = dataSnapshot1.getValue(medicineclass.class);
                                    before.setText(m.getMorning());
                                    after.setText(m.getMorning2());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                if(spinner.getSelectedItem().equals("Noon"))  {
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                    Query query2 = reference2.child("medicine").orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    medicineclass m = dataSnapshot1.getValue(medicineclass.class);
                                    before.setText(m.getNoon());
                                    after.setText(m.getNoon2());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }  if(spinner.getSelectedItem().equals("Night"))  {
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                    Query query2 = reference2.child("medicine").orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    medicineclass m = dataSnapshot1.getValue(medicineclass.class);
                                    before.setText(m.getNight());
                                    after.setText(m.getNight2());
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
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
    public void popupme(View v)
    {
        Button b = mydialogue.findViewById(R.id.button4);
        mydialogue.show();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydialogue.dismiss();
                add(view);
            }
        });
       /* schdle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminder(view);
            }
        });*/
    }

    public void add(View v)
    {
        int selectedId = rg.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton sex = (RadioButton) findViewById(selectedId);
        String gender= (String) sex.getText();


        String time= (String) spinner.getSelectedItem();
        String beforeorafter;


           // Toast.makeText(this, "mrng", Toast.LENGTH_SHORT).show();
        if(gender.equals("Before Food"))
        { Toast.makeText(this, "before", Toast.LENGTH_SHORT).show();
            String str=before.getText().toString();
        count=0;
        for(int i=0;i<str.length();++i)
        {
            if(str.charAt(i) == '\n'){
                ++count;
            }

        }


        before.append(String.valueOf(count+1));
        before.append(". ");
        before.append(name.getText());
        name.setText("");
        before.append("\n");
        //Write to database
            try {
                DatabaseReference ref =FirebaseDatabase.getInstance().getReference();
                if(spinner.getSelectedItem().equals("Morning")) {
                    ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("morning").setValue(before.getText().toString());
                }
            else if (spinner.getSelectedItem().equals("Noon")){
                        ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("noon").setValue(before.getText().toString());

                    }
                else if (spinner.getSelectedItem().equals("Night")){
                    ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("night").setValue(before.getText().toString());

                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }



        }
            if(gender.equals("After Food"))
            {String str=after.getText().toString();

            count=0;
                for(int i=0;i<str.length();++i)
                {
                    if(str.charAt(i) == '\n'){
                        ++count;
                    }

                }
                Toast.makeText(this, "after", Toast.LENGTH_SHORT).show();
                after.append(String.valueOf(count+1));
                after.append(". ");
                after.append(name.getText());
                name.setText("");
                after.append("\n");
                //Write to database
                try {
                    DatabaseReference ref =FirebaseDatabase.getInstance().getReference();
                    if(spinner.getSelectedItem().equals("Morning")) {
                        ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("morning2").setValue(after.getText().toString());
                    }
                else if(spinner.getSelectedItem().equals("Noon")) {
                        ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("noon2").setValue(after.getText().toString());
                    }
                else if(spinner.getSelectedItem().equals("Night")) {
                        ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("night2").setValue(after.getText().toString());
                    }
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        //Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
    }
public void remove(View v){
    before.setText(before.getText().toString());
    int index;
    String str=before.getText().toString();
    str=str.substring(0,str.length()-2);
    if(!str.contains("\n"))
        index=1;
    else
   index=str.lastIndexOf("\n");


        before.setText(before.getText().toString().substring(0,index-1));
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
    if(spinner.getSelectedItem().equals("Morning")) {
        ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("morning").setValue(before.getText().toString());
    }
    else if (spinner.getSelectedItem().equals("Noon")){
        ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("noon").setValue(before.getText().toString());

    }
    else if (spinner.getSelectedItem().equals("Night")){
        ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("night").setValue(before.getText().toString());

    }

}public void remove2(View v){
        after.setText(after.getText().toString());
        int index;
        String str=after.getText().toString();
        str.substring(0,str.length()-2);
        if(!str.contains("\n"))
            index=1;
        else
       index=str.lastIndexOf("\n");

        after.setText(after.getText().toString().substring(0,index-1));
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
        if(spinner.getSelectedItem().equals("Morning")) {
            ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("morning2").setValue(after.getText().toString());
        }
        else if (spinner.getSelectedItem().equals("Noon")){
            ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("noon2").setValue(after.getText().toString());

        }
        else if (spinner.getSelectedItem().equals("Night")){
            ref.child("medicine").child(mAuth.getCurrentUser().getUid()).child("night2").setValue(after.getText().toString());

        }

    }
public void reminder(View v)
{ /*Intent intent = new Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                beginCalendarTime.getTimeInMillis())
        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                endCalendarTime.getTimeInMillis())
        .putExtra(CalendarContract.Events.TITLE, heading)
        .putExtra(CalendarContract.Events.DESCRIPTION, "To be added")
        .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
        .putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=10");
startActivity(Intent);*/
}
}
