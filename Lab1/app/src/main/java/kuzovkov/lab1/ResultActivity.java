package kuzovkov.lab1;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static kuzovkov.lab1.FileStorage.*;
import static kuzovkov.lab1.Helper.*;

public class ResultActivity extends ActionBarActivity {

    public String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String[] data = intent.getStringArrayExtra(FormActivity.FORM_DATA);
        /*получение данных с интента и заполнение текстовых полей*/
        ((TextView)findViewById(R.id.nameLastname)).setText(data[0]+" "+data[1]);
        ((TextView)findViewById(R.id.res_date_value)).setText(data[4]);
        ((TextView)findViewById(R.id.res_email_value)).setText(data[2]);
        if (data[3].equals("male")){
            ((ImageView)findViewById(R.id.avatar)).setImageResource(R.drawable.male);
            ((TextView)findViewById(R.id.registered)).setText(R.string.registered_male);
        }else{
            ((ImageView)findViewById(R.id.avatar)).setImageResource(R.drawable.female);
            ((TextView)findViewById(R.id.registered)).setText(R.string.registered_female);
        }

        ((TextView)findViewById(R.id.registered_value)).setText(data[5]);

        this.data = data;
    }

    /*обработчик кнопки СОХРАНИТЬ, запись данных в файл*/
    public void saveData(View v){
        /*запись в файл*/
        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(StringArray2String(this.data).getBytes());
            fos.close();
            showMessage(getApplicationContext(),getResources().getString(R.string.save_ok));
        }catch(Exception e){
            showMessage(getApplicationContext(),e.toString());
        }
    }

    /*обработчик кнопки ОЧИСТИТЬ, удаление файла с данными*/
    public void clearData(View v){
        if (!deleteFile(FILENAME)){
            showMessage(getApplicationContext(),getResources().getString(R.string.nothing_save));
        }else{
            showMessage(getApplicationContext(),getResources().getString(R.string.clear_ok));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
