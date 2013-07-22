package com.example.mbtimer;

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

		// Send Notification
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		sendNotification(2 * Constants.SECOND);
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void sendNotification(int time)
	{
		Runnable myrunnable = new Runnable() {
			   public void run() {
				   notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				   CharSequence NotificationTicket = "Notification";
				   CharSequence NotificationTitle = "Notification";
				   CharSequence NotificationContent = "Test";
				   long when = System.currentTimeMillis();

				   Notification notification = new Notification(R.drawable.ic_launcher, NotificationTicket, when);

				   Context context = getApplicationContext();

				   Intent notificationIntent = new Intent(getBaseContext(), TimerActivity.class);
				   PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
				 
				   notification.setLatestEventInfo(context, NotificationTitle, NotificationContent, contentIntent); 
				   notification.flags |= Notification.FLAG_AUTO_CANCEL;
				   
				   notificationManager.notify(NOTIFICATION_ID, notification);
				   notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			};
		};
		h.postDelayed(myrunnable, time);

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
