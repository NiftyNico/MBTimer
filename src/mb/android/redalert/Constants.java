package mb.android.redalert;

import java.io.File;
import java.math.BigInteger;

import android.os.Environment;

/**
 * Created by nicolas.higuera on 7/17/13.
 */
public final class Constants {
	public final static class Times {
		public static final int SECOND = 1000;
		public static final int MINUTE = 60 * SECOND;
		public static final int HOUR = 60 * MINUTE;
		public static final int HALF_HOUR = HOUR / 2;
		public static final int QUARTER_HOUR = HOUR / 4;
		public static final int DAY = HOUR * 24;
		public static final int WEEK = DAY * 7;
		public final static BigInteger EPOCH = new BigInteger("1373846400000"); 
	}
	
	public static final String LUNCH = "lunch";
	public static final String OFF = "off";
	public static final String LUNCH_SNOOZE = "snooze";

	public static final String COMMAND = "cmd";
	public static final String RQS = "RQS";
	
	public final static class ServiceActions {
		public static final int ADP = 0;
	}
	
	public final static class Locations {
		public final static Area MBHQ = new Area(0.01, 35.24730500465997,
				-120.64345210790634);
		public final static Area BUREAU = new Area(0.00001, 35.24678457240778,
				-120.64524918794632);
		public final static Area VONS = new Area(0.00001, 35.25007011212378,
				-120.64246237277985);
	}
}
