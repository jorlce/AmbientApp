package com.ambient.ambientapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ShowSensorActivity extends AppCompatActivity {

    private String sensorlabel, sensorLatitud, sensorLongitud;
    private SensorJSON jdao;
    TableLayout tableSensor;
    TableRow rowSensor;
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

        //Calling Endpoint to receive sensor data
        HttpRequestTask httprequesttask = new HttpRequestTask();
        httprequesttask.execute();

           /* if (getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE) {
                // If the screen is now in landscape mode, we can show the
                // dialog in-line with the list so we don't need this activity.
                finish();
                return;
            }*/


    }

    @SuppressWarnings({ "rawtypes" })
    public void addData(String campo, String valor) {

        float fvalor;
        /** Create a TableRow dynamically **/
        rowSensor = new TableRow(this);

        /** Creating a TextView to add to the row **/
        TextView textCampo = new TextView(this);
        textCampo.setText(campo);
        textCampo.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        textCampo.setPadding(5, 5, 5, 5);
        textCampo.setBackgroundResource(R.drawable.cell_shape);
        textCampo.setTextColor(getResources().getColor(R.color.white));
        rowSensor.addView(textCampo); // Adding textView to tablerow.

        /** Creating a TextView to add to the row **/
        TextView textValor = new TextView(this);
        textValor.setText(valor);
        textValor.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
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



    }


    public void addRow(Medidor unMedidor) {

        addData("Sensor",sensorlabel);
        addData("Latitud",sensorLatitud);
        addData("Longitud",sensorLongitud);
        addData("Temperatura", String.valueOf(unMedidor.getTemperatura()) );
        addData("Humedad", String.valueOf(unMedidor.getHumedad()));
        addData("CO", String.valueOf(unMedidor.getNivelCO()));
        addData("CO2",String.valueOf(unMedidor.getNivelCO2()));
        addData("Metano",String.valueOf(unMedidor.getNivelMetano()));
        addData("Ultima Lectura",String.valueOf(unMedidor.getTimelectura()));

        /** Create a TableRow dynamically **/
        /*rowSensor = new TableRow(this);

        *//** Creating a TextView to add to the row **//*
        textSensor = new TextView(this);
        textSensor.setText("Sensor");
        textSensor.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
               LayoutParams.WRAP_CONTENT));
        textSensor.setPadding(5, 5, 5, 5);
        textSensor.setBackgroundResource(R.drawable.cell_shape);
        textSensor.setTextColor(getResources().getColor(R.color.white));
       // LinearLayout Ll = new LinearLayout(this);
        *//*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);*//*
        //Ll.setPadding(10, 5, 5, 5);
        //Ll.addView(label,params);
        rowSensor.addView(textSensor); // Adding textView to tablerow.

        TextView textLat = new TextView(this);
        textLat.setText("Latitud");
        textLat.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        textLat.setPadding(5, 5, 5, 5);
        textLat.setBackgroundResource(R.drawable.cell_shape);
        textLat.setTextColor(getResources().getColor(R.color.white));
        rowSensor.addView(textLat); // Adding textView to tablerow.

        TextView textLong = new TextView(this);
        textLong.setText("Longitud");
        textLong.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        textLong.setPadding(5, 5, 5, 5);
        textLong.setBackgroundResource(R.drawable.cell_shape);
        textLong.setTextColor(getResources().getColor(R.color.white));
        rowSensor.addView(textLong); // Adding textView to tablerow.

        TextView textCO = new TextView(this);
        textCO.setText("CO");
        textCO.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        textCO.setPadding(5, 5, 5, 5);
        textCO.setBackgroundResource(R.drawable.cell_shape);
        textCO.setTextColor(getResources().getColor(R.color.white));
        rowSensor.addView(textCO); // Adding textView to tablerow.

        TextView textCO2 = new TextView(this);
        textCO2.setText("CO2");
        textCO2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        textCO2.setPadding(5, 5, 5, 5);
        textCO2.setBackgroundResource(R.drawable.cell_shape);
        textCO2.setTextColor(getResources().getColor(R.color.white));
        rowSensor.addView(textCO2); // Adding textView to tablerow.

        TextView textMetano = new TextView(this);
        textMetano.setText("Metano");
        textMetano.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        textMetano.setPadding(5, 5, 5, 5);
        textMetano.setBackgroundResource(R.drawable.cell_shape);
        textMetano.setTextColor(getResources().getColor(R.color.white));
        rowSensor.addView(textMetano); // Adding textView to tablerow.*/



    }


    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        //private final ListSensorAdapter mAdapter;


        public HttpRequestTask() {

        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                String url = "http://95.19.30.217:8080/ambientService/consultSensor/" + sensorlabel;
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
        protected void onPostExecute(String responseString) {
            Medidor unMedidor = null;

            if (responseString.length() > 0) {
                Log.d("ListSensorsActivity", "Mapping the result");
                Log.d("ListSensorsActivity",responseString);
                unMedidor = jdao.findSensorMeasure(responseString);
                if (unMedidor != null) {
                    Log.d("Prffffff", unMedidor.getSensorlabel());
                    addRow(unMedidor);

                } else {
                    new CustomToast(getParent(), "Sensores no encontrados");
                    Log.e("ListSensorsActivity", "Error mapping response");

                }
            } else {
                new CustomToast(getParent(), "Sensores no encontrados");
                Log.e("ListSensorsActivity", "Valores Sensor Vacíos");
            }
        }

    }

}
