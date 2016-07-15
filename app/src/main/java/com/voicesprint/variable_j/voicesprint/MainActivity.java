package com.voicesprint.variable_j.voicesprint;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * MainActivity class for the game
 * @author atabakh
 */
public class MainActivity extends FragmentActivity implements
        GameFragment.OnFragmentInteractionListener, HomeMenuFragment.OnFragmentInteractionListener,
HighScoreFragment.OnFragmentInteractionListener, PlayerScoreFragment.OnFragmentInteractionListener {

    public static final String HIGH_SCORE_PREFS = "HighScorePrefsName";

    private static final String HIGH_SCORE_STRING_SET = "HighScore";

    private static float finalScore;
//
//    private float firstUserScore;
//    private float secondUserScore;
//    private float thirdUserScore;

    private static float[] highScores = new float[3];
    private static String[] highScoreNames = new String[3];


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
        for (int i = 1; i <= highScores.length; i++) {
            highScores[i - 1] = sharedPrefHighScore.getFloat("position" + i, 0.0f);
            highScoreNames[i - 1] = sharedPrefHighScore.getString("position_" + i + "_name", null);
        }

        gameFragment = GameFragment.newInstance();
        homeMenuFragment = HomeMenuFragment.newInstance();
        highScoreFragment.newInstance();

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
        this.finalScore = finalScore;
        boolean high_score_scored = updateScores();

        playerScoreFragment = PlayerScoreFragment.newInstance(finalScore, high_score_scored);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                playerScoreFragment).commit();
    }

    private boolean updateScores() {
        boolean score_will_update = false;
        int position = 0;
        for (int i = 1; i <= highScores.length; i++) {
            if (finalScore <= highScores[i - 1]) {
                break;
            }
            if (finalScore > highScores[i - 1]) {
                position = i;
                score_will_update = true;
                break;
            }
        }
        if (score_will_update) {
            SharedPreferences sharedPrefHighScore = getSharedPreferences(HIGH_SCORE_PREFS,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefHighScore.edit();
            editor.putFloat("position" + position, finalScore);
            editor.commit();
        }
        return score_will_update;
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
    public void onHighScoreButtonPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                highScoreFragment).commit();
    }
}
