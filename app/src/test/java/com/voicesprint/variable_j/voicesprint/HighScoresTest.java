package com.voicesprint.variable_j.voicesprint;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by atabakh on 17/07/2016.
 */
public class HighScoresTest {

    static final String json = "{\"scores\":[{\"scoreNum\":15415.0,\"scoreName\":\"test_name4\"},{\"scoreNum\":531.0,\"scoreName\":\"test_name3\"},{\"scoreNum\":143.0,\"scoreName\":\"test_name2\"}]}";

    @Test
    public void test1() throws Exception {
        HighScores highScores = new HighScores();
        highScores.addScore("test_name", (float) 100.5);
        highScores.addScore("test_name2", 143);
        highScores.addScore("test_name3", 531);
        highScores.addScore("test_name4", 15415);

        assertEquals(3, highScores.getScores().size());
        assertEquals(15415, (int)highScores.getScores().get(0).getScoreNum());
    }

    @Test
    public void test2() throws Exception {
        HighScores highScores = new HighScores();
        highScores.addScore("test_name", 100);
        highScores.addScore("test_name2", 143);
        highScores.addScore("test_name3", 531);
        highScores.addScore("test_name4", 15415);

        assertEquals(json, highScores.toJson());
        assertEquals(json, HighScores.fromJson(highScores.toJson()).toJson());
    }
}
