package example.myapplication1.healthassistant.alarm;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import example.myapplication1.healthassistant.MainActivity;
import example.myapplication1.healthassistant.R;
import example.myapplication1.healthassistant.WeekViewActivity;
import example.myapplication1.healthassistant.models.lekarstva;
import example.myapplication1.healthassistant.prof;
import example.myapplication1.healthassistant.vod;

public class    AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, vod.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"foxandroid")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Водный баланс")
                .setContentText("Пора выпить воды!")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,builder.build());


        Intent i1 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context,0,i1,0);

        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context,"lekid")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Лекарства")
                .setContentText("Пора выпить лекарства!")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent1);

        NotificationManagerCompat notificationManagerCompat1 = NotificationManagerCompat.from(context);
        notificationManagerCompat1.notify(12,builder1.build());



    }
}