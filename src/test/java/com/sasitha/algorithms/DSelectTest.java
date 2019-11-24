package com.sasitha.algorithms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DSelectTest extends AlgorithmsApplicationTests {

    @Autowired
    private DSelect dSelect;

    @Test
    void testMergeSort(){
        int A[] = new int[]{12,34,56,34,98,23,240,43,123,42,78,12,34,56,34,98,23,46,21,31,45,100,123,234,33};
        int B[] = Arrays.copyOf(A, A.length);
        Arrays.sort(B);
        int value = dSelect.findElement(A,6);
        assertEquals(B[5],value);
    }
}
