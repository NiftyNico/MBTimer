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
    public static final String ADP_PORTAL = "https://portal.adp.com/wps/employee/employee.jsp";
    public static final String SETTINGS_FILE = "settings";
    
    public static final GPSLocation NORTH_MAIN_COORD = new GPSLocation(-120.64302563667297,35.24835281920167);
    public static final GPSLocation EAST_MAIN_COORD = new GPSLocation(-120.64207077026367,35.24653910810077);
    public static final GPSLocation WEST_MAIN_COORD = new GPSLocation(-120.64503192901611,35.24759053976853);
    public static final GPSLocation SOUTH_MAIN_COORD = new GPSLocation(-120.64416289329529,35.24596081487172);

    /*public static final GPSLocation NORTH_BUREAU_COORD = new GPSLocation();
    public static final GPSLocation EAST_BUREAU_COORD = new GPSLocation();
    public static final GPSLocation WEST_BUREAU_COORD = new GPSLocation();
    public static final GPSLocation SOUTH_BUREAU_COORD = new GPSLocation();*/
}
