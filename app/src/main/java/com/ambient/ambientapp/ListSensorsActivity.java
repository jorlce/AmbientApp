package com.ambient.ambientapp;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListSensorsActivity extends AppCompatActivity {

    private SensorJSON jdao;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<SensorData> mylistSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ListSensorsActivity","Dentro de la Actividad");
        setContentView(R.layout.activity_list_sensors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        jdao = new SensorJSONImplementation();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mylistSensor = new ArrayList<SensorData>();


        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);


        //Llamada al Endpoint con RecyclerView
        HttpRequestTask httprequesttask = new HttpRequestTask();
        httprequesttask.execute();

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


        if (id == R.id.action_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Cierre de sesión")
                    .setMessage("¿Quiere cerrar la sesión?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(ListSensorsActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Log.d("ListSensorActivity", "Logout");
                            startActivity(intent);
                        }
                    }).create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
        private List<SensorData> mlistSensorData;

        // Store the context for easy access
        private Context mContext;

        // Pass in the sensors array into the constructor
        public MyRecyclerAdapter(Context context, List<SensorData> listSensorData) {
            mlistSensorData = listSensorData;
            mContext = context;
            if (mContext != null) {Log.d("MyRecyclerAdapter","Adaptador creado");}
        }

        // Easy access to the context object in the recyclerview
        private Context getContext() {
            return mContext;
        }
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case
            TextView tsensorLabel, tsensorLatitud, tsensorLongitud;

            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                tsensorLabel = (TextView) itemView.findViewById(R.id.sensorLabel);
                tsensorLatitud = (TextView) itemView.findViewById(R.id.sensorLatitud);
                tsensorLongitud = (TextView) itemView.findViewById(R.id.sensorLongitud);
                Log.d("MyRecyclerAdapter","ViewHolder");
                itemView.setOnClickListener(this);
            }

            // Handles the row being clicked
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition(); // gets item position
                // Check if an item was deleted, but the user clicked it before the UI removed it
                if (position != RecyclerView.NO_POSITION) {
                    SensorData sensor = mlistSensorData.get(position);
                    // We can access the data within the views
                    Intent intent = new Intent(ListSensorsActivity.this, ShowSensorActivity.class);
                    intent.putExtra("sensor", sensor.getSensorlabel());
                    intent.putExtra("latitud", String.valueOf(sensor.getLatitud()));
                    intent.putExtra("longitud", String.valueOf(sensor.getLongitud()));
                    intent.putExtra("frecuencia", String.valueOf(sensor.getFrecuencia()));
                    Log.d("WelcomeActivity","Antes cargar ListSensorActivity");
                    startActivity(intent);

                }
            }
        }

        // Usually involves inflating a layout from XML and returning the holder
        @Override
        public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View itemView = inflater.inflate(R.layout.sensorsdetails, parent, false);


            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(MyRecyclerAdapter.ViewHolder viewHolder, int position) {
            // Get the data model based on position
            SensorData sensor = mlistSensorData.get(position);

            // Set item views based on your views and data model
            viewHolder.tsensorLabel.setText("Sensor: " + sensor.getSensorlabel());
            Log.d("MyRecyclerAdapte.onBind", sensor.getSensorlabel());
            viewHolder.tsensorLatitud.setText("Latitud: " + String.valueOf(sensor.getLatitud()));
            Log.d("MyRecyclerAdapte.onBind", String.valueOf(sensor.getLatitud()));
            viewHolder.tsensorLongitud.setText("Longitud: " + String.valueOf(sensor.getLongitud()));
            Log.d("MyRecyclerAdapte.onBind", String.valueOf(sensor.getLongitud()));
        }

        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            return mlistSensorData.size();
        }

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        //private final ListSensorAdapter mAdapter;
        private final ProgressDialog dialog = new ProgressDialog(ListSensorsActivity.this);


        public HttpRequestTask() {

        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                final String url = "http://jorlce.ddns.net:8080/ambientService/listSensors";
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
            //List<SensorData> listSensors = null;

            if (responseString.length() > 0) {
                Log.d("ListSensorsActivity", "Mapping the result");
                Log.d("ListSensorsActivity",responseString);
                mylistSensor = jdao.listaSensores(responseString);

                if (this.dialog.isShowing()) { // if dialog box showing = true
                    this.dialog.dismiss(); // dismiss it
                }

                if ((mylistSensor != null) && !(mylistSensor.isEmpty())) {
                    Log.d("Prffffff",mylistSensor.get(0).getSensorlabel());



                     // specify an adapter (see also next example)
                    mAdapter = new MyRecyclerAdapter(getParent(), mylistSensor);
                    mRecyclerView.setAdapter(mAdapter);

                } else {
                    Log.e("ListSensorsActivity", "Lista Sensores Vacía");
                }


            } else {
                Toast.makeText(getApplicationContext(), "Sensores no encontrados",Toast.LENGTH_LONG).show();
            }
        }

    }
}
