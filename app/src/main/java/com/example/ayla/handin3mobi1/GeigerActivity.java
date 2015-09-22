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

public class GeigerActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sm;
    private Sensor accelerometer;
    private SoundPool soundPool;
    private int soundtick;
    private int vol = 1;
    private int idsoundtick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geiger);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
            Toast.makeText(this, "Using Lollipop or newer", Toast.LENGTH_LONG).show();
        }else{
            createOldSoundPool();
            Toast.makeText(this, "Using pre Lollipop", Toast.LENGTH_LONG).show();
        }

        soundtick = soundPool.load(this, R.raw.tick, 1);
        soundPool.setVolume(idsoundtick, vol, vol);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {//a sound was loaded succesfully)
                    if (sampleId == soundtick) {
                        idsoundtick = soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 1f);
                    }
                } else {
                    Toast.makeText(GeigerActivity.this, "Error loading sound: " + sampleId, Toast.LENGTH_LONG).show();
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
            float y = event.values[1];
            if (Math.round(y) == 0)  {
                soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 2);
            }

            if (Math.round(y) == 1)  {
                soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 1.7f);
            }

            if (Math.round(y) == 2)  {
                soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 1.5f);
            }

            if (Math.round(y) == 3)  {
                soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 1.3f);
            }

            if (Math.round(y) == 4)  {
                soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 1.1f);
            }

            if (Math.round(y) == 5)  {
                soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 0.9f);
            }
            if (Math.round(y) == 6)  {
                soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 0.8f);
            }
            if (Math.round(y) == 7)  {
                soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 0.7f);
            }
            if (Math.round(y) == 8)  {
                soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 0.6f);
            }
            if (Math.round(y) == 9)  {
                soundPool.play(soundtick, 0.2f, 0.2f, 1, -1, 0.5f);
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
        soundPool.stop(soundtick);
        soundPool.release();
    }
}
