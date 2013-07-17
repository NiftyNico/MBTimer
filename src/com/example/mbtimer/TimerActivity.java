package com.example.mbtimer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
public class TimerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_activity_layout);

        Button clockIn = (Button) findViewById(R.id.adpButton);
        Button startTimer = (Button) findViewById(R.id.startTimerButton);

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
            public void onClick(View v)
            {
                String url = "https://portal.adp.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timer, menu);
		return true;
	}

}
