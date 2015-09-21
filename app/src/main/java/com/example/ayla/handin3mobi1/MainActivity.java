package com.example.ayla.handin3mobi1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate views
        Button compass_button = (Button) findViewById(R.id.t1);
        Button lightsaber_button = (Button) findViewById(R.id.t2);
        Button geiger_button = (Button) findViewById(R.id.t3);

        //Attach listener
        compass_button.setOnClickListener(clickonbutton1);
        lightsaber_button.setOnClickListener(clickonbutton2);
        geiger_button.setOnClickListener(clickonbutton3);

        }

    View.OnClickListener clickonbutton1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //create explicit intent
            Intent compassintent = new Intent(MainActivity.this, CompassActivity.class);
            startActivity(compassintent);
        }
    };

    View.OnClickListener clickonbutton2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //create explicit intent
            Intent lightsaberintent = new Intent(MainActivity.this, LightsaberActivity.class);
            startActivity(lightsaberintent);
        }

    };
    View.OnClickListener clickonbutton3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //create explicit intent
            Intent geigerintent = new Intent(MainActivity.this, GeigerActivity.class);
            startActivity(geigerintent);
        }
    };
}




/*
private Menu _menu = null;

        @Override
        public boolean onCreateOptionsMenu(Menu menu)
        {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            _menu = menu;
            return true;

     private Menu getMenu()
        {
            //use it like this
            return _menu;
        }

       @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
           switch (id) {
               case R.id.t1:
                   Toast.makeText(this, "You pressed Compass", Toast.LENGTH_SHORT.show();
                   return true; //break omitted
               case R.id.t2:
                   Toast.makeText(this,"You pressed Lightsaber",Toast.LENGTH_SHORT.show();
                   return true; //break omitted
               case R.id.t3:
                   Toast.makeText(this,"You pressed Geiger",Toast.LENGTH_SHORT.show();
                   return true; //break omitted
               default:
                   return super.onOptionsItemSelected(item);
                   }
 */