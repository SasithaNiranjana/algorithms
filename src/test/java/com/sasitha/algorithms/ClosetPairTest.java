package com.sasitha.algorithms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClosetPairTest extends AlgorithmsApplicationTests {

    @Autowired
    private ClosetPair closetPair;

    @Test
    public void testClosetPair(){
        int[][] points = {{6,-3},{9,2},{8,1},{4,7},{2,6},{7,1},{3,9},{8,2},{6,7},{5,1}};
        ClosetPair.Pair[] closetPair = this.closetPair.findClosetPair(this.closetPair.createPairArray(points));
        ClosetPair.Pair[] closetPairBruteForce = this.closetPair.findClosetPairBruteForce(this.closetPair.createPairArray(points));
        boolean predicate = closetPair[0].getX() == closetPairBruteForce[0].getX() && closetPair[0].getY() == closetPairBruteForce[0].getY() && closetPair[1].getX() == closetPairBruteForce[1].getX() && closetPair[1].getY() == closetPairBruteForce[1].getY();
        assertEquals(true,predicate);
    }
}
