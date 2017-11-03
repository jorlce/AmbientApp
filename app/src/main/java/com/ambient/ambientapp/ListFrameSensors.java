package com.ambient.ambientapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Seven on 13/09/2017.
 */
public class ListFrameSensors extends Fragment {

    private SensorJSON jdao;

    public ListFrameSensors() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewList = inflater.inflate(R.layout.list_frame_sensors, container, false);
        HttpRequestTask httpRequestTask = new HttpRequestTask(viewList);
        httpRequestTask.execute();
        return viewList;
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        jdao = new SensorJSONImplementation();

        final String url = "http://88.23.52.15:8080/ambientService/listSensors";
        Log.d("ListFrameSensor",url);
       // RestTemplate restTemplate = new RestTemplate();
        //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        //restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        Log.d("ListFrameSensor","Antes de la llamada");


        //String responseString = restTemplate.getForObject(url,String.class);

        // Set the Accept header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Add the Jackson message converter
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // Make the HTTP GET request, marshaling the response from JSON to an array of Events
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        //List<SensorData> listSensors  = responseEntity.getBody();
        String responseString = responseEntity.getBody();

        List<SensorData> listSensors = null;
        View viewList = inflater.inflate(R.layout.list_frame_sensors, container, false);
        if (responseString.length() > 0) {
            Log.d("ListFrameSensor", "Mapping the result");

            listSensors = jdao.listaSensores(responseString);

            Iterator it = listSensors.iterator();
            SensorData nuevoSensor = null;



            TableLayout ll = (TableLayout) viewList.findViewById(R.id.table_list_sensors);
            //Button sensorBtn;
            //String text1 = "SPA00";
            TableRow row = new TableRow(viewList.getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView tview = new TextView(viewList.getContext());
            tview.setText("Lista Sensores");
            row.addView(tview);
            ll.addView(row, 1);
            int cont = 2;

            while (it.hasNext()) {
                nuevoSensor = (SensorData) it.next();
                Log.d("ListFrameSensor", "Sensor: ");
                Log.d("ListFrameSensor", nuevoSensor.getSensorlabel());
                Log.d("ListFrameSensor", "Latitud: ");
                Log.d("ListFrameSensor", String.valueOf(nuevoSensor.getLatitud()));
                Log.d("ListFrameSensor", "Longtud: ");
                Log.d("ListFrameSensor", String.valueOf(nuevoSensor.getLongitud()));

                row = new TableRow(viewList.getContext());
                lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                Button sensorBtn = new Button(viewList.getContext());
                //String text2 = text1 + String.valueOf(i+1);
                sensorBtn.setText(nuevoSensor.getSensorlabel());

                sensorBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), ShowSensorActivity.class);
                        //intent.putExtra("index", index);
                        startActivity(intent);
                    }
                });

                row.addView(sensorBtn);

                ll.addView(row, cont);
                cont++;
            }

            //return inflater.inflate(R.layout.list_frame_sensors, container, false);
        } else {
            new CustomToast(getActivity(), "Sensores no encontrados");
        }
        return viewList;
    }

*/


    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        private View mview;

        public HttpRequestTask(View view) {

            mview = view;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                final String url = "http://88.23.52.15:8080/ambientService/listSensors";
                RestTemplate restTemplate = new RestTemplate();
                //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                String responseString = restTemplate.getForObject(url, String.class);
                return responseString;
            } catch (Exception e) {
                Log.e("ListFrameSensors", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String responseString) {
            List<SensorData> listSensors = null;
            //LayoutInflater inflater =
            jdao = new SensorJSONImplementation();

            if (responseString.length() > 0) {
                Log.d("ListFrameSensor", "Mapping the result");

                listSensors = jdao.listaSensores(responseString);

                Iterator it = listSensors.iterator();
                SensorData nuevoSensor = null;



                TableLayout ll = (TableLayout) mview.findViewById(R.id.table_list_sensors);
                //Button sensorBtn;
                //String text1 = "SPA00";
                TableRow row = new TableRow(getActivity());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView tview = new TextView(getActivity());
                tview.setText("Lista Sensores");
                row.addView(tview);
                ll.addView(row, 1);
                int cont = 2;

                while (it.hasNext()) {
                    nuevoSensor = (SensorData) it.next();
                    Log.d("ListFrameSensor", "Sensor: ");
                    Log.d("ListFrameSensor", nuevoSensor.getSensorlabel());
                    Log.d("ListFrameSensor", "Latitud: ");
                    Log.d("ListFrameSensor", String.valueOf(nuevoSensor.getLatitud()));
                    Log.d("ListFrameSensor", "Longtud: ");
                    Log.d("ListFrameSensor", String.valueOf(nuevoSensor.getLongitud()));

                    row = new TableRow(getActivity());
                    lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    Button sensorBtn = new Button(getActivity());
                    //String text2 = text1 + String.valueOf(i+1);
                    sensorBtn.setText(nuevoSensor.getSensorlabel());

                    sensorBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), ShowSensorActivity.class);
                            //intent.putExtra("index", index);
                            startActivity(intent);
                        }
                    });

                    row.addView(sensorBtn);

                    ll.addView(row, cont);
                    cont++;
                }

                //return inflater.inflate(R.layout.list_frame_sensors, container, false);
            } else {
                new CustomToast(getActivity(), "Sensores no encontrados");
            }
        }

    }
}
