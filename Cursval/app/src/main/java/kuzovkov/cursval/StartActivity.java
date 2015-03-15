package kuzovkov.cursval;

import android.app.Activity;
import android.app.Notification;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Map;


public class StartActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    public Map<String, String> valutesMap = null;
    public  String[] valutes = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getValuteCodes();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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

    private void getValuteCodes(){
        String url1 = CBR_ParserXML.urlValutes1;
        new getValutes().execute("get", url1, "windows-1251");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView)findViewById(R.id.text)).setText(valutes[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ((TextView)findViewById(R.id.text)).setText(getResources().getString(R.string.valute_no_select));
    }

    private class getValutes extends HttpReq{

        @Override
        protected void onPostExecute(String result){
            String xmlContent = result;
            try{


                valutesMap = CBR_ParserXML.parseValutes(xmlContent);
                fillSpinner();
            }catch(Exception e){
                Helper.showMessage(getApplicationContext(), e.toString());
            }
        }

    }


    public void fillSpinner(){
        StringBuffer sb = new StringBuffer();
        valutes = new String[valutesMap.size()];
        int index = 0;
        for (String code: valutesMap.keySet()){
            sb.append(code).append(": ").append(valutesMap.get(code)).append("\n");
            valutes[index] = valutesMap.get(code); index++;
        }
        Spinner spin = (Spinner)findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valutes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

    }



}
