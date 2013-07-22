package com.example.mbtimer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.util.Log;

public class Settings implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 235836905978792519L;
	Boolean atWork;
	String arrival;
	String suggestedLunch;
	String takenLunch;
	
	
	public Settings(Boolean load)
	{
		if(load)
		{
			loadSerializedObject();
		}
		else
		{
			this.atWork = false;
		}
	}
	
	public void save()
	{
        try
        {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(Constants.SETTINGS_FILE))); //Select where you wish to save <span id="IL_AD3" class="IL_AD">the file</span>...
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
	
	public void loadSerializedObject()
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(Constants.SETTINGS_FILE)));
	        Object o = ois.readObject();
	        load((Settings)o);
	        ois.close();
		}
	    catch(Exception ex)
	    {
	    	Log.v("Serialization Read Error : ",ex.getMessage());
	        ex.printStackTrace();
	    }
	}
	
	public void load(Settings s)
	{
		atWork = s.atWork;
	}
}
