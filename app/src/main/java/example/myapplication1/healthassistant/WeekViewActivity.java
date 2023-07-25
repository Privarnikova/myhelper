package example.myapplication1.healthassistant;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import static example.myapplication1.healthassistant.CalendarUtils.daysInMonthArray;
import static example.myapplication1.healthassistant.CalendarUtils.daysInWeekArray;
import static example.myapplication1.healthassistant.CalendarUtils.monthYearFromDate;

import example.myapplication1.healthassistant.data.DBContract;
import example.myapplication1.healthassistant.data.DatabaseHelper;
import example.myapplication1.healthassistant.models.Event;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    ImageButton cal;
    ImageButton lek;
    ImageButton prof;
    ImageButton vod;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();

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
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
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
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), event);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    public void dailyAction(View view)
    {
        startActivity(new Intent(this, DailyCalendarActivity.class));
    }
}