package example.myapplication1.healthassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
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
import java.util.Calendar;
import java.util.List;

import example.myapplication1.healthassistant.data.DBContract;
import example.myapplication1.healthassistant.data.DatabaseHelper;
import example.myapplication1.healthassistant.models.WaterBalanceEntry;
import example.myapplication1.healthassistant.models.lekarstva;

public class MainActivity extends AppCompatActivity {
    ImageButton cal;
    private Calendar calendar;
    ImageButton lek;
    ImageButton prof;
    ImageButton vod;
    ImageButton dob;
    TextView del;
    List<String> medicationList;
    ArrayList<String> leks;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private ListView lekListView;
    lekarstva ttt;
    String[] medications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        getlistlek();
        leks = new ArrayList<>();
        cal = findViewById(R.id.imageView_cal);
        lek = findViewById(R.id.imageView_lek);
        prof = findViewById(R.id.imageView_prof);
        vod = findViewById(R.id.imageView_vod);
        dob = findViewById(R.id.blek);
        del = findViewById(R.id.del);
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
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DoblekActivity.class);
                startActivity(intent);
                finish();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> medic = getnamelist();
                medications = new String[medic.size()];
                for (int i = 0; i < medic.size(); i++) {
                    medications[i] = medic.get(i);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Выберите лекарство для удаления")
                        .setItems(medications, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Обработка выбора лекарства

                                String selectedMedication = medications[which];
                                delete(selectedMedication);
                                getlistlek();
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                  dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Удалить все", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteAllRecords();
                                getlistlek();
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }
    private void initWidgets()
    {
        lekListView = findViewById(R.id.lekListView);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        getlistlek() ;
    }

    private void setLekAdpater()
    {
       // ArrayList<lekarstva> lek = lekarstva.createlek();
      //  LekAdapter lekAdapter = new LekAdapter(getApplicationContext(), lek);
       // lekListView.setAdapter(lekAdapter);
    }

    private void delete(String name)
    {
        // Получение экземпляра SQLiteDatabase
        SQLiteDatabase db = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
        // Определение условия удаления (например, удалить записи, где поле "id" равно определенному значению)
        String selection = "name=?";
        String[] selectionArgs = { name };
        int deletedRows = db.delete("lek", selection, selectionArgs);
        // Проверка, сколько записей было удалено
        if (deletedRows > 0) {
            // Записи успешно удалены
        } else {
            // Нет записей, соответствующих условию удаления
        }
        // Закрытие базы данных
        db.close();
    }
    public void deleteAllRecords() {
        SQLiteDatabase db = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
        db.delete("lek", null, null);
        db.close();
    }

    private void getlistlek() {
        SQLiteDatabase db = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
        String[] columns = {"name","data","inter","prod", "kol","doz","t1","t2","t3","chek"};
        Cursor cursor = db.query(
                    DBContract.Lek.TABLE_NAME,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    null
        );
        ArrayList<lekarstva> lek = new ArrayList<>();
        int n =   cursor.getColumnIndex(DBContract.Lek.LEK_NAME);
        int dat =   cursor.getColumnIndex(DBContract.Lek.DATA);
        int in =   cursor.getColumnIndex(DBContract.Lek.INTER);
        int p =   cursor.getColumnIndex(DBContract.Lek.PROD);
        int k =   cursor.getColumnIndex(DBContract.Lek.KOL);
        int d =   cursor.getColumnIndex(DBContract.Lek.DOZ);
        int t1 =   cursor.getColumnIndex(DBContract.Lek.T1);
        int t2 =   cursor.getColumnIndex(DBContract.Lek.T2);
        int t3 =   cursor.getColumnIndex(DBContract.Lek.T3);
        int c =   cursor.getColumnIndex(DBContract.Lek.Chek);
        while (cursor.moveToNext()) {
            String name = cursor.getString(n);
            String data = cursor.getString(dat);
            String inter = cursor.getString(in);
            String prod = cursor.getString(p);
            String kol = cursor.getString(k);
            String doz = cursor.getString(d);
            String t11 = cursor.getString(t1);
            String t22 = cursor.getString(t2);
            String t33 = cursor.getString(t3);
            int chek = cursor.getInt(c);
            lek.add(new lekarstva(name,data,inter,prod,kol,doz,t11,t22,t33,chek));
            }
            cursor.close();
        LekAdapter lekAdapter = new LekAdapter(getApplicationContext(), lek);
        lekListView.setAdapter(lekAdapter);
    }

    private ArrayList<String>  getnamelist() {
        SQLiteDatabase db = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
        String[] columns = {"name"};
        Cursor cursor = db.query(
                DBContract.Lek.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
        int n = cursor.getColumnIndex(DBContract.Lek.LEK_NAME);
        while (cursor.moveToNext()) {
            String name = cursor.getString(n);
            leks.add(name);
        }
        cursor.close();
        return(leks);
    }

    public void onClick(View view) {
    }


}