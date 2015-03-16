package kuzovkov.cursval;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public static Map<String,String> valutesMap = null; /*отображение задающее соответствие кодов и названий валют*/
    public String[] valutes = null; /*массив названий валют*/
    public static String codeValute = "R01235"; /*выбор валюты по умолчанию*/
    public static String nameValute = "Доллар США";
    public static final String CURSES_XML_DATA = "kyzovkov.cursval.CURSES_XML_DATA";
    public static final String SELECTED_VALUTE = "kyzovkov.cursval.SELECTED_VALUTE";
    public static final String SERVICE_ENCODE = "windows-1251";

    public ActionBarActivity instance = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getValuteCodes();
        fillDates();
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

    /*получение списка валют*/
    private void getValuteCodes(){
        if (isNetworkOk()){
            String url1 = CBR_ParserXML.urlValutes1;
            ((TextView)findViewById(R.id.text)).setText(getResources().getString(R.string.valutes_download));
            new getValutes().execute("get", url1, SERVICE_ENCODE);
        }else{
            Helper.showMessage(getApplicationContext(),getResources().getString(R.string.network_not_avail));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String ValuteNameSelected = valutes[position];
        for ( String code: valutesMap.keySet() ){
            if ( valutesMap.get(code).equals(ValuteNameSelected) ){
                codeValute = code;
                nameValute = valutesMap.get(code);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        codeValute = "R01235";
    }

    /*вложенный класс для получения списка валют с сайта центробанка*/
    private class getValutes extends HttpReq{

        @Override
        protected void onPostExecute(String result){
            String xmlContent = result;
            FileStorage.setItem(instance, "ValutesXmlContent", xmlContent);
            if ( result == null ){
                Helper.showMessage(getApplicationContext(), getResources().getString(R.string.load_valutes_fail));
                appEnd();
            }
            ((TextView)findViewById(R.id.text)).setText(getResources().getString(R.string.select_valutes));
            try{
                valutesMap = CBR_ParserXML.parseValutes(xmlContent);
                fillSpinner(valutesMap);

            }catch(Exception e){
                Helper.showMessage(getApplicationContext(), e.toString());
            }
        }

    }

    /*вложенный класс для получения динамики курса валюты с сайта центробанка*/
    private class getCurses extends HttpReq{

        @Override
        protected void onPostExecute(String result){

            if ( result == null ){
                Helper.showMessage(getApplicationContext(), getResources().getString(R.string.load_сurses_fail));
                appEnd();

            }else{
                showResult(result);
            }

        }

    }

    /*старт активности где отображается результат с передачей ей результата*/
    public void showResult(String xmlContent){
        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra(CURSES_XML_DATA, xmlContent);
        intent.putExtra(SELECTED_VALUTE, nameValute);
        startActivity(intent);
    }

    /*заполнение спинера списком валют*/
    public void fillSpinner(Map<String,String> valutesMap){
        valutes = new String[valutesMap.size()];
        int index = 0;
        for (String code: valutesMap.keySet()){
            valutes[index] = valutesMap.get(code); index++;
        }
        Spinner spin = (Spinner)findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valutes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

    }

    /*заполнение полей дат*/
    public void fillDates(){
        String endDate = Helper.getCurrDate();
        String beginDate = Helper.getDate(-10);
        ((TextView)findViewById(R.id.dateBegin)).setText(beginDate);
        ((TextView)findViewById(R.id.dateEnd)).setText(endDate);
    }

    /*проверка сети*/
    public boolean isNetworkOk(){
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return ( networkInfo != null && networkInfo.isConnected() )? true: false;
    }

    /*завершение работы*/
    public void appEnd(){
        this.finish();

    }

    /*обработчик кнопки ПОЛУЧИТЬ*/
    public void getCurses(View v){
        String dateBegin = ((TextView)findViewById(R.id.dateBegin)).getText().toString();
        String dateEnd = ((TextView)findViewById(R.id.dateEnd)).getText().toString();
        if (!Helper.checkDateInterval(dateBegin, dateEnd)){
            Helper.showMessage(getApplicationContext(),getResources().getString(R.string.dates_error));
            return;
        }
        String url = CBR_ParserXML.url +"date_req1=" + Helper.convDate(dateBegin) + "&date_req2=" + Helper.convDate(dateEnd) + "&VAL_NM_RQ=" + codeValute;

        Log.d("url:",url);
        if (isNetworkOk()){
            new getCurses().execute("get",url,SERVICE_ENCODE);
        }else{
            Helper.showMessage(getApplicationContext(),getResources().getString(R.string.network_not_avail));
        }
    }
}
