package mb.android.redalert;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.example.mbtimer.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by nicolas.higuera on 7/17/13.
 */
import android.app.Service;
import android.content.BroadcastReceiver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

public class AlarmService extends Service {

	final static String ACTION = "NotifyServiceAction";
	final static String STOP_SERVICE = "";
	final static int RQS_STOP_SERVICE = 1;

	NotifyServiceReceiver notifyServiceReceiver;

	private static final int NOTIFICATION_ID = 1;
	private NotificationManager notificationManager;
	private Handler h;

    Date time;
	private Boolean repeatDaily = false;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		notifyServiceReceiver = new NotifyServiceReceiver();
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		h = new Handler();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION);
		registerReceiver(notifyServiceReceiver, intentFilter);
        final Settings s = new Settings();


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, s.alarmTime.getHours());
        calendar.set(Calendar.MINUTE, s.alarmTime.getMinutes());
        calendar.set(Calendar.SECOND, 0);

        this.time = calendar.getTime();
        this.repeatDaily = s.repeatDaily;
        
        if(repeatDaily){
            new Timer().scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    sendNotification("Clock into ADP", "Park: " + s.whereToPark(), "=)", false);
                }
            }, time, Constants.Times.DAY);
        } else {
            new Timer().schedule(new TimerTask(){
                @Override
                public void run(){
                    sendNotification("Clock into ADP", "Park: " + s.whereToPark(), "=)", true);
                }
            }, time);
        }

		// Send Notification
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		return super.onStartCommand(intent, flags, startId);
	}

	public void sendNotification(String ticket, String title, String content, boolean destroy) {
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		CharSequence NotificationTicket = ticket;
		CharSequence NotificationTitle = title;
		CharSequence NotificationContent = content;
		long when = System.currentTimeMillis();

		Notification notification = new Notification(R.drawable.ic_launcher,
				NotificationTicket, when);

		Context context = getApplicationContext();

		Intent notificationIntent;

		notificationIntent = new Intent(getBaseContext(), AndroidTabLayoutActivity.class);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, NotificationTitle,
				NotificationContent, contentIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(NOTIFICATION_ID, notification);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
		);
        if(destroy)
            onDestroy();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		this.unregisterReceiver(notifyServiceReceiver);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public class NotifyServiceReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			int rqs = arg1.getIntExtra("RQS", 0);
			if (rqs == RQS_STOP_SERVICE) {
				stopSelf();
			}
		}
	}
}
