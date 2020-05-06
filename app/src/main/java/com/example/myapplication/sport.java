package com.example.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.textclassifier.ConversationActions;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.security.auth.callback.Callback;

import androidx.fragment.app.Fragment;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class sport extends Fragment implements Callback, AdapterView.OnItemClickListener, SensorEventListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AboutUs.OnFragmentInteractionListener mListener;
    Button search;
    EditText search_box;
    ListView listView;
    String token;
    ArrayAdapter<String> adapter;
    ArrayList<String> medList;
    JSONArray med_array;
    int type; // type 0 for autocomplete, type 1 for autocomplete

    private static double precision = 2;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private double deltaX = 0, deltaY = 0, deltaZ = 0;
    private double lastX = 0, lastY = 0, lastZ = 0;
    private int steps = 0, counter = 0, oldSteps = 0;
    ;
    private double height = 0.0;

    private long startTime = 0, startWatch = 0, stopWatch = 0;
    private int walked = 0;
    private double meters = 0, runningSpeed = 0;

    private ArrayList<Double> x;
    private ArrayList<Double> y;
    private ArrayList<Double> z;

    private ArrayList<Double> dataX;
    private ArrayList<Double> dataY;
    private ArrayList<Double> dataZ;

    private TextView stepsNumber, distance, speed, calories;
    private Chronometer watch;
    private Button start, stop;
    private EditText heightData;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public interface OnFragmentInteractionListener {
    }

  /*  public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }*/
