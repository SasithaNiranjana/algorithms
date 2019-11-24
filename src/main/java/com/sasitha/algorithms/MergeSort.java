package com.sasitha.algorithms;

import org.springframework.stereotype.Component;

/**
 * The following algorithms demonstrate how to sort an array in ascending order using merge sort algorithm. The running time of this algorithm is O(nlogn).
 *
 * @author  Sasitha Niranjana
 * @version 1.0
 * @since   2019-11-10
 */
@Component
public class MergeSort {

    /**
     * @para A The array needed to be sorted using merge sort
     */
    public void sortArray(int[] A){
        mergeSort(A,0,A.length-1);
    }

    /**
     * This subroutine will call recursively to sort the left sub array and right sub array recursively. The base case for the recursive function is an array of input size 2.
     * The start and end parameters are being used to mark the correct positions of sub arrays without creating a new array for each of left sub array and right sub array.
     * The value calculated and saved in variable p will hold the index of the center point of the provided array.
     * @para A The array needed to be sorted using merge sort
     * @param start The start position of the sub array
     * @param end The ending position of sub array
     */
    private void mergeSort(int[] A,int start,int end){
        int p = (end-start)/2;
        if(p >= 1){
            mergeSort(A,start,start+p);
            mergeSort(A,start+p+1,end);
        }
        mergeArray(A,start,end,p);
    }

    /**
     * This subroutine will call to merge the result of left  sub array and right sub array. The corresponding merge logic is explained as follows.
     * An array named K which has the same size as the sub array will be created to hold the result of the intermediate merge. The variable j will represent the current point of
     * left sub array while the variable k will represent the current point of right sub array. The maximum value for j will be start+p while the maximum value for k will be the value
     * of end. A for loop will be executed to fill the values to the merge array K. The algorithm will compare the current value of left sub array with the current value of right sub
     * array. If the left sub array value is the smallest it will be assigned as the value of the current position of K array and the value of j will be incremented by one. If the
     * right sub array value is the smallest, it will be assigned as the value of the current position of K array and the value of k will be incremented by one. If both values are
     * equal, that value will be assigned as the value of current position of K array as well as the value next to the current position of array K. The values for j,k and i will be
     * incremented by one since copied from both sides and two positions have been filled. If copying values from either one sub array has already been completed, all remaining
     * values other array will be copied over to K array. Finally the values from array K will be copied back to array A if the control flag needToMerge is true. This control flag is being
     * used to control unnecessary values copying if the section treated in array A is exactly same as array K.
     * @para A The array needed to be sorted using merge sort
     * @param start The start position of the sub array
     * @param end The ending position of sub array
     * @param p Index of the center point of the sub array
     */
    private void mergeArray(int[] A,int start, int end, int p){
        int j = start;
        int k = start+p+1;
        int K[] = new int[end-start+1];
        boolean needToMerge = false;
        for(int i = start;i <= end;i++){
            if(j <= start+p && k <= end){
                if(A[j] < A[k]){
                    K[i-start] = A[j];
                    j++;
                }
                else if(A[k] < A[j]){
                    if(j <= start+p)
                        needToMerge = true;
                    K[i-start] = A[k];
                    k++;
                }
                else if(A[k] == A[j]){
                    needToMerge = true;
                    K[i-start] = A[k];
                    K[i-start+1] = A[k];
                    j++;
                    k++;
                    i++;
                }
            }
            else if(k <= end){
                K[i-start] = A[k];
                k++;
            }
            else if(j <= start+p){
                K[i-start] = A[j];
                j++;
            }
        }
        if(needToMerge){
            for(int i = start;i <= end;i++){
                A[i] = K[i-start];
            }
        }

    }
}
