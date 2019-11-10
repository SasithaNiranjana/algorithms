package com.sasitha.algorithms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MergeSortTest extends AlgorithmsApplicationTests {

    @Autowired
    private MergeSort mergeSort;

    @Test
    void testMergeSort(){
        int A[] = new int[]{12,34,56,34,98,23,240,43,123,42,90,67,74,54,23};
        int B[] = Arrays.copyOf(A, A.length);
        Arrays.sort(B);
        mergeSort.sortArray(A);
        for(int i=0;i<A.length;i++){
            assertEquals(B[i],A[i]);
        }
    }
}
