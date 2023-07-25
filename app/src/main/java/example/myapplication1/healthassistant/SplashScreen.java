package example.myapplication1.healthassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {
    public static final String MY_PREFS_NAME="sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Boolean isUserRegistered = prefs.getBoolean("userRegistered",false);
        if (isUserRegistered) {

            startActivity(new Intent(this,prof.class));
        }
        else{

            startActivity(new Intent(this,StartActivity.class));
        }
        finish();
    }
}