public View onCreateView (LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState) {
    getActivity().setTitle("Pedometer");
    View vsp=inflater.inflate(R.layout.fragment_sport, container, false);
    stepsNumber = (TextView) vsp.findViewById(R.id.stepsNumber);
    distance = (TextView) vsp.findViewById(R.id.distance);
    speed = (TextView) vsp.findViewById(R.id.speed);
    calories = (TextView) vsp.findViewById(R.id.calories);
    watch = (Chronometer) vsp.findViewById(R.id.chronometer5);
    heightData = (EditText) vsp.findViewById(R.id.heightData);
    start = (Button) vsp.findViewById(R.id.start_button);
    stop = (Button) vsp.findViewById(R.id.stop_button);
    startTime = System.currentTimeMillis();

    x = new ArrayList<Double>();
    y = new ArrayList<Double>();
    z = new ArrayList<Double>();

    dataX = new ArrayList<Double>();
    dataY = new ArrayList<Double>();
    dataZ = new ArrayList<Double>();

    start.setOnClickListener(this);
    stop.setOnClickListener(this);

    stepsNumber.setEnabled(false);
    distance.setEnabled(false);
    speed.setEnabled(false);
    calories.setEnabled(false);
    stop.setEnabled(false);
    start.setEnabled(true);

    sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
        // success
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    } else {
        // error
    }
    return vsp ;
}
    @Override
    public void onClick(View v) {

        if (v.equals(start)) {

            watch.setBase(SystemClock.elapsedRealtime() + stopWatch);
            watch.start();

            startWatch = System.currentTimeMillis();

            height = Double.parseDouble(heightData.getText().toString());
            stepsNumber.setEnabled(true);
            distance.setEnabled(true);
            speed.setEnabled(true);
            calories.setEnabled(true);
            stop.setEnabled(true);
            start.setEnabled(false);
            heightData.setEnabled(false);

            resetValues();

            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        } else if (v.equals(stop)) {

            stepsNumber.setEnabled(false);
            distance.setEnabled(false);
            speed.setEnabled(false);
            calories.setEnabled(false);
            stop.setEnabled(false);
            start.setEnabled(true);
            heightData.setEnabled(true);

            stopWatch = watch.getBase() - SystemClock.elapsedRealtime();
            watch.stop();

            sensorManager.unregisterListener(this);

        }
    }


    public void resetValues() {

        watch.setBase(SystemClock.elapsedRealtime());
        meters = 0;
        steps = 0;
        runningSpeed = 0;

        distance.setText("0");
        stepsNumber.setText("0");
        speed.setText("0");
        calories.setText("0");
    }

  public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        deltaX = Math.abs(lastX - event.values[0]);
        deltaY = Math.abs(lastY - event.values[1]);
        deltaZ = Math.abs(lastZ - event.values[2]);


        // if the change is below 2, it is just noise
        if (deltaX > precision) {
            x.add((double) event.values[0]);
            lastX = event.values[0];
        } else {
            x.add(lastX);
        }
        if (deltaY > precision) {
            y.add((double) event.values[1]);
            lastY = event.values[1];
        } else {
            y.add(lastY);
        }
        if (deltaZ > precision) {
            z.add((double) event.values[2]);
            lastZ = event.values[2];
        } else {
            z.add(lastZ);
        }

        counter++;

        if (counter == 200) {
            if (System.currentTimeMillis() > startTime + 200) {
                walked = steps - oldSteps;
                startTime = System.currentTimeMillis();
                oldSteps = steps;

            }
            updateSteps();
            calculateData();
            counter = 0;

        }

    }

    public void calculateData() {

        // distance
        if (walked > 0 && walked < 2) {
            meters += height / 5.0;
        } else if (walked >= 2 && walked < 3) {
            meters += height / 4.0;
        } else if (walked >= 3 && walked < 4) {
            meters += height / 3.0;
        } else if (walked >= 4 && walked < 5) {
            meters += height / 2.0;
        } else if (walked >= 5 && walked < 6) {
            meters += height / 1.2;
        } else if (walked >= 6 && walked < 8) {
            meters += height;
        } else if (walked >= 8) {
            meters += height * 1.2;
        }

        // update speed
        runningSpeed = meters / (((System.currentTimeMillis() - startWatch) * 1000));
        speed.setText(Double.toString(runningSpeed));

        // update calories
        calories.setText(Double.toString((runningSpeed * 3.6) * 1.25));

        // update distance
        distance.setText(Double.toString(meters));
    }

    public void updateSteps() {

        // Filter Out the data
        for (int i = 0; i < 200; i += 4) {
            double sumX = 0, sumY = 0, sumZ = 0;
            for (int j = 0; j < 4 && i + j < 200; j++) {
                sumX += x.get(i + j);
                sumY += y.get(i + j);
                sumZ += z.get(i + j);
            }
            dataX.add(sumX / 4);
            dataY.add(sumY / 4);
            dataZ.add(sumZ / 4);

        }

        // clear the array list of input
        x.clear();
        y.clear();
        z.clear();

        // select the axis to work on
        int workingAxis = -1; // 0 = x, 1 = y, 2 = z

        double maxX = Collections.max(dataX), minX = Collections.min(dataX);
        double maxY = Collections.max(dataY), minY = Collections.min(dataY);
        double maxZ = Collections.max(dataZ), minZ = Collections.min(dataZ);

        double diffX = maxX - minX ;
        double diffY = maxY - minY;
        double diffZ = maxZ - minZ;

        double maxDiff = Math.max(diffX, Math.max(diffY, diffZ));

        if (maxDiff == diffX) {
            workingAxis = 0;
        } else if (maxDiff == diffY) {
            workingAxis = 1;
        } else if (maxDiff == diffZ) {
            workingAxis = 2;
        }

        // check how many steps now
        if (workingAxis == 0) {
            double average = (maxX + minX) / 2;
            for (int i = 0, j = 1; i < 49; i++, j++) {
                if (average > dataX.get(j) && average < dataX.get(i)) {
                    steps++;
                    displayCurrentsValues();
                }
            }

        } else if (workingAxis == 1) {
            double average = (maxY + minY) / 2;
            for (int i = 0, j = 1; i < 49; i++, j++) {
                if (average > dataY.get(j) && average < dataY.get(i)) {
                    steps++;
                    displayCurrentsValues();
                }
            }

        } else if (workingAxis == 2) {
            double average = (maxZ + minZ) / 2;
            for (int i = 0, j = 1; i < 49; i++, j++) {
                if (average > dataZ.get(j) && average < dataZ.get(i)) {
                    steps++;
                    displayCurrentsValues();
                }
            }

        } else {
            // error
        }

        // clear the array list of filter
        dataX.clear();
        dataY.clear();
        dataZ.clear();


    }


    public void displayCurrentsValues() {
        stepsNumber.setText(Integer.toString(steps));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }





}

