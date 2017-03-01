package com.example.maxim.musify.helpers;

/**
 * Created by ${Maxim} on ${07.01.2017}.
 */

public class TimeManager {

    public static final long  MILLISECONDS_IN_MONTH = 2628000000l;
    public static final int MILLISECONDS_IN_WEEK = 604800000;
    public static final int MILLISECONDS_IN_DAY = 86400000;
    public static final int MILLISECONDS_IN_HOUR = 3600000;
    public static final int MILLISECONDS_IN_MINUTE= 60000;
    public static final int MILLISECONDS_IN_SECOND= 1000;

    public static final char SEPARATING_CHAR  = ':';

    public static String MillisecondsToTimeFormat(int msc,int v_days,int v_hours,int v_minutes, int v_seconds ,  int v_milliseconds){

        boolean view_days = BooleanAdapter(v_days);
        boolean view_hours = BooleanAdapter(v_hours);
        boolean view_minutes= BooleanAdapter(v_minutes);
        boolean view_seconds= BooleanAdapter(v_seconds);
        boolean view_milliseconds= BooleanAdapter(v_milliseconds);

        int days = view_days? GetDays(msc) : 0;
        int hours = view_hours? GetHours(msc-days*MILLISECONDS_IN_DAY) : 0;
        int minutes = view_minutes? GetMinutes(msc - hours*MILLISECONDS_IN_HOUR) :0;
        int seconds = view_seconds? GetSeconds(msc -minutes*MILLISECONDS_IN_MINUTE):0;
        int milliseconds = view_milliseconds? msc - seconds*MILLISECONDS_IN_SECOND:0;

        String result = "";
        if(view_days) result+=String.valueOf(days);
        result+=SEPARATING_CHAR;
        if(view_hours) result+=String.valueOf(hours);
        result+=SEPARATING_CHAR;
        if(view_minutes) result+=AddMissingZeros(minutes,2);
        result+=SEPARATING_CHAR;
        if(view_seconds) result+= AddMissingZeros(seconds,2);
        result+=SEPARATING_CHAR;
        if(view_milliseconds) result+= AddMissingZeros(milliseconds,4);

        return RemoveExtraSeparatingChars(result,SEPARATING_CHAR);

    }


    public static int GetWeeks(int msc){
        int weeks = (int)msc/MILLISECONDS_IN_WEEK;
        return weeks;
    }
    public static int GetDays(int msc){
        int days = (int)msc/MILLISECONDS_IN_DAY;
        return days;
    }
    public static int GetHours(int msc){
        int hours = (int)msc/MILLISECONDS_IN_HOUR;
        return hours;
    }
    public static int GetMinutes(int msc){
        int minutes = (int)msc/MILLISECONDS_IN_MINUTE;
        return minutes;
    }
    public static int GetSeconds(int msc){
        int seconds = (int)msc/MILLISECONDS_IN_SECOND;
        return seconds;
    }

    private static boolean BooleanAdapter(int value){
        return value!=0?true:false;
    }
    private static String AddMissingZeros(int value, int numberOfChars){
        String stringValue;
        String result = "";
        stringValue = String.valueOf(value);
        if(stringValue.length()<numberOfChars)
        for (int i = 0; i <numberOfChars- stringValue.length(); i++) {
             result+='0';
        }
        result+=stringValue;
        return result;
    }
    private static String RemoveExtraSeparatingChars(String string,char SEPARATING_CHAR){
        String result = "";
        String[] strings = string.split(String.valueOf(SEPARATING_CHAR));
        for (int i = 0; i < strings.length; i++) {
            if(!strings[i].equals("")) {
                result += strings[i];
                if(i!=strings.length-1)
                    result+=SEPARATING_CHAR;
            }


        }
        return result;
    }


}
