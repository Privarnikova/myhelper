package example.myapplication1.healthassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import example.myapplication1.healthassistant.alarm.AlarmReceiver;
import example.myapplication1.healthassistant.data.DatabaseHelper;
import example.myapplication1.healthassistant.models.lekarstva;

public class DoblekActivity extends AppCompatActivity {
    EditText naz;
     Switch switch1;
     TimePicker time,time2,time3;
    NumberPicker prod, interval,kol;
    TextView  prodol, iner, otmena, vremtext,vremtext2,vremtext3, kolch;
    LinearLayout  prodollayoyt, interlayoyt,  yved, timel,timel2,timel3, kolich, redactor, redactor2, redactor3,vr2, vr3 ;
    EditText date,doz;
    private FragmentTransaction transaction;
    ImageButton imageView_lek;
    private Calendar calendar;
    int chek = 0;
    DatePickerDialog datePickerDialog;
    DatabaseHelper DatabaseHelper;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int res = 0;
    final String[] values= {"Каждый день", "Раз в неделю"};
    final String[] val= {"1 раз", "2 раза", "3 раза"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doblek);
        createNotificationChannel();
        naz = findViewById(R.id.naz);
        switch1 = findViewById(R.id.switch1);
        prod = findViewById(R.id.prodPicker);
        interval = findViewById(R.id.inerPicker);
        prodol = findViewById(R.id.prodch);
        iner = findViewById(R.id.interch);
        otmena = findViewById(R.id.otm);
        interlayoyt = findViewById(R.id.kolll);
        prodollayoyt = findViewById(R.id.prodol);
        imageView_lek = findViewById(R.id.dob);
        date = (EditText) findViewById(R.id.datanach);

        doz = findViewById(R.id.doza);

        yved = findViewById(R.id.yved);

        kol = findViewById(R.id.kolPicker);
        kolich = findViewById(R.id.kolich);
        kolch = findViewById(R.id.kolch);
        DatabaseHelper = new DatabaseHelper(this);

        redactor = findViewById(R.id.redactor);
        timel = findViewById(R.id.timel);
        vremtext = findViewById(R.id.vremtext);
        time = findViewById(R.id.timePicker);

        redactor2 = findViewById(R.id.redactor2);
        timel2 = findViewById(R.id.timel2);
        vremtext2 = findViewById(R.id.vremtext2);
        time2 = findViewById(R.id.timePicker2);

        redactor3 = findViewById(R.id.redactor3);
        timel3 = findViewById(R.id.timel3);
        vremtext3 = findViewById(R.id.vremtext3);
        time3 = findViewById(R.id.timePicker3);



        vr2 = findViewById(R.id.vr2);

