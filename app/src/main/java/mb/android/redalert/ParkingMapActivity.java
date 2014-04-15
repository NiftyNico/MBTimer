package mb.android.redalert;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.maps.MapController;

public class ParkingMapActivity extends FragmentActivity {
	
	LatLng MBHQ = new LatLng(35.24730500465997, -120.64345210790634),
		   BUREAU = new LatLng(35.24678457240778, -120.64524918794632),
		   VONS = new LatLng(35.25007011212378, -120.64246237277985);
		   
	
	TextView whereToPark;
	//MapController myMapController;
    GoogleMap map;
    ProgressBar loader;
    LinearLayout l;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        
        //loader = (ProgressBar) findViewById(R.id.map_loader);
        l = (LinearLayout) findViewById(R.id.map_layout);
        map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        whereToPark = (TextView) findViewById(R.id.whereToPark);
        
        Settings s = new Settings(ParkingMapActivity.this);
        if(s.passColor < 0){
        	promptParkingChange();
        }
        
        if(s.passColor >= 0){
        	whereToPark.setText(s.whereToPark());

        	if(whereToPark.getText().equals("Onsite")){
                map.addMarker(new MarkerOptions().position(MBHQ).icon(BitmapDescriptorFactory.fromResource(s.passImage)));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(MBHQ,15));
        	} else if(whereToPark.getText().equals("Farm Bureau")){
                map.addMarker(new MarkerOptions().position(BUREAU).icon(BitmapDescriptorFactory.fromResource(s.passImage)));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(BUREAU,15));
        	} else {
                map.addMarker(new MarkerOptions().position(VONS).icon(BitmapDescriptorFactory.fromResource(s.passImage)));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(VONS,15));
        	}
    	
        } else {
        	whereToPark.setText("No parking information set");
            map.addMarker(new MarkerOptions().position(VONS));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(VONS,15));
        }
    }
    
    public void promptParkingChange(){
        final Settings s = new Settings(ParkingMapActivity.this);
    	
    	final Dialog dialog = new Dialog(ParkingMapActivity.this);
        dialog.setContentView(R.layout.simple_dialog);
        dialog.setTitle("No Pass Color Registered");

        //ImageView image = (ImageView) dialog.findViewById(R.drawable.adp_login_normal);
        final TextView text = (TextView) dialog.findViewById(R.id.simple_dialot_text);
        final Button cancel = (Button) dialog.findViewById(R.id.simple_dialog_cancel);
        final Button confirm = (Button) dialog.findViewById(R.id.simple_dialog_confirm);
        
        text.setText("Would you like to choose your pass color?");
        
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	SettingsActivity.setupPassColor(ParkingMapActivity.this, AndroidTabLayoutActivity.class);
            	dialog.dismiss();
            }
        });

        dialog.show();
    }
}