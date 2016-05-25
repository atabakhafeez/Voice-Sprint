package com.voicesprint.variable_j.voicesprint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button btnStart = (Button) findViewById(R.id.btnStartGame);
        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent startGameIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(startGameIntent);
            }
        });

        Button btnEndGame = (Button) findViewById(R.id.btnEndGame);
        btnEndGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent endIntent = new Intent(MainActivity.this, Quit.class);
                endIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                endIntent.putExtra("EXIT", true);
                startActivity(endIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}