package mb.android.redalert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SettingsActivity extends ListActivity {

    public class MainSettingsAdapter extends ArrayAdapter<String>{
    	
		ArrayList<String> entries;
        Context context;
        int layoutResourceId;
        
	    public MainSettingsAdapter(Context context, int layoutResourceId, ArrayList<String> entries) {    	
            super(context, layoutResourceId, entries);
	        this.entries=entries;
	    	this.context = context;
	    	this.layoutResourceId = layoutResourceId;
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            View item = inflater.inflate(layoutResourceId, parent, false);

	        TextView rowText = (TextView)item.findViewById(R.id.settingsRowParkingText); // title
	        ImageView rowImage = (ImageView)item.findViewById(R.id.settingsRowParkingImage); // thumb image

	        // Setting all values in listview
	        rowText.setText(entries.get(position));
	        
	        Drawable d;
	        
	        switch(position){
	        	case 0:
	        		d = item.getResources().getDrawable(R.drawable.lock);
	        		break;
	        	case 1:
	        		d = item.getResources().getDrawable(R.drawable.document);
	        		break;
	        	case 2:
	        		d = item.getResources().getDrawable(R.drawable.warning);
	        		break;
	        	case 3:
	        		d = item.getResources().getDrawable(R.drawable.recycle_bin);
	        		break;
	        	default:
	        		d = item.getResources().getDrawable(R.drawable.autobotlogo);
	        		break;
	        }
	        
	        rowImage.setImageDrawable(d);	      
	        return item;
	    }
    	
    }

    private class PassSettingsAdapter extends ArrayAdapter<String>{    
		ArrayList<String> colors;
        Context context;
        int layoutResourceId;
        
	    private PassSettingsAdapter(Context context, int layoutResourceId, ArrayList<String> entries) {    	
            super(context, layoutResourceId, entries);
	        this.colors=entries;
	    	this.context = context;
	    	this.layoutResourceId = layoutResourceId;
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            View item = inflater.inflate(layoutResourceId, parent, false);

	        TextView rowText = (TextView)item.findViewById(R.id.settingsRowParkingText); // title
	        ImageView rowImage = (ImageView)item.findViewById(R.id.settingsRowParkingImage); // thumb image

	        // Setting all values in listview
	        rowText.setText(colors.get(position));
	        
	        Drawable d;
	        
	        switch(position){
	        	case 0:
	        		d = item.getResources().getDrawable(R.drawable.bluepass);
	        		break;
	        	case 1:
	        		d = item.getResources().getDrawable(R.drawable.greypass);
	        		break;
	        	case 2:
	        		d = item.getResources().getDrawable(R.drawable.greenpass);
	        		break;
	        	case 3:
	        		d = item.getResources().getDrawable(R.drawable.purplepass);
	        		break;
	        	case 4:
	        		d = item.getResources().getDrawable(R.drawable.orangepass);
	        		break;
	        	case 5:
	        		d = item.getResources().getDrawable(R.drawable.goldpass);
	        		break;
	        	case 6:
	        		d = item.getResources().getDrawable(R.drawable.blackpass);
	        		break;
	        	default:
	        		d = item.getResources().getDrawable(R.drawable.autobotlogo);
	        		break;
	        }
	        
	        rowImage.setImageDrawable(d);	        
	        return item;
	    }
    }

    // This is the Adapter being used to display the list's data
    MainSettingsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        // storing string resources into Array
        ListView lv = getListView();

        mAdapter = new MainSettingsAdapter(this, R.layout.settings_row
                , new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.setting_options))));

        // Binding resources Array to ListAdapter
        lv.setAdapter(mAdapter);
        Log.w("# items", "" + lv.getCount());
        // listening to single list item on click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch(position){
                    case 0:
                        setupLoginDialog(SettingsActivity.this);
                        break;
                    case 1:
                    	setupPassColor(SettingsActivity.this, null);
                        break;
                    /*case 2:
                    	setupNotifications(SettingsActivity.this);
                        break;*/
                    case 2:
                    	confirmReset(SettingsActivity.this);
                    	break;
                    default:                 	
                        break;
                }
            }
        });       
    }

    public static void setupLoginDialog(final Context ctxt){
        Settings s = new Settings(ctxt);
    	
    	final Dialog dialog = new Dialog(ctxt);
        dialog.setContentView(R.layout.settings_login_dialog);
        dialog.setTitle("Edit your ADP username and password");

        //ImageView image = (ImageView) dialog.findViewById(R.drawable.adp_login_normal);
        final EditText user = (EditText) dialog.findViewById(R.id.username);
        final EditText pass = (EditText) dialog.findViewById(R.id.password);
        final Button cancel = (Button) dialog.findViewById(R.id.login_dialog_cancel);
        final Button confirm = (Button) dialog.findViewById(R.id.login_dialog_confirm);

        if(s.passWord.length() > 0 && s.userName.length() > 0){
        	user.append(s.userName);
        	pass.append(s.passWord);
        }
        
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getText().length() == 0 && pass.getText().length() == 0){
                    Toast toast = Toast.makeText(ctxt,
                            "Enter a Username and Password", Toast.LENGTH_LONG);
                    toast.show();
                } else if(user.getText().length() == 0){
                    Toast toast = Toast.makeText(ctxt,
                            "Enter a Username", Toast.LENGTH_LONG);
                    toast.show();
                } else if(pass.getText().length() == 0){
                    Toast toast = Toast.makeText(ctxt,
                            "Enter a Password", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Settings s = new Settings(ctxt);
                    s.userName = user.getText().toString();
                    s.passWord = pass.getText().toString();
                    
                    s.save(ctxt);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
    
    public static void setupPassColor(final Context ctxt, final Class<?> c){
        final Dialog dialog = new Dialog(ctxt);
        SettingsActivity a = new SettingsActivity();
        dialog.setContentView(R.layout.settings_change_parking_listview);
        dialog.setTitle("Select your badge");

        ListView l = (ListView) dialog.findViewById(R.id.settingsPassListView);
        PassSettingsAdapter adapter = a.new PassSettingsAdapter(ctxt, R.layout.settings_row, new ArrayList<String>(Arrays.asList(ctxt.getResources().getStringArray(R.array.pass_colors_string))));
        l.setAdapter(adapter);
        dialog.show();

        l.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Settings s = new Settings(ctxt);
		    
				s.passColor = position;
				switch(position){
	        		case 0:
	        			s.passImage = R.drawable.bluepassmarker;
	        			break;
	        		case 1:
	        			s.passImage = R.drawable.greypassmarker;
	        			break;
	        		case 2:
	        			s.passImage = R.drawable.greenpassmarker;
	        			break;
	        		case 3:
	        			s.passImage = R.drawable.purplepassmarker;
	        			break;
	        		case 4:
	        			s.passImage = R.drawable.orangepassmarker;
	        			break;
	        		case 5:
	        			s.passImage = R.drawable.goldpassmarker;
	        			break;
	        		case 6:
	        			s.passImage = R.drawable.blackpassmarker;
	        			break;
	        		default:
	        			s.passImage = R.drawable.autobotlogo;
	        			break;
				}
				
	        	s.save(ctxt);
	        	dialog.dismiss();
	        	if(c != null){
	        		Intent i = new Intent(ctxt, c);
	        		ctxt.startActivity(i);
	        	}
			}
        	
        });
    }

    public void setupNotifications(final Context ctxt){
        final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.setContentView(R.layout.notification_time_picker);
        dialog.setTitle("Select a time");

        TextView text = (TextView) dialog.findViewById(R.id.notification_text);
        final TimePicker tp = (TimePicker) dialog.findViewById(R.id.timeSelect);
        final CheckBox repeat = (CheckBox) dialog.findViewById(R.id.repeatWeeklyCheckbox);
        Button cancel = (Button) dialog.findViewById(R.id.cancelTimeSetButton);
        Button confirm = (Button) dialog.findViewById(R.id.confirmTimeSetButton);

        text.setText("Select when you would like to recieve reminders");
        
        cancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
        });

        confirm.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) { 
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
                calendar.set(Calendar.MINUTE, tp.getCurrentMinute());
                calendar.set(Calendar.SECOND, 0);
                
                Settings s = new Settings(ctxt);
                
                s.alarmTime = calendar.getTime();
                s.repeatDaily = repeat.isChecked();           
                s.save(ctxt);

                startService(new Intent(SettingsActivity.this, AlarmService.class));
                dialog.dismiss();
			}
        });

        dialog.show();
    }
    
    public void confirmReset(Context ctxt){
        final Settings s = new Settings(ctxt);
    	
    	final Dialog dialog = new Dialog(ctxt);
        dialog.setContentView(R.layout.simple_dialog);
        dialog.setTitle("Confirmation");

        //ImageView image = (ImageView) dialog.findViewById(R.drawable.adp_login_normal);
        final TextView text = (TextView) dialog.findViewById(R.id.simple_dialot_text);
        final Button cancel = (Button) dialog.findViewById(R.id.simple_dialog_cancel);
        final Button confirm = (Button) dialog.findViewById(R.id.simple_dialog_confirm);
        
        text.setText("Are you sure you want to delete your setting configurations?");
        
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	s.resetSettings(SettingsActivity.this);
            	dialog.dismiss();
            }
        });

        dialog.show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_credit_menu, menu);
        return true;
    	

    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.setContentView(R.layout.simple_dialog);
        dialog.setTitle("Image Credit");

        //ImageView image = (ImageView) dialog.findViewById(R.drawable.adp_login_normal);
        final TextView text = (TextView) dialog.findViewById(R.id.simple_dialot_text);
        final Button cancel = (Button) dialog.findViewById(R.id.simple_dialog_cancel);
        final Button confirm = (Button) dialog.findViewById(R.id.simple_dialog_confirm);
        
        text.setText("The images on this screen are used in compliance with the DryIcons "
        		+ "Free License Agreement.\nWould you like to visit this site?");
        
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dryicons.com"));
            	startActivity(browserIntent);
            	dialog.dismiss();
            }
        });

        dialog.show();
        
		return true;
    }
    
    
}