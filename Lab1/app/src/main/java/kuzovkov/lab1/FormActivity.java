package kuzovkov.lab1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import static kuzovkov.lab1.Helper.*;


public class FormActivity extends ActionBarActivity {

    public final static String FORM_DATA = "kuzovkov.lab1.form_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
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

    private String[] getFormData(){
        String[] data = new String[6];
        data[0] = ((EditText)findViewById(R.id.editName)).getText().toString();
        data[1] = ((EditText)findViewById(R.id.editLastname)).getText().toString();
        data[2] = ((EditText)findViewById(R.id.editEmail)).getText().toString();
        data[3] = (((RadioButton)findViewById(R.id.male)).isChecked())? "male" : "female";
        data[4] = ((EditText)findViewById(R.id.editDate)).getText().toString();
        data[5] = getCurrDateTime();
        return data;
    }

    public void checkData(View v){
        String[] data =  getFormData();
        if (!checkValid(data[0],namePattern)){
            showMessage(getApplicationContext(), getResources().getString(R.string.not_valid_name));
            return;
        }

        if (!checkValid(data[1],namePattern)){
            showMessage(getApplicationContext(), getResources().getString(R.string.not_valid_lastname));
            return;
        }

        if (!checkValid(data[2],emailPattern)){
            showMessage(getApplicationContext(), getResources().getString(R.string.not_valid_email));
            return;
        }

        if (!checkValid(data[4],datePattern)){
            showMessage(getApplicationContext(), getResources().getString(R.string.not_valid_date));
            return;
        }

        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra(FORM_DATA, data);
        startActivity(intent);
    }
}
