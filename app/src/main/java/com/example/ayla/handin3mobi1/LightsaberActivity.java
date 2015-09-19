package com.example.ayla.handin3mobi1;

import android.annotation.TargetApi;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class LightsaberActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensormanager;
    SensorManager sm;
    private Sensor accelerometer;
    private SoundPool soundPool;
    private int soundhumhigh;
    private int soundhumlow;
    private int idsoundhumhigh;
    private int idsoundhumlow;
    private int vol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsaber);

        sensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        soundhumhigh = soundPool.load(this,R.raw.saberhum_hf,1);
        soundhumlow = soundPool.load(this,R.raw.saberhum_lf,1);
        soundPool.setVolume(idsoundhumhigh, vol, vol);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
            Toast.makeText(this, "Using Lollipop or never", Toast.LENGTH_LONG).show();
        }else{
            createOldSoundPool();
            Toast.makeText(this, "Using pre Lollipop", Toast.LENGTH_LONG.show();
        }
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

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void OnLoadComplete(SoundPool arg0, int arg1, int arg2) {
                if (arg2 == 0) {//a sound was loaded succesfully)
                    if (arg1 == soundhumhigh) {
                        idsoundhumhigh = soundPool.play(soundhumhigh, 0.2f, 0.2f, 1, -1, 1f);
                    }
                    if (arg1 == soundhumlow) {
                        idsoundhumlow = soundPool.play(soundhumlow, 1f, 1f, 1, -1, 1f);
                    }
                } else {
                    Toast.makeText(LightsaberActivity.this, "Error loading sound: " + arg1, Toast.LENGTH_LONG.show();
                }
            }
        };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        //We are not sensitive to changes in accuracy
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        soundPool.autoPause();
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