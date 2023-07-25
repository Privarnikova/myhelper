package example.myapplication1.healthassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import example.myapplication1.healthassistant.data.DBContract;
import example.myapplication1.healthassistant.data.DatabaseHelper;
import example.myapplication1.healthassistant.models.WaterBalanceEntry;

public class StatActivity extends AppCompatActivity {
    ImageButton cal;
    ImageButton lek;
    ImageButton prof;
    ImageButton vod;
    TextView text;
    String name;
    private List<WaterBalanceEntry> entries;
    private RecyclerView recyclerView;
    private WaterBalanceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        cal = findViewById(R.id.imageView_cal);
        lek = findViewById(R.id.imageView_lek);
        prof = findViewById(R.id.imageView_prof);
        vod = findViewById(R.id.imageView_vod);


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


        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        entries = getWaterBalanceEntries();
        adapter = new WaterBalanceAdapter(entries);
        recyclerView.setAdapter(adapter);


    }

    private List<WaterBalanceEntry> getWaterBalanceEntries() {

        SQLiteDatabase db = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
           String[] projection = {
                DBContract.DBy.COLUMN_NAME_DATA,
                DBContract.DBy.COLUMN_NAME_PROG
        };
        Cursor cursor = db.query(
                DBContract.DBy.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        List<WaterBalanceEntry> entries = new ArrayList<>();
        int d =    cursor.getColumnIndex(DBContract.DBy.COLUMN_NAME_DATA);
        int p =   cursor.getColumnIndex(DBContract.DBy.COLUMN_NAME_PROG);

        while (cursor.moveToNext()) {
            String value_name = cursor.getString(d);
            String value_phone = cursor.getString(p);
            entries.add(new WaterBalanceEntry(value_name, value_phone));
        }
        cursor.close();

        return entries;
    }

}