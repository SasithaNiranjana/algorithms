package com.sasitha.algorithms;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * The following algorithms demonstrate how to select the element in the given position in the subjected sorted array by piggybacking randomized QuickSort algorithm. For an example
 * finding the seventh largest element in the provided array.
 *
 * @author  Sasitha Niranjana
 * @version 1.0
 * @since   2019-11-24
 */
@Component
public class RSelect {

    public int findElement(int A[],int position){
        if(position < 1 || position > A.length)
            return -1;
        SortData sortData = new SortData(A,new Random(),0,A.length-1,0,0,0,0,position-1);
        return quickSelect(sortData);
    }

    /**
     * This function is being called recursively to partition the subjected array segment around the selected pivot. The pivot will be selected randomly and will be assigned to
     * {@link SortData#pivot}. The array segment will be partitioned around the selected pivot by using {@link #partitionArray(SortData)} function. Finally the algorithm will check
     * whether the given position is inside the index range where values are equal to the pivot. If yes, it will return the value of the pivot, otherwise it will check whether the
     * given position is in the subset where all the values are smaller than the pivot, if yes it will recurse only the left half of the array, otherwise it will recurse the right
     * half of the array.
     * @param sortData An instance of {@link SortData}
     */
    private int quickSelect(SortData sortData){
        if(sortData.end - sortData.start + 1 > 2){
            sortData.pivot = sortData.array[sortData.randomGenerator.nextInt(sortData.array.length)];
            partitionArray(sortData);
            if(sortData.position >= sortData.start+sortData.lowerCount && sortData.position < sortData.start+sortData.lowerCount+sortData.equalsCount)
                return sortData.array[sortData.start+sortData.lowerCount];
            else if(sortData.position < sortData.start+sortData.lowerCount)
                return quickSelect(new SortData(sortData.array,sortData.randomGenerator,sortData.start,sortData.start+sortData.lowerCount-1,0,0,0,0,sortData.position));
            else
                return quickSelect(new SortData(sortData.array,sortData.randomGenerator,sortData.start+sortData.lowerCount+sortData.equalsCount,sortData.end,0,0,0,0,sortData.position));
        }
        else{
            for(int i = sortData.start ; i <= sortData.end-1 ; i++){
                for(int j = i + 1; j <= sortData.end; j++){
                    if(sortData.array[i] > sortData.array[j]){
                        int temp = sortData.array[i];
                        sortData.array[i] = sortData.array[j];
                        sortData.array[j] = temp;
                    }
                }
            }
            return sortData.array[sortData.position];
        }
    }

    /**
     * This function is being used to partition the array segment around the pivot element. The scanning process will start from the starting point and the scan will end at the ending
     * position of the array segment.
     * <br><br>
     * If an element is equal to the pivot point the value of {@link SortData#equalsCount} will be incremented. In the same time the algorithm will check whether the value of
     * {@link SortData#upperCount} is greater than 0. If it is greater than 0, that means that we have found some values which are greater than the pivot point so the swapping will happen
     * between the current position of the scanning and the minimum index of the point where the value is greater than the pivot. Basically this operation will ensure to shift all the elements
     * greater than the pivot to the right.
     * <br><br>
     * If an element is smaller than the pivot point the value of {@link SortData#lowerCount} will be incremented. In the same time the algorithm will check whether the value of
     * {@link SortData#equalsCount} is greater than 0. If it is greater than 0, that means that we have found some values which are equal to the pivot point so the swapping will happen
     * between the current position of the scanning and the minimum index of the point where the value is equal to the pivot. One more swapping is required in this special case to
     * avoid moving a point where the value is equal to the pivot, to the right side of the array. In this case the second swapping will happen between the current position and the minimum
     * index of the point where the value is greater than the pivot. If the value of {@link SortData#equalsCount} is zero, the second if clause will be activated to find whether there are
     * values greater than the pivot which have already been discovered. This can be determined if the value of {@link SortData#upperCount} is greater than zero. A swapping operation
     * will happen between the current position and the minimum index of the point where the value is greater than the pivot. Basically this will ensure to shift all the elements which are
     * less than to the pivot to the left.
     * <br><br>
     * If an element is greater than the pivot the value of {@link SortData#upperCount} will be incremented.
     * @param sortData An instance of {@link SortData}
     */
    private void partitionArray(SortData sortData){
        for(int i = sortData.start; i <= sortData.end ; i++){
            if(sortData.array[i] == sortData.pivot){
                if(sortData.upperCount > 0){
                    swapPosition(sortData,i,sortData.start+sortData.lowerCount+sortData.equalsCount);
                }
                sortData.equalsCount++;
            }
            else if(sortData.array[i] < sortData.pivot){
                if(sortData.equalsCount > 0){
                    swapPosition(sortData,i,sortData.start+sortData.lowerCount);
                    swapPosition(sortData,i,sortData.start+sortData.lowerCount+sortData.equalsCount);
                }
                else if(sortData.upperCount > 0){
                    swapPosition(sortData,i,sortData.start+sortData.lowerCount);
                }
                sortData.lowerCount++;
            }
            else if(sortData.array[i] > sortData.pivot)
                sortData.upperCount++;
        }
    }

    /**
     * this function is being used to swap two values in the array
     * @param data An instance of {@link SortData}
     * @param  left Index of the first element
     * @param  right Index of the second element
     */
    private void swapPosition(SortData data,int left, int right){
        int temp = data.array[left];
        data.array[left] = data.array[right];
        data.array[right] = temp;
    }

    /**
     * SortData class is acting as a value object to hold the necessary data of a particular recursive call. The purpose of designing such a value object to avoid stack overflow error when
     * there are many recursive calls because the objects will be created in the heap and only the reference will be available to the stack.
     *
     * @author  Sasitha Niranjana
     * @version 1.0
     * @since   2019-11-24
     */
    @AllArgsConstructor
    @NoArgsConstructor
    private class SortData{
        /**
         * The array is subject to have sorting order
         */
        int array[];

        /**
         * Random number generator to generate the position of the pivot randomly
         */
        Random randomGenerator;

        /**
         * Starting position of the array when doing recursive calls, defining this inline boundary will help to perform inline modifications to the original array without creating a
         * new sub array for each recursive call
         */
        int start;

        /**
         * Ending position of the array when doing recursive calls, defining this inline boundary will help to perform inline modifications to the original array without creating a
         * new sub array for each recursive call
         */
        int end;

        /**
         * Number of elements less than to the value of pivot
         */
        int lowerCount;

        /**
         * Number of elements greater than to the value of pivot
         */
        int upperCount;

        /**
         * Number of elements equal to the value of pivot
         */
        int equalsCount;

        /**
         * The value of the pivot point
         */
        int pivot;

        /**
         * The value of the position where the selection is happening
         */
        int position;
    }
}
