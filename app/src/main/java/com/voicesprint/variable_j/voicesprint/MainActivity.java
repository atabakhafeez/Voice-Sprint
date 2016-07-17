package com.voicesprint.variable_j.voicesprint;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * MainActivity class for the game
 * @author atabakh
 */
public class MainActivity extends FragmentActivity implements
        GameFragment.OnFragmentInteractionListener, HomeMenuFragment.OnFragmentInteractionListener,
HighScoreFragment.OnFragmentInteractionListener, PlayerScoreFragment.OnFragmentInteractionListener {

    static final String TAG = "MainActivity";

    public static final String HIGH_SCORE_PREFS = "HighScorePrefsName";
    public static final String HIGH_SCORE = "HighScore";

    private HighScores highScores;

    GameFragment gameFragment;
    HomeMenuFragment homeMenuFragment;
    HighScoreFragment highScoreFragment;
    PlayerScoreFragment playerScoreFragment;

    /**
     * Overridden onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        SharedPreferences sharedPrefHighScore = getSharedPreferences(HIGH_SCORE_PREFS,
                Context.MODE_PRIVATE);
        String highScoreString = sharedPrefHighScore.getString(HIGH_SCORE, null);
        highScores = HighScores.fromJson(highScoreString);
        if (highScores == null) {
            Log.d("MAIN", "highScores is null");
        }

        gameFragment = GameFragment.newInstance();
        homeMenuFragment = HomeMenuFragment.newInstance();
        highScoreFragment = HighScoreFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                homeMenuFragment).commit();
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
    public void onGameOver(float finalScore) {
        playerScoreFragment = PlayerScoreFragment.newInstance(finalScore);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                playerScoreFragment).commit();
    }

    @Override
    public void onGameStartButtonPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                gameFragment).addToBackStack(null).commit();
    }

    @Override
    public void onScoreScreenDismissed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                homeMenuFragment).commit();
    }

    @Override
    public void onHighScoreButtonPressed(float score, String name) {
        updateScores(score, name);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                highScoreFragment).commit();
    }

    private void updateScores(float score, String name) {
        if (highScores == null) {
            highScores = new HighScores();
        }
        highScores.addScore(name, score);
        Log.d(TAG, highScores.toJson());

        SharedPreferences sharedPrefHighScore = getSharedPreferences(HIGH_SCORE_PREFS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefHighScore.edit();
        editor.putString(HIGH_SCORE, highScores.toJson());
        editor.commit();
    }

    @Override
    public void onHighScoreButtonPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                highScoreFragment).commit();
    }
}
