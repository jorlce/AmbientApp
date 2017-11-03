package com.ambient.ambientapp;

/**
 * Created by Seven on 26/10/2017.
 */
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class HttpRequestTask extends AsyncTask<Void, Void, String> {

    //EndPoints
    final String ENDPOINT_LOCALIZA_SENSOR = "/getSensor";
    final String ENDPOINT_CONSULTA_SENSOR = "/consultSensor/";
    final String ENDPOINT_LISTA_SENSORES = "/listSensors";
    final String ENDPOINT_ALTA_SENSOR = "/addSensor";
    final String ENDPOINT_BAJA_SENSOR = "/deleteSensor";

    @Override
    protected String doInBackground(Void... params) {
        try {
            final String url = "http://localhost:8080/ambientService" + ENDPOINT_LISTA_SENSORES;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //Greeting greeting = restTemplate.getForObject(url, Greeting.class);
            String responseString = restTemplate.getForObject(url,String.class);
            return responseString;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String responseString) {
       /* TextView greetingIdText = (TextView) findViewById(R.id.id_value);
        TextView greetingContentText = (TextView) findViewById(R.id.content_value);
        greetingIdText.setText(greeting.getId());
        greetingContentText.setText(greeting.getContent());*/
    }

}

