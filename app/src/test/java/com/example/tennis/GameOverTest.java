package com.example.tennis;

import junit.framework.TestCase;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;

public class GameOverTest{

    @Rule
    val rule = InstantTaskExecutorRule()

    @Test
    public void gameOverTest(){
        GameOver gameOver = new GameOver();
        assertEquals(true,gameOver.check(20,10));
    }

    @Test
    public void gameOverTest2(){
        GameOver gameOver = new GameOver();
        assertEquals(false,gameOver.check(20,30));
    }

}