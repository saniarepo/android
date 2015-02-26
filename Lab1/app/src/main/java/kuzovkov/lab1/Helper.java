package kuzovkov.lab1;

import android.content.Context;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.*;

/**
 * Created by sania on 2/26/2015.
 */
public class Helper {

    public static final String DATETIME_FORMAT = "hh:mm:ss dd/MM/yyyy";
    public static final String DATE_FORMAT = "dd/MM/yyyy";


    public static final Pattern emailPattern = Pattern.compile
            ("[a-zA-Z]{1}[a-zA-Z\\d\\u002E\\u005F]+@([a-zA-Z]+\\u002E){1,2}((net)|(com)|(org)|(ru))");

    public static final Pattern namePattern = Pattern.compile
            ("[a-zA-Z\\u0410-\\u044f]{3,20}");

    public static final Pattern datePattern = Pattern.compile
            ("[0-9]{1,2}.[0-9]{1,2}.[0-9]{4}");

    public static String getCurrDateTime(){
        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(DATETIME_FORMAT);
        String time = format1.format(date);
        return  time;
    }


    public static void showMessage(Context context, String text){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }


    public static boolean checkValid(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        return (matcher.matches())? true:false;
    }

    public static String convDate(String datestr){
        String[] parths = datestr.split("\\.");
        int day = Integer.parseInt(parths[0]);
        int month = Integer.parseInt(parths[1]);
        int year = Integer.parseInt(parths[2]);
        Calendar c = new GregorianCalendar(year,month-1,day);
        Date date = c.getTime();
        SimpleDateFormat format2 = new SimpleDateFormat(DATE_FORMAT);
        return format2.format(date);
    }

}
