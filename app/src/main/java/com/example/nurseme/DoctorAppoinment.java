package com.example.nurseme;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DoctorAppoinment extends AppCompatActivity {
EditText txtDate,txtTime,name,hosp,cause;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appoinment);
        mAuth=FirebaseAuth.getInstance();
        name=findViewById(R.id.editText5);
        hosp=findViewById(R.id.editText6);
        cause=findViewById(R.id.editText4);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
    }
    public void schedule(View v)
    {
        String nametxt,hosptxt,causetxt,time,date;
        nametxt=name.getText().toString();
        hosptxt=hosp.getText().toString();
        causetxt=cause.getText().toString();
        date=txtDate.getText().toString();
        time=txtTime.getText().toString();
        if(nametxt.isEmpty()||hosptxt.isEmpty()||causetxt.isEmpty()|| time.isEmpty()||date.isEmpty()){
            Toast.makeText(this, "please fill up the form", Toast.LENGTH_SHORT).show();
            return;}
        else{
            Doctorsappointmentclass d=new Doctorsappointmentclass(nametxt,hosptxt,causetxt,time,date);
            DatabaseReference databasereference2= FirebaseDatabase.getInstance().getReference("appointment");
            String id = databasereference2.push().getKey();
            databasereference2.child(mAuth.getCurrentUser().getUid()).setValue(d);

            //setting alarm
            Calendar cal = Calendar.getInstance();

            cal.setTimeInMillis(System.currentTimeMillis());
            cal.clear();
            int year,year1,day,month,hr,min;
            year1=date.lastIndexOf("-");
            year=Integer.valueOf(date.substring(year1+1,year1+4));
            day=date.indexOf("-");
            day=Integer.valueOf(date.substring(0,day-1));
            month=Integer.valueOf(date.substring(day+1,year1-1));
            hr=time.indexOf(":");
            hr=Integer.valueOf(time.substring(0,hr-1));
            min=time.indexOf(":");
            min=Integer.valueOf(time.substring(min+1));
            cal.set(year,month,day,hr,min);

           /* AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            // cal.add(Calendar.SECOND, 5);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);*/

           addevent( year, month, day, year, min,nametxt,hosptxt,causetxt);
            Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show();

        }
    }
    public void addevent(int year,int month,int day,int hr,int min,String name,String hosp,String cause)
    {
       // Toast.makeText(this, year+month+day, Toast.LENGTH_SHORT).show();
        Calendar beginTime = Calendar.getInstance();
        if(min<10)
            beginTime.set(year, month, day, hr, Integer.parseInt("0"+ min));
            else
        beginTime.set(year, month, day, hr, min);

        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, hr+1, min);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Doctors appointment for Dr."+name)
                .putExtra(CalendarContract.Events.DESCRIPTION, "Consultation for :- "+cause)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, hosp)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
        startActivity(intent);
    }

    public void date(View v)
    {
        int mYear,mMonth,mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void time(View v)
    {
        // Get Current Time
        int mHour,mMinute;
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    }

