package mb.android.redalert;

import java.math.BigInteger;
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
}
