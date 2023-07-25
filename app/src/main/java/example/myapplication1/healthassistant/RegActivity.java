
package example.myapplication1.healthassistant;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import example.myapplication1.healthassistant.data.DBContract;
import example.myapplication1.healthassistant.data.DatabaseHelper;

public class RegActivity extends AppCompatActivity {
    EditText user,pas, voz;
    NumberPicker rost, ves, gves;
    TextView rostT, vesT, gvesT;
    LinearLayout rostL, vesL, gvasL;
    ImageButton dall;
    String pol;
    ProgressBar progressBar;
    DatabaseHelper DatabaseHelper;
    public static final String MY_PREFS_NAME="sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        progressBar = findViewById(R.id.progress);
        user = findViewById(R.id.username);
        voz = findViewById(R.id.voz);
        pas = findViewById(R.id.pas);
        rost = findViewById(R.id.rostPicker);
        ves = findViewById(R.id.vesPicker);
        gves = findViewById(R.id.gvesPicker);
        rostT = findViewById(R.id.rostch);
        vesT = findViewById(R.id.vesch);
        gvesT = findViewById(R.id.gvesch);
        rostL = findViewById(R.id.rost);
        vesL = findViewById(R.id.ves);
        gvasL = findViewById(R.id.gves);
        dall = findViewById(R.id.dall);
        DatabaseHelper = new DatabaseHelper(this);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_m:
                        pol="M";
                        break;
                    case R.id.radio_g:
                        pol="Ж";
                        break;
                }
            }

        });

        vesL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ves.setVisibility(View.VISIBLE);
                ves.setMaxValue(150);
                ves.setMinValue(30);
                ves.setValue(50);
                ves.setWrapSelectorWheel(true);
                ves.setOnValueChangedListener(new RegActivity.MyListener());
            }
        });
        rostL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rost.setVisibility(View.VISIBLE);
                rost.setMaxValue(250);
                rost.setMinValue(120);
                rost.setValue(160);
                rost.setWrapSelectorWheel(true);
                rost.setOnValueChangedListener(new RegActivity.MyListener1());
            }
        });
        gvasL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gves.setVisibility(View.VISIBLE);
                gves.setMaxValue(150);
                gves.setMinValue(30);
                gves.setValue(50);
                gves.setWrapSelectorWheel(true);
                gves.setOnValueChangedListener(new RegActivity.MyListener2());
            }
        });



        dall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password, ves, gves, rost, vozr;
                username = String.valueOf(user.getText());
                password = String.valueOf(pas.getText());
                ves = String.valueOf(vesT.getText());
                gves = String.valueOf(gvesT.getText());
                rost = String.valueOf(rostT.getText());
                vozr = String.valueOf(voz.getText());
                if(vozr.equals("")|| username.equals("")||ves.equals("")||gves.equals("") || rost.equals("")||password.equals("")||pol.equals("")){
                    Toast.makeText(RegActivity.this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }
                if (!vozr.equals("") && !username.equals("") && !ves.equals("") && !gves.equals("") && !rost.equals("") && !password.equals("")&& !pol.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Boolean insert = DatabaseHelper.insertData(username, password, ves,gves,rost,pol,vozr);
                    if(insert == true) {
                        Toast.makeText(RegActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putBoolean("userRegistered", true);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), prof.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
    }

    private class MyListener implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            String value = "" + newVal;
            vesT.setText(value);
            ves.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ves.setVisibility(View.GONE);
                }
            });
        }
    }
    private class MyListener1 implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            String value = "" + newVal;
            rostT.setText(value);
            rost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rost.setVisibility(View.GONE);
                }
            });
        }
    }
    private class MyListener2 implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            String value = "" + newVal;
            gvesT.setText(value);
            gves.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gves.setVisibility(View.GONE);
                }
            });
        }
    }

}
