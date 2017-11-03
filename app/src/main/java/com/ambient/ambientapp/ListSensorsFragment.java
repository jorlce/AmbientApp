package com.ambient.ambientapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Seven on 23/10/2017.
 */
public class ListSensorsFragment extends Fragment{

    public ListSensorsFragment() {
    }

    String[] sensors_text = new String[] { "SPA001", "SPA002", "SPA003" };
    String[] sensor_digits = new String[] { "1", "2", "3" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_sensors, container, false);
       // final ListView listview = (ListView) view.findViewById(R.id.listview_sensors);

       /* final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
*/
       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), R.layout.fragment_list_sensors, sensors_text);
        setListAdapter(adapter);*/
       return inflater.inflate(R.layout.list_frame_sensors, container, false);
    }

   /* private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //showSensor(position);
        new CustomToast(getActivity(), sensor_digits[(int) id]);
    }
*/

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {
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
            /*jdao = new SensorJSONImplementation();

            if (responseString.length() > 0) {
                Log.d("ListFrameSensor", "Mapping the result");

                listSensors = jdao.listaSensores(responseString);

                Iterator it = listSensors.iterator();
                SensorData nuevoSensor = null;



                TableLayout ll = (TableLayout) findViewById(R.id.table_list_sensors);
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
            }*/
        }

    }
}
