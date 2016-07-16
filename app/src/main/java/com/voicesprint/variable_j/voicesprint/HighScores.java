package com.voicesprint.variable_j.voicesprint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by atabakh on 17/07/2016.
 */
public class HighScores {

    private List<Score> scores;

    public HighScores() {
        scores = new ArrayList<>();
    }

    public void addScore(String name, float scoreNum) {
        Score newScore = new Score(scoreNum, name);
        scores.add(newScore);
        Collections.sort(scores);
        if (scores.size() > 3) {
            scores.subList(3,scores.size()).clear();
        }
    }

    public List<Score> getScores() {
        return scores;
    }

    public String toJson(){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(this);
    }

    public static HighScores fromJson(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, HighScores.class);
    }

    public class Score implements Comparable<Score> {

        public Score(float scoreNum, String scoreName) {
            this.scoreNum = scoreNum;
            this.scoreName = scoreName;
        }

        public float scoreNum;
        private String scoreName;

        public float getScoreNum() {
            return scoreNum;
        }

        @Override
        public int compareTo(Score score) {
            //return 0 if equal
            //1 if passed greater than this
            //-1 if this greater than passed
            return score.scoreNum > scoreNum ? 1 : score.scoreNum < scoreNum ? -1 : 0;

        }
    }
}
