package mb.android.redalert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import android.text.format.Time;
import android.util.Log;

public class Settings implements Serializable{
	
	public static final File SETTINGS_FILE = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath(), "Red_Alert_Settings");
	private static final long serialVersionUID = 235836905978792519L;
	
    String userName;
    String passWord;
	int passColor;
	Date alarmTime;
	Boolean repeatDaily;
	
	public Settings()
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SETTINGS_FILE));
	        Settings s = (Settings)ois.readObject();
	        ois.close();

            this.userName = s.userName;
            this.passWord = s.passWord;
			this.passColor = s.passColor;
			this.alarmTime = s.alarmTime;
			this.repeatDaily = s.repeatDaily;
		}
	    catch(Exception ex)
	    {
	    	Log.v("Serialization Read Error : ",ex.getMessage());
	        ex.printStackTrace();

            this.userName = "";
            this.passWord = "";
			this.passColor = -1;
			this.alarmTime = null;
			this.repeatDaily = false;
		}
	}
	
	public void save()
	{
        try
        {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SETTINGS_FILE));
           oos.writeObject(this);
           oos.flush(); 
           oos.close();
        }
        catch(Exception ex)
        {
           Log.v("Serialization Save Error : ",ex.getMessage());
           ex.printStackTrace();
        }
	}
	
	public Settings loadSerializedObject()
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SETTINGS_FILE));
	        Object o = ois.readObject();
	        ois.close();
	        return ((Settings)o);
		}
	    catch(Exception ex)
	    {
	    	Log.v("Serialization Read Error : ",ex.getMessage());
	        ex.printStackTrace();
	        return null;
	    }
	}
	
	public String whereToPark(){
		if(passColor < 0)
			return "No Pass Information Registered";
		BigInteger today = new BigInteger(Long.toString(Calendar.getInstance().getTime().getTime()));
		BigInteger sinceEpoch = today.subtract(Constants.Times.EPOCH);
		BigInteger week = new BigInteger(Integer.toString(Constants.Times.WEEK));

		int weekNo = sinceEpoch.divide(week).intValue();
		
		if(passColor > 4)
			return "Onsite";
		
		switch((passColor + weekNo) % 5){
			case 0:
				return "Onsite";
			case 1:
				return "Offsite";
			case 2:
				return "Farm Bureau";
			case 3:
				return "Offsite";
			case 4:
				return "Offsite";
			default:
				return "This should absolutely never ever happen";
		}
	}
}
