package com.example.ayla.handin3mobi1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView compass;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnometer;
    private float currentCompassAngle = 0;
    private float[] readingMagnometer = new float[3];
    private float[] readingAccelerometer = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compass = (ImageView) findViewById(R.id.compass);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometer);
        sensorManager.unregisterListener(this, magnometer);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] rotation = new float[9];
        float[] orientation = new float[3];

        if (sensorEvent.sensor == accelerometer) {
            readingAccelerometer[0] = sensorEvent.values[0];
            readingAccelerometer[1] = sensorEvent.values[1];
            readingAccelerometer[2] = sensorEvent.values[2];
        }

        if (sensorEvent.sensor == magnometer) {
            readingMagnometer[0] = sensorEvent.values[0];
            readingMagnometer[1] = sensorEvent.values[1];
            readingMagnometer[2] = sensorEvent.values[2];
        }


        sensorManager.getRotationMatrix(rotation, null, readingAccelerometer, readingMagnometer);
        sensorManager.getOrientation(rotation, orientation);
        float azimuthRadians = orientation[0];
        float azimuthDegrees = -(float) (Math.toDegrees(azimuthRadians) + 360) % 360;

        doAnimation(currentCompassAngle, azimuthDegrees, compass);
        currentCompassAngle = azimuthDegrees;
    }

    private void doAnimation(float currentCompassAngle, float azimuthDegrees, ImageView compassImage) {

        RotateAnimation rotateAnimation = new RotateAnimation(currentCompassAngle, azimuthDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(2500);
        rotateAnimation.setFillAfter(true);
        compassImage.startAnimation(rotateAnimation);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
