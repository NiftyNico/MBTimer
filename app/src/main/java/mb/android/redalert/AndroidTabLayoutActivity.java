package mb.android.redalert;

import mb.android.redalert.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabLayoutActivity extends TabActivity {
	/* Called when the activity is first created. */
	TabHost tabHost;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tabHost = getTabHost();

		TabSpec mapspec = tabHost.newTabSpec("");
		// setting Title and Icon for the Tab
		mapspec.setIndicator("", getResources().getDrawable(R.drawable.icon_map_tab));
		Intent mapIntent = new Intent(this, ParkingMapActivity.class);
		mapspec.setContent(mapIntent);

		TabSpec adpspec = tabHost.newTabSpec("");
		adpspec.setIndicator("", getResources().getDrawable(R.drawable.icon_adp_tab));
		Intent adpIntent = new Intent(this, AdpActivity.class);
		adpspec.setContent(adpIntent);

		TabSpec settingspec = tabHost.newTabSpec("");
		settingspec.setIndicator("", getResources().getDrawable(R.drawable.icon_settings_tab));
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		settingspec.setContent(settingsIntent);

		// Adding all TabSpec to TabHost
		tabHost.addTab(mapspec); // Adding photos tab
		tabHost.addTab(adpspec); // Adding songs tab
		tabHost.addTab(settingspec);
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
	    return tabHost.getCurrentTab();
	}

	@Override
	protected void onSaveInstanceState (Bundle outState){
	    outState.putInt("LastTab", tabHost.getCurrentTab());
	}

}