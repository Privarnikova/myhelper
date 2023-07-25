package example.myapplication1.healthassistant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import example.myapplication1.healthassistant.models.WaterBalanceEntry;

public class WaterBalanceAdapter extends RecyclerView.Adapter<WaterBalanceAdapter.ViewHolder> {

    private List<WaterBalanceEntry> entries;

    public WaterBalanceAdapter(List<WaterBalanceEntry> entries) {
        this.entries = entries;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stat_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        WaterBalanceEntry entry = entries.get(position);
        holder.dateTextView.setText(entry.getDate());
        String s = String.valueOf(entry.getWaterAmount());
        holder.waterAmountTextView.setText(s +" мл");
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView waterAmountTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            waterAmountTextView = itemView.findViewById(R.id.waterAmountTextView);
        }
    }
}

