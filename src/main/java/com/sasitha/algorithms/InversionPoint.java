package com.sasitha.algorithms;

import org.springframework.stereotype.Component;

/**
 * The following algorithms demonstrate how to calculate number of inversion points in a given array which is containing n elements in any order.
 * The definition for an inversion point can be given as for all i,j where i>j A[i] > A[j]. In the worst case scenario where the given array is sorted in descending order the number of
 * inversion points will be combinations of n choose 2 because any single pair of i,j will be an inversion point. The best case scenario will be the case where the provided array is
 * already sorted in ascending order. In this case the number of inversion points will be 0. The proposed algorithm for this is a piggyback version of merge sort algorithm where we can
 * calculate number of inversion points in left sub array where both i,j are in left sub array as well as number of inversion points in right sub array where both i,j are in right
 * sub array as well as number of split inversion points where i and j is left and right sub arrays.
 *
 * @author  Sasitha Niranjana
 * @version 1.0
 * @since   2019-11-10
 */
@Component
public class InversionPoint {

    /**
     * @para A The array needed to be sorted using merge sort
     */
    public int getNumberOfInversionPoints(int[] A){
        return mergeSortAndCalculateInversionPoints(A,0,A.length-1);
    }

    /**
     * This subroutine will call recursively to sort the left sub array and right sub array recursively. The base case for the recursive function is an array of input size 2.
     * The start and end parameters are being used to mark the correct positions of sub arrays without creating a new array for each of left sub array and right sub array.
     * The value calculated and saved in variable p will hold the index of the center point of the provided array.
     * @para A The array needed to be sorted using merge sort
     * @param start The start position of the sub array
     * @param end The ending position of sub array
     */
    private int mergeSortAndCalculateInversionPoints(int[] A, int start, int end){
        int p = (end-start)/2;
        int totalInversionPoints = 0;
        if(p >= 1){
            totalInversionPoints = totalInversionPoints + mergeSortAndCalculateInversionPoints(A,start,start+p);
            totalInversionPoints = totalInversionPoints + mergeSortAndCalculateInversionPoints(A,start+p+1,end);
        }
        totalInversionPoints = totalInversionPoints + mergeArrayAndFindSplitInversionPoints(A,start,end,p);
        return totalInversionPoints;
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
     * used to control unnecessary values copying if the section treated in array A is exactly same as array K. The indication for an split point is when there is an element copy from the right
     * sub array while there are remaining elements in the left sub array. Each time such an element copy happens from right sub array, number of split points generated will be equal to the
     * number of remaining elements in left sub array.
     * @para A The array needed to be sorted using merge sort
     * @param start The start position of the sub array
     * @param end The ending position of sub array
     * @param p Index of the center point of the sub array
     */
    private int mergeArrayAndFindSplitInversionPoints(int[] A, int start, int end, int p){
        int splitPoints = 0;
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
                    splitPoints = splitPoints + (start+p+1-j);
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
        return splitPoints;

    }
}
