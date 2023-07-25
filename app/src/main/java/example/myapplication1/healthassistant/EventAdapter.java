package example.myapplication1.healthassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import example.myapplication1.healthassistant.CalendarUtils;
import example.myapplication1.healthassistant.R;
import example.myapplication1.healthassistant.models.Event;

public class EventAdapter extends ArrayAdapter<Event>
{
    public EventAdapter(Context context, List<Event> events)
    {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent)
    {
        Event event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String eventTitle = event.getName() +" "+ CalendarUtils.formattedTime(event.getTime());
        eventCellTV.setText(eventTitle);
        return convertView;
    }

}