package example.myapplication1.healthassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import example.myapplication1.healthassistant.data.DBContract;
import example.myapplication1.healthassistant.data.DatabaseHelper;
import example.myapplication1.healthassistant.models.Event;
import example.myapplication1.healthassistant.models.lekarstva;

public class prof extends AppCompatActivity implements SensorEventListener {
    ImageButton cal;
    ImageButton lek;
    ImageButton prof;
    ImageButton vod;
    ArrayList<String> ev;
    String name;
    boolean activityRunning;
    TextView username, userrost, userves, userves1, resvod, progress, stat;
    SensorManager sensorManager;
    TextView tv_Steps, ccall;
    private ListView eventListView;
    private String vs;
    private int resK = 0;
    private double ccal = 0;
    public static final String PROGRESS = "progress";
    public static final String SHARED_PREFS="sharedPrefs";

    boolean isRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tv_Steps = findViewById(R.id.textView_Steps);
        ccall = findViewById(R.id.ccal);
        cal = findViewById(R.id.imageView_cal);
        lek = findViewById(R.id.imageView_lek);
        prof = findViewById(R.id.imageView_prof);
        vod = findViewById(R.id.imageView_vod);
        progress = findViewById(R.id.prog);
        resvod = findViewById(R.id.resvod);
        eventListView = findViewById(R.id.eventListView);
        stat = findViewById(R.id.stat);

        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatActivity.class);
                intent.putExtra("username", name);
                startActivity(intent);
                finish();
            }
        });
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), cal.class);
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

        username = findViewById(R.id.fio);
        userves = findViewById(R.id.ves1);
        userves1 = findViewById(R.id.ves2);
        userrost = findViewById(R.id.rost);

        String[] projection = {
                DBContract.DBEntry._ID,
                DBContract.DBEntry.COLUMN_NAME_NAME,
                DBContract.DBEntry.COLUMN_NAME_VES,
                DBContract.DBEntry.COLUMN_NAME_GVES,
                DBContract.DBEntry.COLUMN_NAME_ROST,
                DBContract.DBEntry.COLUMN_NAME_POL,
                DBContract.DBEntry.COLUMN_NAME_VOZ
        };

        SQLiteDatabase db = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
        Cursor cursor = db.query(
                DBContract.DBEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        int n = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_NAME);
        int v = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_VES);
        int gv = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_GVES);
        int r = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_ROST);
        int p = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_POL);
        int voz = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_VOZ);
        int v1, v2, ro, vo;

        if (cursor != null) {
            cursor.moveToLast();
            name = cursor.getString(n);
            String Ves = cursor.getString(v);
            String GVes = cursor.getString(gv);
            String Rost = cursor.getString(r);
            String Pol = cursor.getString(p);
            String VOZ = cursor.getString(voz);
            username.append(name);
            userves.setText(Ves);
            userves1.setText(GVes);
            userrost.setText(Rost);
            vo  = Integer.parseInt(VOZ);
            v1 = Integer.parseInt(Ves);
            v2 = Integer.parseInt(GVes);
            ro = Integer.parseInt(Rost);
            switch (Pol) {
                case "M":
                    if (v1 > v2) {
                        ccal = (10*v1 + 6.25*ro - 5*vo + 5)*1.5;
                        resK = 40 * v1;
                    } else {
                        ccal = (10*v2 + 6.25*ro - 5*vo + 5)*1.5;
                        resK = (ro - 110) * 45;
                    }
                    break;
                case "Ж":
                    if (v1 > v2) {
                        ccal = (10*v1 + 6.25*ro - 5*vo -161)*1.5;
                        resK = 35 * v1;
                    } else {
                        ccal = (10*v2 + 6.25*ro - 5*vo -161)*1.5;
                        resK = (ro - 110) * 40;
                    }
                    break;
            }
            vs = String.valueOf(resK);
            resvod.setText(vs);
            ccall.setText(String.valueOf(ccal));
        }
        db.close();
        SQLiteDatabase db1 = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
        String[] projection1 = {
                DBContract.DBy.COLUMN_NAME_DATA,
                DBContract.DBy.COLUMN_NAME_PROG
        };

        String selectQuery = "SELECT * FROM vod";

        Cursor cursorr = db1.rawQuery(selectQuery, null);
        if (cursorr != null && cursorr.getCount() > 0) {
            Cursor cursor1 = db1.query(
                    DBContract.DBy.TABLE_NAME,
                    projection1,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            int p1 = cursor1.getColumnIndex(DBContract.DBy.COLUMN_NAME_PROG);
            if (cursor1 != null) {
                cursor1.moveToLast();
                String value_phone = cursor1.getString(p1);
                progress.setText(value_phone);
            }
            cursor1.close();
            cursorr.close();
        } else {
            // База данных пуста, нет записей
            cursorr.close();
        }
        db1.close();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();

        activityRunning = true;
        Sensor countSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            StepCounter stepCounter = new StepCounter(prof.this);
            stepCounter.start();

            int steps = stepCounter.getStepCount();

            tv_Steps.setText(String.valueOf(steps));
            stepCounter.stop();
        }
    }
    private void setEventAdpater()
    {
        SQLiteDatabase db = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
        String[] columns = {"name","data","time"};
        Cursor cursor = db.query(
                DBContract.Event.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
        ArrayList<Event> event = new ArrayList<>();
        int n =   cursor.getColumnIndex(DBContract.Event.EV_NAME);
        int dat =   cursor.getColumnIndex(DBContract.Event.DATA);
        int ti =   cursor.getColumnIndex(DBContract.Event.TIME);
        while (cursor.moveToNext()) {
            String name = cursor.getString(n);
            String data = cursor.getString(dat);
            String time = cursor.getString(ti);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localTime = LocalTime.parse(time, formatter);
            DateTimeFormatter formatterd = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            LocalDate localDate = LocalDate.parse(data, formatterd);
            event.add(new Event(name,localDate,localTime));
        }
        cursor.close();
        EventData eventAdapter = new EventData(getApplicationContext(), event);
        eventListView.setAdapter(eventAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting
        // step events
        // sensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            tv_Steps.setText(String.valueOf(event.values[0]));

        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}





