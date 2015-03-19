package kuzovkov.cursval;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.FileInputStream;


public class GraphResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_result);
        String xmlContent = loadItem("CURSES_XML_CONTENT");
        ((TextView)findViewById(R.id.graph)).setText(xmlContent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.as_table) {
            Intent intent = new Intent(this, TableResultActivity.class);
            startActivity(intent);
            this.finish();
            return true;
        }else if (id == R.id.as_list){
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
            this.finish();
            return true;
        }
        return true;
        //return super.onOptionsItemSelected(item);
    }

    /*чтение строки из файла сименем key*/
    public String loadItem(String key){
        try{
            FileInputStream fis = openFileInput(key);
            byte[] buf = new byte[fis.available()];
            fis.read(buf);
            String s = new String(buf);
            return s;
        }catch(Exception e){
            return null;
        }
    }
}
