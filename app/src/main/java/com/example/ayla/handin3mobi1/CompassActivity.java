package com.example.ayla.handin3mobi1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView compassImage;
    private SensorManager sensormanager;
    private Sensor accelerometer;
    private Sensor magnometer;
    private float currentCompassAngle = 0;
    private float[] readingmagnometer = new float[3];
    private float[] readingaccelerometer = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compassImage = (ImageView) findViewById(R.id.compass);
        sensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = sensormanager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        //We are not sensitive to changes in accuracy
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensormanager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensormanager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensormanager.unregisterListener(this, accelerometer);
        sensormanager.unregisterListener(this, magnometer);
    }

    private void doAnimation(float from, float to, View rotateme){

        RotateAnimation ra = new RotateAnimation(
                from,
                to,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(200);
        ra.setFillAfter(true);
        rotateme.startAnimation(ra);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] rotation = new float[9];
        float[] orientation = new float[3];

        if (event.sensor == accelerometer) {
            readingaccelerometer[0] = event.values[0];
            readingaccelerometer[1] = event.values[1];
            readingaccelerometer[2] = event.values[2];
        }
        if (event.sensor == magnometer) {
            readingaccelerometer[0] = event.values[0];
            readingaccelerometer[1] = event.values[1];
            readingaccelerometer[2] = event.values[2];
        }

        sensormanager.getRotationMatrix(rotation, null, readingaccelerometer, readingmagnometer);
        sensormanager.getOrientation(rotation, orientation);
        float azimuthRadians = orientation[0];
        float azimuthDegrees = -(float) (Math.toDegrees(azimuthRadians) + 360) % 360;

        doAnimation(currentCompassAngle, azimuthDegrees, compassImage);
        currentCompassAngle = azimuthDegrees;
    }
}

