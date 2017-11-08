package com.develop.expense.myapplication;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

/**
 * Created by Shailendra on 12/10/2016.
 */
public class Settings extends AppCompatActivity {
    private LinearLayout timeLayout;
    private static TextView timeText;
    public static int setHour;
    public static int setMinute;
    public static Switch mswitch;
    public static String myTime;
    public static String timeValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // Summary.interstitial.loadAd(Summary.adRequest);

        timeLayout = (LinearLayout) findViewById(R.id.timeLayout);
        timeText = (TextView) findViewById(R.id.timeText);
        mswitch = (Switch) findViewById(R.id.mSwitch);
//        AdView adView=(AdView)findViewById(R.id.adView);
//        AdRequest adRequest= new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
//
//        adView.loadAd(adRequest);

//        EditText editText = new EditText(getApplicationContext());
//        getSupportActionBar().setCustomView(editText);

        SharedPreferences pref = getSharedPreferences("mSwitch", MODE_PRIVATE);
        String buttonStatus = pref.getString("status", null);
        try {
            if (buttonStatus.matches("On")) {
                mswitch.setChecked(true);
            } else {
                mswitch.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences timePref = getSharedPreferences("time", MODE_PRIVATE);
        timeValue = timePref.getString("timevalue", null);     //this is the retrived value for time when user sets for alram
        try {
            timeText.setText(timeValue);
        } catch (Exception e) {
            e.printStackTrace();
        }


        mswitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        SharedPreferences.Editor sp = getSharedPreferences("mSwitch", MODE_PRIVATE).edit();

                        if (isChecked) {

                            sp.putString("status", "On");    //set notification works here
                            myTime = timeText.getText().toString();
                            Toast.makeText(getApplicationContext(), "Reminder activated at " + myTime, Toast.LENGTH_LONG).show();
//mytime is time for notification
    String[] notificationTime=myTime.split(":");


                            Calendar calender = Calendar.getInstance();
                            try{  calender.set( Calendar.HOUR_OF_DAY,Integer.parseInt(notificationTime[0]));
                            calender.set( Calendar.MINUTE,Integer.parseInt(notificationTime[1]));
                             calender.set(Calendar.SECOND,0);} catch(Exception e){};




                            Intent intent = new Intent(getApplicationContext(), NotificationWork.class);
                            PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                            AlarmManager alramManager= (AlarmManager)getSystemService(ALARM_SERVICE);
                            alramManager.setRepeating(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);





                        } else {
                            sp.putString("status", "Off");
                        }

                        sp.commit();
                    }
                }
        );


        timeLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        DialogFragment timeFragment = new SelectTimeFragment();
                        timeFragment.show(getFragmentManager(), "TimePicker");


                    }
                }
        );

    }

    public static class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);


            return new TimePickerDialog(getActivity(), this, hour, minute, false);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timeText.setText(hourOfDay + ":" + minute);
            myTime = timeText.getText().toString();
            setHour = hourOfDay;
            setMinute = minute;
            SharedPreferences.Editor timePref = getActivity().getSharedPreferences("time", MODE_PRIVATE).edit();
            timePref.putString("timevalue", hourOfDay + ":" + minute);
            timePref.commit();
            mswitch.setChecked(false);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            Intent intent = new Intent(Settings.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
