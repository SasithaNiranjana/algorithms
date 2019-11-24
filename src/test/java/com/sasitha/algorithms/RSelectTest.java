package com.sasitha.algorithms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RSelectTest extends AlgorithmsApplicationTests {

    @Autowired
    private RSelect rSelect;

    @Test
    void testMergeSort(){
        int A[] = new int[]{12,34,56,34,98,23,240,43,123,42,78,12,34,56,34,98,23,240,43,123,42,78};
        int B[] = Arrays.copyOf(A, A.length);
        Arrays.sort(B);
        int value = rSelect.findElement(A,7);
        assertEquals(B[7],value);
    }
}
