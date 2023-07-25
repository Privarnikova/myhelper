package example.myapplication1.healthassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import example.myapplication1.healthassistant.alarm.AlarmReceiver;
import example.myapplication1.healthassistant.data.DatabaseHelper;
import example.myapplication1.healthassistant.models.Event;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    LinearLayout timel3;
    TimePicker time3;
    private TextView eventDateTV, eventTimeTV;
    ImageButton cal;
    ImageButton lek;
    ImageButton prof;
    ImageButton vod;
    private LocalTime time;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar;
    int day, mes;
    DatabaseHelper DatabaseHelper;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Дата: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Время: " + CalendarUtils.formattedTime(time));
        String dat = CalendarUtils.formattedDate1(CalendarUtils.selectedDate);
        DatabaseHelper = new DatabaseHelper(this);
        String[] parts = dat.split(" ");
        String res = parts[0];
        String res1 = parts[1];
        day = Integer.parseInt(res);
        mes = Integer.parseInt(res1);
        cal = findViewById(R.id.imageView_cal);
        lek = findViewById(R.id.imageView_lek);
        prof = findViewById(R.id.imageView_prof);
        vod = findViewById(R.id.imageView_vod);


        timel3 = findViewById(R.id.timel3);
        time3 = findViewById(R.id.timePicker3);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),cal.class);
                startActivity(intent);
                finish();
            }
        });
        lek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        vod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), vod.class);
                startActivity(intent);
                finish();
            }
        });
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), prof.class);
                startActivity(intent);
                finish();
            }
        });


        eventTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timel3.setVisibility(View.VISIBLE);

                time3.setIs24HourView(true);
                time3.setHour(9);
                time3.setMinute(00);
                time3.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
                {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        eventTimeTV.setText(String.format(Locale.getDefault(), "%02d:%02d",hourOfDay, minute));
                        time.of(hourOfDay,minute,0);


                    }
                });


            }
        });
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        String t = eventTimeTV.getText().toString()+":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(t, formatter);
        String data = CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, localTime);
        Event.eventsList.add(newEvent);

        DatabaseHelper.insertDataEvent(data,t, eventName);

        finish();
    }

}