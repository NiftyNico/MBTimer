package com.example.mbtimer;

/**
 * Created by nicolas.higuera on 7/17/13.
 */
public final class Constants {
    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int HALF_HOUR = HOUR / 2;
    public static final int QUARTER_HOUR = HOUR / 4;
    public static final int DAY = HOUR * 24;
    public static final int WEEK = DAY * 7;
    
    public static final String LUNCH = "lunch";
    public static final String OFF = "off";
    public static final String LUNCH_SNOOZE = "snooze";
    
    public static final String COMMAND = "cmd";
    public static final String RQS = "RQS";
    public static final String ADP_PORTAL = "https://portal.adp.com/";
    public static final String SETTINGS_FILE = "settings";
}
