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
    //TableRow rowSensor;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
                            Log.d("MainActivity", "Logout");
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


    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        //private final ListSensorAdapter mAdapter;
        private final ProgressDialog dialog = new ProgressDialog(ShowSensorActivity.this);


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
        protected void onPreExecute(){
            this.dialog.setMessage("Recibiendo datos...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(String responseString) {
            Medidor unMedidor = null;

            if (responseString.length() > 0) {
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
