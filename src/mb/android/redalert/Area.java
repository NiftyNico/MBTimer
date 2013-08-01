package mb.android.redalert;

import android.location.Location;
import android.util.Log;

public class Area {

	double radius;
	Location center;
	
	Area(double radius, double lat, double lon)
	{
		this.radius = radius;
		this.center = new Location("CENTER");
		this.center.setLatitude(lat);
		this.center.setLongitude(lon);
	}
    
    public Boolean atLocation(Location loc)
    {    	
    	return center.getLatitude() >= loc.getLatitude() - radius && 
    		   center.getLatitude() <= loc.getLatitude() + radius &&
    		   center.getLongitude() >= loc.getLongitude() - radius &&
    		   center.getLongitude() <= loc.getLongitude() + radius;

    }
}
