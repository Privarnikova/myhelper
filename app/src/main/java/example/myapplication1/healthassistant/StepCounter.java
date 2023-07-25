package example.myapplication1.healthassistant;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class StepCounter implements SensorEventListener {

    private static final int STEP_THRESHOLD = 6; // Пороговое значение для определения шага
    private int stepCount = 0; // Счетчик шагов

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    public StepCounter(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void start() {
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        // Вычисление суммарного ускорения
        float acceleration = (float) Math.sqrt(x * x + y * y + z * z);

        // Если ускорение превышает пороговое значение, считаем это шагом
        if (acceleration > STEP_THRESHOLD) {
            stepCount++;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Метод не требуется для этого примера
    }

    public int getStepCount() {
        return stepCount;
    }
}
