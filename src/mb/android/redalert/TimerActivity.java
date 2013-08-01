package mb.android.redalert;

import com.example.mbtimer.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TimerActivity extends FragmentActivity {

    Button clockIn;
    Button startTimer;
    Button gpsButton;
    Settings set;
    
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_activity_layout);
		
        clockIn = (Button) findViewById(R.id.adpButton);
        startTimer = (Button) findViewById(R.id.startTimerButton);
        gpsButton = (Button) findViewById(R.id.gpsButton);
        
        final GPSTracker gps = new GPSTracker(this);
        
        //Log.w("Debug", str);
       /* if(!set.validSettings)
        	setupSettings();*/

       /*gpsButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View view) 
            {
            	
                if(gps.isGPSEnabled)
                {
                	gps.getLocation();
                	gpsButton.setText("Latitude: " + gps.latitude
                			+ ", Longitude: " + gps.longitude
                			+ "\n" + Constants.Locations.MBHQ.atLocation(gps.getLocation()));
                } 
                else 
                {
                	gpsButton.setText("not enabled");
                }
            }
        });*/
    }
}
