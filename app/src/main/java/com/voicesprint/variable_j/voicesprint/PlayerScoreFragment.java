package com.voicesprint.variable_j.voicesprint;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerScoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerScoreFragment extends Fragment implements View.OnClickListener {

    public static final String HIGH_SCORE_PREFS = "HighScorePrefsName";


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FINAL_SCORE = "finalScore";

    private float finalScore;
    private boolean updateScoreBool;

    private static float[] highScores = new float[3];
    private static String[] highScoreNames = new String[3];

    private boolean scoreWillUpdate;
    private int position;

    private OnFragmentInteractionListener mListener;

    public PlayerScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param finalScore Parameter 1.
     * @return A new instance of fragment PlayerScoreFragment.
     */
    public static PlayerScoreFragment newInstance(float finalScore) {
        PlayerScoreFragment fragment = new PlayerScoreFragment();
        Bundle args = new Bundle();
        args.putFloat(ARG_FINAL_SCORE, finalScore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            finalScore = getArguments().getFloat(ARG_FINAL_SCORE);
        }
        scoreWillUpdate = false;
        position = 0;
        initHighScoreValues();
        updateScores();
    }

    private void updateScores() {
        for (int i = 1; i <= highScores.length; i++) {
            if (finalScore <= highScores[i - 1]) {
                break;
            }
            if (finalScore > highScores[i - 1]) {
                position = i;
                scoreWillUpdate = true;
                break;
            }
        }

    }

    private void initHighScoreValues() {
        SharedPreferences sharedPrefHighScore = getContext().getSharedPreferences(HIGH_SCORE_PREFS,
                Context.MODE_PRIVATE);
        for (int i = 1; i <= highScores.length; i++) {
            highScores[i - 1] = sharedPrefHighScore.getFloat("position" + i, 0.0f);
            highScoreNames[i - 1] = sharedPrefHighScore.getString("position_" + i + "_name", null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_player_score, container, false);
        updateUI(v);
        return v;
    }

    private boolean updateUI(View v) {
        // Display the score
        TextView score_display = (TextView) v.findViewById(R.id.score_display);
        score_display.setText(Float.toString(finalScore));
        // Show a message
        TextView player_message = (TextView) v.findViewById(R.id.player_score_message);
        if (updateScoreBool) {
            player_message.setText(R.string.high_score_scored);
        } else {
            player_message.setText(R.string.high_score_not_scored);
        }

        v.findViewById(R.id.enter_player_name).setVisibility(scoreWillUpdate ? View.VISIBLE : View.GONE);
        v.findViewById(R.id.button_check_high_scores).setOnClickListener(this);

        return updateScoreBool;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_check_high_scores) {

            if (scoreWillUpdate) {
                EditText playerNameField = (EditText) view.findViewById(R.id.enter_player_name);
                String playerName = null;
                if (playerNameField != null) {
                    playerName = playerNameField.getText().toString();
                }
                mListener.onHighScoreButtonPressed(finalScore, playerName, position);
            } else {
                mListener.onHighScoreButtonPressed();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onHighScoreButtonPressed(float score, String name, int position);

        void onHighScoreButtonPressed();
    }
}