        vr3 = findViewById(R.id.vr3);


        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((Switch) v).isChecked();
                if (checked){
                    chek = 1;
                    yved.setVisibility(View.VISIBLE);
                }else{
                    yved.setVisibility(View.GONE);
                }
            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(DoblekActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        prodollayoyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prod.setVisibility(View.VISIBLE);
                prod.setMaxValue(getResources().getStringArray(R.array.numberPicker).length);
                prod.setMinValue(0);
                prod.getResources().getStringArray(R.array.numberPicker);
                prod.setWrapSelectorWheel(true);
                prod.setOnValueChangedListener(new MyListener1());
            }
        });

        interlayoyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interval.setVisibility(View.VISIBLE);
                interval.setMinValue(0);
                interval.setMaxValue(1);
                interval.setDisplayedValues(values);
                interval.setOnValueChangedListener(new MyListener());
            }
        });


        kolich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kol.setVisibility(View.VISIBLE);
                kol.setMinValue(0);
                kol.setMaxValue(2);
                kol.setDisplayedValues(val);
                kol.setOnValueChangedListener(new MyListenerKol());
            }
        });


        redactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timel.setVisibility(View.VISIBLE);

                time.setIs24HourView(true);
                time.setHour(9);
                time.setMinute(0);

                time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
                {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                        vremtext.setText(String.format(Locale.getDefault(), "%02d:%02d",hourOfDay, minute));

                        Calendar startalarm = Calendar.getInstance();
                        startalarm.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        startalarm.set(Calendar.SECOND, 0);
                        startalarm.set(Calendar.MINUTE, minute);
                        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent3 = new Intent(DoblekActivity.this, AlarmReceiver.class);
                        pendingIntent = PendingIntent.getBroadcast(DoblekActivity.this,0,intent3,0);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,startalarm.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY,pendingIntent);
                    }
                });
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timel.setVisibility(View.GONE);
                    }
                });


            }
        });

        redactor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timel2.setVisibility(View.VISIBLE);

                time2.setIs24HourView(true);
                time2.setHour(15);
                time2.setMinute(00);
                time2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
                {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        vremtext2.setText(String.format(Locale.getDefault(), "%02d:%02d",hourOfDay, minute));
                        Calendar startalarm = Calendar.getInstance();
                        startalarm.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        startalarm.set(Calendar.SECOND, 0);
                        startalarm.set(Calendar.MINUTE, minute);
                        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent2 = new Intent(DoblekActivity.this, AlarmReceiver.class);
                        pendingIntent = PendingIntent.getBroadcast(DoblekActivity.this,0,intent2,0);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,startalarm.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY,pendingIntent);
                    }
                });
                time2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timel2.setVisibility(View.GONE);
                    }
                });


            }
        });
        redactor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timel3.setVisibility(View.VISIBLE);

                time3.setIs24HourView(true);
                time3.setHour(21);
                time3.setMinute(00);
                time3.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
                {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        vremtext3.setText(String.format(Locale.getDefault(), "%02d:%02d",hourOfDay, minute));
                        Calendar startalarm = Calendar.getInstance();
                        startalarm.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        startalarm.set(Calendar.SECOND, 0);
                        startalarm.set(Calendar.MINUTE, minute);
                        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent2 = new Intent(DoblekActivity.this, AlarmReceiver.class);
                        pendingIntent = PendingIntent.getBroadcast(DoblekActivity.this,0,intent2,0);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,startalarm.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY,pendingIntent);

                    }
                });
                time3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timel3.setVisibility(View.GONE);
                    }
                });


            }
        });

        imageView_lek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lekDoz = doz.getText().toString();
                String lekName = naz.getText().toString();
                String lekData = date.getText().toString();
                String lekInter= iner.getText().toString();
                String lekProd = prodol.getText().toString();
                String lekKol = kolch.getText().toString();
                String lekt1 = vremtext.getText().toString();
                String lekt2 = vremtext2.getText().toString();
                String lekt3 = vremtext3.getText().toString();
                if(lekDoz.equals("")|| lekName.equals("")||lekData.equals("")||lekInter.equals("") || lekProd.equals("")){
                    Toast.makeText(DoblekActivity.this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseHelper.insertlek(lekName, lekData, lekInter,lekProd,lekKol,lekDoz,lekt1, lekt2, lekt3,chek);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        otmena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }

    private class MyListener implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            String value = "" + newVal;
            iner.setText((values[Integer.parseInt(value)]) );
            interval.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interval.setVisibility(View.GONE);
                }
            });
        }
    }

    private class MyListenerKol implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            String value = "" + newVal;

            if (val[Integer.parseInt(value)] =="2 раза")
            {
                vr2.setVisibility(View.VISIBLE);
                vr3.setVisibility(View.GONE);
            }
            if (val[Integer.parseInt(value)] =="3 раза")
            {
                vr2.setVisibility(View.VISIBLE);
                vr3.setVisibility(View.VISIBLE);
            }
            if (val[Integer.parseInt(value)] =="1 раз")
            {
                vr2.setVisibility(View.GONE);
                vr3.setVisibility(View.GONE);
            }
            kolch.setText((val[Integer.parseInt(value)]) );
            kol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kol.setVisibility(View.GONE);
                }
            });
        }
    }
    private class MyListener1 implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            String value = "" + newVal;
            prodol.setText(getResources().getStringArray(R.array.numberPicker)[Integer.parseInt(value)-1]);
            prod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prod.setVisibility(View.GONE);
                }
            });
        }
    }
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LekChannel";
            String description = "Channel For AlarmLek";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("lekid", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

    }

}