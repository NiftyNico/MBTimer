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
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class TimerActivity extends Activity {

    final static private long ONE_SECOND = 1000;
    final static private long TWENTY_SECONDS = ONE_SECOND * 20;

    PendingIntent pi;
    BroadcastReceiver br;
    AlarmManager am;

    Button clockIn;
    Button startTimer;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_activity_layout);

        clockIn = (Button) findViewById(R.id.adpButton);
        startTimer = (Button) findViewById(R.id.startTimerButton);

        am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));

        setup();
        clockIn.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String url = "https://portal.adp.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        startTimer.setOnClickListener(new OnClickListener()
        {
            public void onClick(View view) {
                am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + TWENTY_SECONDS, pi );
            }
        });
    }

        private void setup() {
            br = new BroadcastReceiver() {
                @Override
                public void onReceive(Context c, Intent i) {
                    Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();
                }
            };
            registerReceiver(br, new IntentFilter("com.authorwjf.wakeywakey") );
            pi = PendingIntent.getBroadcast( this, 0, new Intent("com.authorwjf.wakeywakey"), 0 );
            am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
        }

        @Override
        protected void onDestroy() {
            am.cancel(pi);
            unregisterReceiver(br);
            super.onDestroy();
        }




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timer, menu);
		return true;
	}

}
