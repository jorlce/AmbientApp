package com.ambient.ambientapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ShowSensorActivity extends AppCompatActivity {

    private String sensorlabel, sensorLatitud, sensorLongitud, sensorFrecuencia;
    private SensorJSON jdao;
    TableLayout tableSensor;

    TextView textSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tableSensor = (TableLayout) findViewById(R.id.tabla_sensor);

        jdao = new SensorJSONImplementation();

    // Receiving parameter having key value as 'sensor'
        Intent intent = getIntent();
        sensorlabel = intent.getStringExtra("sensor");
        sensorLatitud = intent.getStringExtra("latitud");
        sensorLongitud = intent.getStringExtra("longitud");
        sensorFrecuencia = intent.getStringExtra("frecuencia");

        //Calling Endpoint to receive sensor data
        HttpRequestTask httprequesttask = new HttpRequestTask();
        httprequesttask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_sensor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (sensorFrecuencia){
            case "1": {
                MenuItem menufreq = menu.findItem(R.id.action_freq);
                SubMenu subMenu = menufreq.getSubMenu();
                MenuItem subItem1 = subMenu.findItem(R.id.submenu_freq1);
                subItem1.setChecked(true);
                break;
            }
            case "2": {
                MenuItem menufreq = menu.findItem(R.id.action_freq);
                SubMenu subMenu = menufreq.getSubMenu();
                MenuItem subItem1 = subMenu.findItem(R.id.submenu_freq2);
                subItem1.setChecked(true);
                break;
            }
            case "3": {
                MenuItem menufreq = menu.findItem(R.id.action_freq);
                SubMenu subMenu = menufreq.getSubMenu();
                MenuItem subItem1 = subMenu.findItem(R.id.submenu_freq3);
                subItem1.setChecked(true);
                break;
            }
            case "4": {
                MenuItem menufreq = menu.findItem(R.id.action_freq);
                SubMenu subMenu = menufreq.getSubMenu();
                MenuItem subItem1 = subMenu.findItem(R.id.submenu_freq4);
                subItem1.setChecked(true);
                break;
            }
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int freq = 0;

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.submenu_freq1) {
            if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            sensorFrecuencia = "1";
            freq = 1;
            //Calling Endpoint to update sensor frequency data
            HttpRequestFreq httpRequestFreq = new HttpRequestFreq(freq);
            httpRequestFreq.execute();
            return true;
        }

        if (id == R.id.submenu_freq2) {
            if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            sensorFrecuencia = "2";
            freq = 2;
            //Calling Endpoint to update sensor frequency data
            HttpRequestFreq httpRequestFreq = new HttpRequestFreq(freq);
            httpRequestFreq.execute();
            return true;
        }

        if (id == R.id.submenu_freq3) {
            if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            sensorFrecuencia = "3";
            freq = 3;
            //Calling Endpoint to update sensor frequency data
            HttpRequestFreq httpRequestFreq = new HttpRequestFreq(freq);
            httpRequestFreq.execute();
            return true;
        }

        if (id == R.id.submenu_freq4) {
            if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            sensorFrecuencia = "4";
            freq = 4;
            //Calling Endpoint to update sensor frequency data
            HttpRequestFreq httpRequestFreq = new HttpRequestFreq(freq);
            httpRequestFreq.execute();
            return true;
        }

        if (id == R.id.action_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Cierre de sesión")
                    .setMessage("¿Quiere cerrar la sesión?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(ShowSensorActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Log.d("WelcomeActivity", "Logout");
                            startActivity(intent);
                        }
                    }).create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings({ "rawtypes" })
    public void addData(String campo, String valor, int position) {

        Log.d("Show.addData", campo + " " + String.valueOf(position));
        float fvalor;
        /** Create a TableRow dynamically **/
        TableRow rowSensor = new TableRow(this);

        /** Creating a TextView to add to the row **/
        TextView textCampo = new TextView(this);
        textCampo.setText(campo);
        textCampo.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        textCampo.setPadding(5, 5, 5, 5);
        textCampo.setBackgroundResource(R.drawable.cell_shape);
        textCampo.setTextColor(getResources().getColor(R.color.white));
        rowSensor.addView(textCampo); //


        /** Creating a TextView to add to the row **/
        TextView textValor = new TextView(this);
        textValor.setText(valor);
        textValor.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        textValor.setPadding(5, 5, 5, 5);
        textValor.setBackgroundResource(R.drawable.cell_shape);
        switch (campo) {
            case "CO":
                fvalor = Float.valueOf(valor);
                if ((fvalor > 11) && (fvalor < 30)) {
                    textValor.setTextColor(getResources().getColor(R.color.colorWarn));
                } else if (fvalor >= 30) {
                    textValor.setTextColor(getResources().getColor(R.color.colorAlarm));
                } else
                    textValor.setTextColor(getResources().getColor(R.color.colorOk));
                break;
            case "CO2":
                fvalor = Float.valueOf(valor);
                if ((fvalor > 1500) && (fvalor < 30000)) {
                    textValor.setTextColor(getResources().getColor(R.color.colorWarn));
                } else if (fvalor >= 30000) {
                    textValor.setTextColor(getResources().getColor(R.color.colorAlarm));
                } else
                    textValor.setTextColor(getResources().getColor(R.color.colorOk));
                break;
            case "Metano":
                fvalor = Float.valueOf(valor);
                if ((fvalor > 5000) && (fvalor < 10000)) {
                    textValor.setTextColor(getResources().getColor(R.color.colorWarn));
                } else if (fvalor >= 10000) {
                    textValor.setTextColor(getResources().getColor(R.color.colorAlarm));
                } else
                    textValor.setTextColor(getResources().getColor(R.color.colorOk));
                break;
            default:
                textValor.setTextColor(getResources().getColor(R.color.black));
        }

        rowSensor.addView(textValor); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tableSensor.addView(rowSensor, position, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

    }


    public void addRow(Medidor unMedidor) {

        /*// TEXTVIEW
        if(tv.getParent()!=null)
            ((ViewGroup)tv.getParent()).removeView(tv); // <- fix
        layout.addView(tv); //  <==========  ERROR IN THIS LINE DURING 2ND RUN
        // EDITTEXT*/
        int pos = 0;
        Log.d("Show.addRow",unMedidor.getSensorlabel());
        addData("Sensor",sensorlabel, pos++);
        addData("Latitud",sensorLatitud, pos++);
        addData("Longitud",sensorLongitud, pos++);
        addData("Temperatura", String.valueOf(unMedidor.getTemperatura()),pos++);
        addData("Humedad", String.valueOf(unMedidor.getHumedad()), pos++);
        addData("CO", String.valueOf(unMedidor.getNivelCO()), pos++);
        addData("CO2",String.valueOf(unMedidor.getNivelCO2()), pos++);
        addData("Metano",String.valueOf(unMedidor.getNivelMetano()), pos++);
        Log.d("Show.addRow",unMedidor.getTimelectura());
        addData("Ultima Lectura",unMedidor.getTimelectura(), pos);

    }

    private class HttpRequestFreq extends AsyncTask<String, Void, String> {

        private SensorData unSensor;

        public HttpRequestFreq(int freq) {
            this.unSensor = new SensorData();
            unSensor.setId(sensorlabel);
            unSensor.setLatitud(Float.parseFloat(sensorLatitud));
            unSensor.setLongitud(Float.parseFloat(sensorLongitud));
            unSensor.setFrecuencia(freq);

        }

        @Override
        protected String doInBackground(String... params) {
            String requestJson = "";
            String responseString = "";

            try {
                requestJson = jdao.addSensorData(unSensor);
                String url = "http://jorlce.ddns.net:8080/ambientService/cambiafreq";
                RestTemplate restTemplate = new RestTemplate();

                List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
                messageConverters.add(new FormHttpMessageConverter());
                messageConverters.add(new StringHttpMessageConverter());
               // messageConverters.add(new MappingJacksonHttpMessageConverter());
                restTemplate.setMessageConverters(messageConverters);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

                responseString = restTemplate.postForObject(url, entity, String.class);
                Log.d("ListSensorsActivity",responseString);
                return responseString;
            } catch (Exception e) {
                Log.e("ListFrameSensors", e.getMessage(), e);
            }
            return responseString;
        }

        /*@Override
        protected void onPostExecute(String responseString) {
            // Haríamos un refresh de la actividad actual.
            finish();
            startActivity(getIntent());
        }*/
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        //private final ListSensorAdapter mAdapter;
        private final ProgressDialog dialog = new ProgressDialog(ShowSensorActivity.this);


        public HttpRequestTask() {

        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                String url = "http://jorlce.ddns.net:8080/ambientService/consultSensor/" + sensorlabel;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                String responseString = restTemplate.getForObject(url, String.class);
                Log.d("ListSensorsActivity",responseString);
                return responseString;
            } catch (Exception e) {
                Log.e("ListFrameSensors", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPreExecute(){
            this.dialog.setMessage("Recibiendo datos...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(String responseString) {
            Medidor unMedidor = null;

            if ((responseString.length() > 0) && (!(responseString.equalsIgnoreCase("EMPTY")))){
                Log.d("ListSensorsActivity", "Mapping the result");
                Log.d("ListSensorsActivity",responseString);
                unMedidor = jdao.findSensorMeasure(responseString);

                if (this.dialog.isShowing()) { // if dialog box showing = true
                    this.dialog.dismiss(); // dismiss it
                }

                if (unMedidor != null) {
                    Log.d("Prffffff", unMedidor.getSensorlabel());
                    addRow(unMedidor);

                } else {
                    Toast.makeText(getApplicationContext(), "Sensor sin Lecturas",Toast.LENGTH_LONG).show();
                    Log.e("ListSensorsActivity", "Error mapping response");

                }
            } else {
                if (this.dialog.isShowing()) { // if dialog box showing = true
                    this.dialog.dismiss(); // dismiss it
                }
                Toast.makeText(getApplicationContext(), "Sensor sin Lecturas",Toast.LENGTH_LONG).show();
                Log.e("ListSensorsActivity", "Valores Sensor Vacíos");
            }
        }

    }

}
