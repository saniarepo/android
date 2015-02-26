package kuzovkov.lab1;

import android.content.Context;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sania on 2/26/2015.
 */
public class Helper {

    public static final String DATE_FORMAT = "hh:mm::ss dd/MM/yyyy";

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

}
