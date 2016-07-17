package com.voicesprint.variable_j.voicesprint;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HighScoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HighScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HighScoreFragment extends Fragment implements View.OnClickListener {
    public static final String HIGH_SCORE_PREFS = "HighScorePrefsName";
    public static final String HIGH_SCORE = "HighScore";


    // the fragment initialization parameters

    private OnFragmentInteractionListener mListener;

//    private float firstUserScore;
//    private float secondUserScore;
//    private float thirdUserScore;
//    private static float[] highScores = new float[3];
//    private static String[] highScoreNames = new String[3];

    private HighScores highScores;

    public HighScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HighScoreFragment newInstance() {
//        HighScoreFragment fragment = new HighScoreFragment();
//        Bundle args = new Bundle();
//        args.putString(FINAL_SCORE, score);
//        fragment.setArguments(args);
        return new HighScoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_high_score, container, false);
        v.findViewById(R.id.score_screen_dismiss).setOnClickListener(this);
        updateHighScoresView(v);
        return v;
    }

    private void updateHighScoresView(View v) {
        SharedPreferences sharedPrefHighScore = getContext().getSharedPreferences(HIGH_SCORE_PREFS,
                Context.MODE_PRIVATE);
        String highScoreString = sharedPrefHighScore.getString(HIGH_SCORE, null);
        highScores = HighScores.fromJson(highScoreString);
        TableLayout table = (TableLayout) v.findViewById(R.id.high_score_table);

        if (highScores != null) {
            for (HighScores.Score score: highScores.getScores()) {
                TableRow row = (TableRow)LayoutInflater.from(getContext()).inflate(R.layout.attrib_row, null);
                ((TextView)row.findViewById(R.id.name)).setText(score.getScoreName());
                ((TextView)row.findViewById(R.id.score)).setText(Float.toString(score.getScoreNum()));
                table.addView(row);
            }
            table.requestLayout();
        }
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
        if (mListener != null) {
            mListener.onScoreScreenDismissed();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onScoreScreenDismissed();
    }
}
