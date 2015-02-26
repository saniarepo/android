package kuzovkov.lab1;

import android.content.Context;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.*;

/**
 * Created by sania on 2/26/2015.
 */
public class Helper {

    public static final String DATE_FORMAT = "hh:mm:ss dd/MM/yyyy";

    public static final Pattern emailPattern = Pattern.compile
            ("[a-zA-Z]{1}[a-zA-Z\\d\\u002E\\u005F]+@([a-zA-Z]+\\u002E){1,2}((net)|(com)|(org)|(ru))");

    public static final Pattern namePattern = Pattern.compile
            ("[a-zA-Z\\u0410-\\u044f]{3,20}");

    public static final Pattern datePattern = Pattern.compile
            ("[0-9]{1,2}[./-][0-9]{1,2}[./-][0-9]{2,4}");

    public static String getCurrDateTime(){
        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(DATE_FORMAT);
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

}
