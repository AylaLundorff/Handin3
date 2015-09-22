package com.example.ayla.handin3mobi1;

import android.annotation.TargetApi;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class LightsaberActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private Sensor accelerometer;
    private SoundPool soundPool;
    private int soundhumhigh;
    private int soundhumlow;
    private int idsoundhumhigh;
    private int idsoundhumlow;
    private int saberSwing;
    private int vol = 1;
    private float x, y, z;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsaber);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
            Toast.makeText(this, "Using Lollipop or never", Toast.LENGTH_LONG).show();
        }else{
            createOldSoundPool();
            Toast.makeText(this, "Using pre Lollipop", Toast.LENGTH_LONG).show();
        }

        soundhumhigh = soundPool.load(this,R.raw.hf,1);
        soundhumlow = soundPool.load(this,R.raw.lf,1);
        saberSwing = soundPool.load(this,R.raw.saberswing,1);
        soundPool.setVolume(idsoundhumhigh, vol, vol);


        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {//a sound was loaded succesfully)
                    if (sampleId == soundhumhigh) {
                        idsoundhumhigh = soundPool.play(soundhumhigh, 0.2f, 0.2f, 1, -1, 1f);
                    }
                    if (sampleId == soundhumlow) {
                        idsoundhumlow = soundPool.play(soundhumlow, 1f, 1f, 1, -1, 1f);
                    }
                } else {
                    Toast.makeText(LightsaberActivity.this, "Error loading sound: " + sampleId, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

        @TargetApi(21)
        protected void createNewSoundPool() {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }

        @SuppressWarnings("deprecation")
        protected void createOldSoundPool(){
            soundPool = new SoundPool(50, AudioManager.STREAM_MUSIC,0);
        }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {

            if (x == 0.0) {
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
            } else {
                if (Math.abs(event.values[1] - y) > 2 || Math.abs(event.values[0] - x) > 2 || Math.abs(event.values[2] - z) > 2) {
                    soundPool.play(saberSwing, 0.2f, 0.2f, 1, 1, 1.7f);
                }
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        //We are not sensitive to changes in accuracy
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        soundPool.autoResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this, accelerometer);
        soundPool.autoPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.stop(idsoundhumhigh);
        soundPool.stop(idsoundhumlow);
        soundPool.release();
    }
}