package com.voicesprint.variable_j.voicesprint;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * MainActivity class for the game
 * @author atabakh
 */
public class MainActivity extends FragmentActivity implements
        GameFragment.OnFragmentInteractionListener, HomeMenuFragment.OnFragmentInteractionListener {

    GameFragment gameFragment;
    HomeMenuFragment homeMenuFragment;

    /**
     * Overridden onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        gameFragment = GameFragment.newInstance();
        homeMenuFragment = HomeMenuFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                homeMenuFragment).commit();
//
//        Button btnStart = (Button) findViewById(R.id.btnPlayGame);
//        btnStart.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
//                        gameFragment).commit();
//            }
//        });
    }

    /**
     * Overridden onCreateOptionsMenu method
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    /**
     * Overridden onOptionsItemSelected method
     * @param item
     * @return
     */
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

    @Override
    public void onGameOver() {

    }

    @Override
    public void onGameStartButtonPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                gameFragment).addToBackStack(null).commit();
    }
}
