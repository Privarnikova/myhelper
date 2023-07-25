package example.myapplication1.healthassistant.models;

public class WaterBalanceEntry {
    private String date;
    private String waterAmount;

    public WaterBalanceEntry(String date, String waterAmount) {
        this.date = date;
        this.waterAmount = waterAmount;
    }

    public String getDate() {
        return date;
    }

    public String getWaterAmount() {
        return waterAmount;
    }
}

