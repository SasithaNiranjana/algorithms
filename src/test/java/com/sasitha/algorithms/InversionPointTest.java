package com.sasitha.algorithms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InversionPointTest extends AlgorithmsApplicationTests {

    @Autowired
    private InversionPoint inversionPoint;

    @Test
    public void testInversionPointForDescendingOrderArray(){
        int A[] = new int[]{12,10,9,8,7};
        int numberOfInversionPoints = inversionPoint.getNumberOfInversionPoints(A);
        assertEquals(10,numberOfInversionPoints);
    }

    @Test
    public void testInversionPointForAscendingOrderArray(){
        int A[] = new int[]{7,8,23,45,67};
        int numberOfInversionPoints = inversionPoint.getNumberOfInversionPoints(A);
        assertEquals(0,numberOfInversionPoints);
    }
}
