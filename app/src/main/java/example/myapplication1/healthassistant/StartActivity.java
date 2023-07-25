package example.myapplication1.healthassistant;
import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import example.myapplication1.healthassistant.data.DBContract;
import example.myapplication1.healthassistant.data.DatabaseHelper;

public class StartActivity extends AppCompatActivity {
    private static final int PERMISSIONS_READ_PHONE_STATE = 1;
    TextView rostT;
    Button vh,reg;
    String deviceId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        rostT = findViewById(R.id.textView1);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_READ_PHONE_STATE);
        }


        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        vh = findViewById(R.id.vh);
        reg = findViewById(R.id.reg);


        vh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegActivity.class);
                intent.putExtra("id", deviceId);
                startActivity(intent);
                finish();
            }
        });

    }
}