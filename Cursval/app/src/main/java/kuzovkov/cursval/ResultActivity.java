package kuzovkov.cursval;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class ResultActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String xmlContent = intent.getStringExtra(StartActivity.CURSES_XML_DATA);

        ((TextView)findViewById(R.id.valutaName)).setText(intent.getStringExtra(StartActivity.SELECTED_VALUTE));
        fillViewList(xmlContent);
    }

    /*преобразование строки с XML данными в массив строк, пригодный для вывода(парсинг)*/
    private String[] xml2stringArray(String xmlContent){
        String[] listData = null;
        try{
            List<CBR_ParserXML.Curs> curses = CBR_ParserXML.parseCurses(xmlContent);
            listData = new String[curses.size()];
            int row = 0;
            for (CBR_ParserXML.Curs curs: curses){
                listData[row] = "Дата: " + curs.getDate() + "\n" + "Курс: " + curs.getCurs()/curs.getNominal() + " рублей\n" + "Код валюты: " + curs.getValuta();
                row++;
            }

        }catch(Exception e){
            listData = new String[1];
            listData[0] = getResources().getString(R.string.parse_error);

        }
        return listData;
    }

    public void onListItemClick(ListView parent, View v, int position, long id){

    }

    /*заполнение списка данными из XML строки*/
    private void fillViewList(String xmlContent){
        String[] listCurses = xml2stringArray(xmlContent);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listCurses));
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
