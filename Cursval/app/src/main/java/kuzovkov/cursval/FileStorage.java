package kuzovkov.cursval;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;


/**
 * Created by sania on 3/16/2015.
 */
public class FileStorage {

    private static final int BUFER_SIZE = 4096;
    private static final String SEPARATOR = ";";

    /*запись строки в храниличе под ключом key*/
    public static boolean setItem(ActionBarActivity activity, String key, String value){
        File file = new File(key);
        if (!file.exists() || !file.isFile()){
            try{
                file.createNewFile();
            }catch (Exception e){
                return false;
            }
        }
        try{
            FileOutputStream fos = activity.openFileOutput(key,0);
            fos.write(value.getBytes());
            fos.close();
        }catch(Exception e){
            return false;
        }
        return false;
    }

    /*чтение строки из хранилища по ключу key*/
    public static String getItem(ActionBarActivity activity,String key){
        File file = new File(key);
        byte[] buf = new byte[BUFER_SIZE];
        if (!file.exists() || !file.isFile())return null;
        try{
            FileInputStream fis = activity.openFileInput(key);
            fis.read(buf);
            String value = new String(buf);
            fis.close();
            return value;
        }catch(Exception e){
            return null;
        }

    }

    /*удаление записи из хранилища с ключом кеу (удаление файла с именем key)*/
    public static boolean clearItem(ActionBarActivity activity,String key){
        return activity.deleteFile(key);
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
