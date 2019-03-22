package ch.supsi.dti.isin.meteoapp.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import ch.supsi.dti.isin.meteoapp.R;

public class BackgroundTempMonitor extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BackgroundTempMonitor(String name) {
        super(name);
    }

    public BackgroundTempMonitor() {
        super("BTM");//BackgroundTempMonitor
    }


    public static void setBackgroundTempMonitor(Context context, boolean isOn) {

        Intent i = BackgroundTempMonitor.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (isOn)
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),
                    60000, pi); // 60'000 ms = 1 minuto
        else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, BackgroundTempMonitor.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("BTM", "Received an intent: " + intent);
        sendNotification(false, 30);
    }

    private void sendNotification(boolean isCold, int degrees) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Temp notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Test Channel Description");
            mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(isCold ? "Brr... " : "Muy caliente!")
                .setContentText("Warning! Temperature " + (isCold ? "below " : "over ") + degrees+" degrees")
                .setSmallIcon(isCold ? R.drawable.ic_cold : R.drawable.hot_sun)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        mNotificationManager.notify(0, mBuilder.build());
    }
}
