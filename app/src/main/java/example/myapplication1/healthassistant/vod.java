package example.myapplication1.healthassistant;

import android.app.AlarmManager;
import android.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import example.myapplication1.healthassistant.alarm.AlarmReceiver;
import example.myapplication1.healthassistant.data.DBContract;
import example.myapplication1.healthassistant.data.DatabaseHelper;


public class vod extends AppCompatActivity {
    ImageButton cal;
    ImageButton lek;
    ImageButton prof;
    ImageButton vod;
    ImageButton imageButton;
    LinearLayout root;
    EditText ml;
    ProgressBar pr;
    TextView text, res;
    private int cprog = 0;
    private int mll = 0;
    private int proc = 0;
    private int result = 0;
    private String k;
    private String vs;
    private int resK = 0;
    Nap1 fragment1;
    Nap2 fragment2;
    Nap3 fragment3;
    Nap4 fragment4;
    Nap5 fragment5;
    DatabaseHelper DatabaseHelper;
    public static final String PROGRESS = "progress";
    public static final String LAST_UPDATE = "last";
    public static final String SHARED_PREFS="sharedPrefs";
    String nameuser;
    private FragmentTransaction transaction;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    LocalDate newday;
    boolean vop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod);
        createNotificationChannel();
        DatabaseHelper = new DatabaseHelper(this);

        String[] projection = {
                DBContract.DBEntry._ID,
                DBContract.DBEntry.COLUMN_NAME_NAME,
                DBContract.DBEntry.COLUMN_NAME_VES,
                DBContract.DBEntry.COLUMN_NAME_GVES,
                DBContract.DBEntry.COLUMN_NAME_ROST,
                DBContract.DBEntry.COLUMN_NAME_POL
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
        int v = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_VES);
        int gv = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_GVES);
        int r = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_ROST);
        int p = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_POL);
        int n = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_NAME);
        int v1, v2, ro;
        if (cursor != null) {
            cursor.moveToLast();
            String Ves = cursor.getString(v);
            String GVes = cursor.getString(gv);
            String Rost = cursor.getString(r);
            String Pol = cursor.getString(p);
            nameuser = cursor.getString(n);

            v1 = Integer.parseInt(Ves);
            v2 = Integer.parseInt(GVes);
            ro = Integer.parseInt(Rost);

            switch (Pol) {
                case "M":
                    if (v1 > v2) {
                        resK = 40 * v1;
                    } else {
                        resK = (ro - 110) * 45;
                    }
                    break;
                case "Ж":
                    if (v1 > v2) {
                        resK = 35 * v1;
                    } else {
                        resK = (ro - 110) * 40;
                    }
                    break;
            }

            vs = String.valueOf(resK);
        }


        fragment1 = new Nap1();
        fragment2 = new Nap2();
        fragment3 = new Nap3();
        fragment4 = new Nap4();
        fragment5 = new Nap5();
        transaction = getFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment, fragment1);
        transaction.addToBackStack(null);
        transaction.commit();


        cal = findViewById(R.id.imageView_cal);
        lek = findViewById(R.id.imageView_lek);
        prof = findViewById(R.id.imageView_prof);
        vod = findViewById(R.id.imageView_vod);
        root = findViewById(R.id.root);
        pr = findViewById(R.id.progressBar);
        imageButton = findViewById(R.id.imageButton);
        ml = findViewById(R.id.ml);
        ml.setTransformationMethod(null);
        res = findViewById(R.id.res);
        res.setText(vs);
        text = findViewById(R.id.rml);

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

        pr.setMax(resK);
        result = resK;

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ml.getText().toString())) {
                    Snackbar.make(root, "Введите ml", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (fragment1.isVisible()) {
                    k = ml.getText().toString();
                    mll = Integer.parseInt(k);
                    cprog = cprog + mll;


                }
                if (fragment2.isVisible()) {
                    k = ml.getText().toString();
                    mll = Integer.parseInt(k);
                    proc = (mll * 50) / 100;
                    cprog = cprog + (mll - proc);

                }
                if (fragment3.isVisible()) {
                    k = ml.getText().toString();
                    mll = Integer.parseInt(k);
                    proc = (mll * 20) / 100;
                    cprog = cprog + (mll - proc);

                }
                if (fragment4.isVisible()) {
                    k = ml.getText().toString();
                    mll = Integer.parseInt(k);
                    proc = (mll * 90) / 100;
                    cprog = cprog + (mll - proc);
                }
                if (fragment5.isVisible()) {
                    k = ml.getText().toString();
                    mll = Integer.parseInt(k);
                    proc = (mll * 80) / 100;
                    cprog = cprog + (mll - proc);

                }

                saveData();
                pr.setProgress(cprog);

                if (result == cprog) {
                    showclasswindow();
                }
                if (result < cprog) {
                    showclasswindow();
                }
            }

        });
        loadData();
        updateData();

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), prof.class);
                intent.putExtra("progress", cprog);
                startActivity(intent);
                finish();
            }
        });


        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 10);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(vod.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent2,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),
                1000*60*60*4,pendingIntent);


    }

    private void showclasswindow() {
        AlertDialog.Builder dialogs = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog = inflater.inflate(R.layout.dialog, null);
        dialogs.setView(dialog);
        dialogs.setPositiveButton("Супер!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogs.show();
    }

    public void onClickRight(View v) {

        transaction = getFragmentManager().beginTransaction();

        if (fragment1.isVisible()) {
            transaction.replace(R.id.fragment, fragment2);
        }
        if (fragment2.isVisible()) {
            transaction.replace(R.id.fragment, fragment3);
        }
        if (fragment3.isVisible()) {
            transaction.replace(R.id.fragment, fragment4);
        }
        if (fragment4.isVisible()) {
            transaction.replace(R.id.fragment, fragment5);
        }
        if (fragment4.isVisible()) {
            transaction.replace(R.id.fragment, fragment1);
        }

        transaction.commit();
    }

    public void onClickLeft(View v) {

        transaction = getFragmentManager().beginTransaction();

        if (fragment1.isVisible()) {
            transaction.replace(R.id.fragment, fragment5);
        }
        if (fragment2.isVisible()) {
            transaction.replace(R.id.fragment, fragment1);
        }
        if (fragment3.isVisible()) {
            transaction.replace(R.id.fragment, fragment2);
        }
        if (fragment4.isVisible()) {
            transaction.replace(R.id.fragment, fragment3);
        }
        if (fragment5.isVisible()) {
            transaction.replace(R.id.fragment, fragment4);
        }
        transaction.commit();
        removeData();
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "foxandroidReminderChannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
    public void saveData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PROGRESS, cprog);
        text.setText(cprog + " ml");
        editor.apply();
        String prog = ""+cprog;
        newday = LocalDate.now();
        String data = CalendarUtils.formattedDate(newday);
        if(cprog!=0) {
            DatabaseHelper.insertDatavod(data, prog,nameuser);
        }

    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text.setText(cprog + " ml");
        cprog = sharedPreferences.getInt(PROGRESS, 0);
    }

    public void updateData() {
        pr.setProgress(cprog);
        text.setText(cprog + " ml");

    }

    public void removeData() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        long lastUpdate = sharedPreferences.getLong(LAST_UPDATE, -1);
        if (lastUpdate == -1) {
            sharedPreferences.edit().putLong(LAST_UPDATE, System.currentTimeMillis()).apply();
        } else {

             vop = prov(lastUpdate);

            if (vop) {
                sharedPreferences.edit().clear().apply();
            }
        }
    }

    public long getLastValidTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime().getTime();
    }

    public boolean prov (long lastValue) {
        return getLastValidTimestamp() > lastValue;
    }
}


