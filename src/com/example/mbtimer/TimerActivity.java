package com.example.mbtimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends Activity {

    Button clockIn;
    Button startTimer;
    Button gpsButton;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_activity_layout);
		
		String str = getIntent().getStringExtra(Constants.COMMAND);
		if (str == null)
			str = "null";
		
        clockIn = (Button) findViewById(R.id.adpButton);
        startTimer = (Button) findViewById(R.id.startTimerButton);
        gpsButton = (Button) findViewById(R.id.gpsButton);
        
        final GPSTracker gps = new GPSTracker(this);
        
        //Log.w("Debug", str);
        
        clockIn.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {	
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.ADP_PORTAL));
                startActivity(i);
            }
        });

        startTimer.setOnClickListener(new OnClickListener()
        {
            public void onClick(View view) {
            	stopAlarmService();
            	startAlarmService();
            }
        });
        
        gpsButton.setOnClickListener(new OnClickListener(){
            public void onClick(View view) {
                if(gps.isGPSEnabled)
                {
                	Log.w("Coordinates", "Latitude: " + gps.latitude + ", Longitude: " + gps.longitude);
                }
            }
        });
    }
    
    public void startAlarmService(){
  	  Intent intent = new Intent(TimerActivity.this, com.example.mbtimer.AlarmService.class);
  	  TimerActivity.this.startService(intent);
    }
    
    public void stopAlarmService(){
  	  	Intent intent = new Intent();
  	  	intent.setAction(AlarmService.ACTION);
  	  	intent.putExtra(Constants.RQS, AlarmService.RQS_STOP_SERVICE);
  	  	sendBroadcast(intent);
    }

}
