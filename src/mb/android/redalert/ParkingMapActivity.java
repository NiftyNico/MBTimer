package mb.android.redalert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.mbtimer.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
 
public class ParkingMapActivity extends FragmentActivity {
	
	TextView whereToPark;
	MapController myMapController;
    MapView map;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        
        /*map = (MapView) findViewById(R.id.map);
        myMapController = map.getController();
        myMapController.setCenter(new GeoPoint(0, 0));*/
        
        whereToPark = (TextView) findViewById(R.id.whereToPark);
        
        Settings s = new Settings();
        if(s.passColor < 0){
        	promptParkingChange();
        }
        
        if(s.passColor >= 0){
        	whereToPark.setText(s.whereToPark());
        } else {
        	whereToPark.setText("No parking information set");
        }
    }
    
    public void promptParkingChange(){
    	final AlertDialog d = new AlertDialog.Builder(this).create();
    	d.setTitle("No Pass Color Registered");
    	d.setMessage("Would you like to choose your pass color?");
    	d.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                         new Runnable(){
							@Override
							public void run() {
								SettingsActivity.setupPassColor(ParkingMapActivity.this);
								d.dismiss();
							}
                         }.run();
                    }
                });
    	d.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                         new Runnable(){
							@Override
							public void run() {
								d.dismiss();
							}
                         }.run();
                    }
                });
    	d.show();
    }
}