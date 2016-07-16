package com.voicesprint.variable_j.voicesprint;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by atabakh on 17/07/2016.
 */
public class HighScoresTest {

    @Test
    public void test() throws Exception {
        HighScores highScores = new HighScores();
        highScores.addScore("test_name", (float) 100.5);
        highScores.addScore("test_name2", 143);
        highScores.addScore("test_name3", 531);
        highScores.addScore("test_name4", 15415);

        assertEquals(3, highScores.getScores().size());
        assertEquals(15415, (int)highScores.getScores().get(0).getScoreNum());
    }
}
