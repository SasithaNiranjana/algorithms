package com.sasitha.algorithms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlgorithmsApplicationTests {

	@Autowired
	private MergeSort mergeSort;

	@Test
	void contextLoads() {
	}

	@Test
	void testMergeSort(){
		int A[] = new int[]{12,34,56,34,98,23,240,43,123,42,90,67,74,54,23};
		mergeSort.sortArray(A);
		for(int i=0;i<A.length;i++){
			System.out.print(A[i]+",");
		}
	}

}
