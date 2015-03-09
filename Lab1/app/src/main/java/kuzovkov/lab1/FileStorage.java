package kuzovkov.lab1;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sania on 2/26/2015.
 */
public class FileStorage {

    public static final String FILENAME = "data.dat";
    public static final String SEPARATOR = ";";
    public final static String PHOTO_FILENAME = "anketa.jpg";
    public final static String APP_IMAGE_FOLDER = "Анкета";

    public static void saveStrToFile(String str)throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(FILENAME);
        fos.write(str.getBytes());
        fos.close();
    }

    public static void saveArrStrToFile(String[] arr)throws FileNotFoundException, IOException {

        String str = StringArray2String(arr);
        File f = new File(FILENAME);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(str.getBytes());
        fos.close();
    }

    /*преобразование массива строк в строку*/
    public static String StringArray2String(String[] arr){
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < arr.length; i++ ){
            sb.append(arr[i]);
            if ( i < arr.length -1 ) sb.append(SEPARATOR);
        }
        return sb.toString();
    }

    /*преобразование строки в массив строк*/
    public static String[] String2StringArray(String str){
        String separator = SEPARATOR;
        String[] arr = str.split(separator);
        return arr;
    }

}