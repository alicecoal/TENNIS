package com.example.tennis;

import junit.framework.TestCase;
import static org.junit.Assert.*;
import org.junit.Test;

public class VelocityTest {

    @Test
    public void xVelocityTest(){
        Velocity velocity = new Velocity(25,35);
        assertEquals(25,velocity.getX());
    }

    @Test
    public void yVelocityTest(){
        Velocity velocity = new Velocity(25,35);
        assertEquals(35,velocity.getY());
    }

    @Test
    public void xVelocityTest2(){
        Velocity velocity = new Velocity(25,35);
        assertEquals(25,velocity.getX());
    }

    @Test
    public void yVelocityTest2(){
        Velocity velocity = new Velocity(25,35);
        assertEquals(35,velocity.getY());
    }

}