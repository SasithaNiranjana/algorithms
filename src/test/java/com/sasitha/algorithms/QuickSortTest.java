package com.sasitha.algorithms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuickSortTest extends AlgorithmsApplicationTests {

    @Autowired
    private QuickSort quickSort;

    @Test
    void testMergeSort(){
        int A[] = new int[]{12,34,56,34,98,23,240,43,123,42,78,12,34,56,34,98,23,240,43,123,42,78};
        int B[] = Arrays.copyOf(A, A.length);
        Arrays.sort(B);
        quickSort.sortArray(A);
        for(int i=0;i<A.length;i++){
            assertEquals(B[i],A[i]);
        }
    }
}
