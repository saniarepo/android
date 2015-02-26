package kuzovkov.lab1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class ResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String[] data = intent.getStringArrayExtra(FormActivity.FORM_DATA);

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
