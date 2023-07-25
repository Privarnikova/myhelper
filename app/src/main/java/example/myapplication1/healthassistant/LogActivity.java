package example.myapplication1.healthassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import example.myapplication1.healthassistant.data.DatabaseHelper;
import example.myapplication1.healthassistant.data.DBContract;
import example.myapplication1.healthassistant.data.DatabaseHelper;

public class LogActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText user,pas;
    DatabaseHelper DatabaseHelper;
    ImageButton dall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        progressBar = findViewById(R.id.progress);
        user = findViewById(R.id.username);
        pas = findViewById(R.id.pas);
        dall = findViewById(R.id.dall);
        DatabaseHelper = new DatabaseHelper(this);
        dall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = String.valueOf(user.getText());
                password = String.valueOf(pas.getText());
                if (!username.equals("") && !username.equals("") && !password.equals("") ) {
                    progressBar.setVisibility(View.VISIBLE);
                    Boolean checkLog = DatabaseHelper.login(username, password);
                    if(checkLog == true) {
                        Toast.makeText(LogActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), prof.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LogActivity.this, "Необходима регистрация", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}