package com.sasitha.algorithms;

import org.springframework.stereotype.Component;

@Component
public class MergeSort {

    public void sortArray(int[] A){
        mergeSort(A,0,A.length-1);
    }

    private int mergeSort(int[] A,int start,int end){
        int p = (end-start)/2;
        if(p >= 1){
            mergeSort(A,start,start+p);
            mergeSort(A,start+p+1,end);
        }
        mergeArray(A,start,end,p);
        return 1;
    }

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
