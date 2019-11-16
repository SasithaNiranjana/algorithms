package com.sasitha.algorithms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class ClosetPair {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class Pair{

        private int x;
        private int y;
    }

    public Pair[] createPairArray(int[][] pairs){
        Pair[] pair = new Pair[pairs.length];
        for(int i = 0; i < pair.length ; i++){
            pair[i] = new Pair(pairs[i][0],pairs[i][1]);
        }
        return pair;
    }

    public Pair[] findClosetPair(Pair[] P){
        Pair[] Px = P;
        Pair[] Py = clonePairArray(P);
        sortArray(Px,true);
        sortArray(Py,false);
        return closetPair(Px,Py);
    }

    public Pair[] findClosetPairBruteForce(Pair[] P){
        Pair p = null;
        Pair q = null;
        double distance = Double.MAX_VALUE;
        for(int i=0;i<P.length-1;i++){
            for(int j=i+1;j<P.length;j++){
                double distanceBetweenPair = findDistanceBetweenPair(P[i], P[j]);
                if(distanceBetweenPair < distance){
                    p = P[i];
                    q = P[j];
                    distance = distanceBetweenPair;
                }
            }
        }
        return p.getX()<q.getX() ? new Pair[]{p,q} : new Pair[]{q,p};

    }

    private Pair[] clonePairArray(Pair[] P){
        Pair[] Py = new Pair[P.length];
        for(int i = 0; i < P.length; i++){
            Py[i] = P[i];
        }
        return Py;
    }

    /**
     * This is the recursive function to simplify the problem into multiple sub problems. First it will recurse the left half of the Px array with the left half of the Py array to get the pair
     * returns from the left side. After the it will call recursively with the right halves of the corresponding arrays to get the pair returned from right side. Then it will find the best
     * pair from those two pairs. The distance of the points in the best pair will be pass as a parameter to the function which is being used to find the split pair if there is any.
     * @para Px The array containing points sorted in x coordinate
     * @para Py The array containing points sorted in y coordinate
     */
    private Pair[] closetPair(Pair[] Px, Pair[] Py){
        Pair[] closePair = null;
        double pairDistance = Double.MAX_VALUE;
        if(Px.length > 2){
            int p = Px.length/2;
            Pair[] leftPx = new Pair[p];
            Pair[] rightPx = new Pair[Px.length - p];
            Pair[] leftPy = new Pair[p];
            Pair[] rightPy = new Pair[Py.length - p];
            for(int i = 0; i < Px.length ; i++){
                if(i<p){
                    leftPx[i] = Px[i];
                    leftPy[i] = Py[i];
                }
                else if(i>=p){
                    rightPx[i-p]=Px[i];
                    rightPy[i-p] = Py[i];
                }
            }

            Pair[] leftPair = closetPair(leftPx,leftPy);
            Pair[] rightPair = closetPair(rightPx,rightPy);
            if(leftPair!=null && rightPair!=null && leftPair.length == 2 && rightPair.length == 2){
                double leftPairDistance = findDistanceBetweenPair(leftPair[0], leftPair[1]);
                double rightPairDistance = findDistanceBetweenPair(rightPair[0], rightPair[1]);
                closePair = leftPairDistance < rightPairDistance ? leftPair : rightPair;
                pairDistance = Math.min(leftPairDistance,rightPairDistance);
            }
            else if(leftPair!=null && leftPair.length == 2){
                closePair = leftPair;
                pairDistance = findDistanceBetweenPair(leftPair[0], leftPair[1]);
            }
            else if(rightPair!=null && rightPair.length == 2){
                closePair = rightPair;
                pairDistance = findDistanceBetweenPair(rightPair[0], rightPair[1]);
            }
            Pair[] splitPair = findSplitPair(leftPx, rightPy, pairDistance);
            return splitPair != null ? splitPair : closePair;
        }
        return Px;

    }

    /**
     * This function is being used to find if there is a split pair where one point is lying on left half and the other one is lying on the right half and the distance of the points return by
     * the split pair must have an euclidean distance less than the value provided by closetPairDistance which is the minimum from the two pairs return from left half and the right half of the
     * array. First of all the algorithm is trying to remove all the points from Ry where the x coordinate is not with in the range of
     * [maxCoordinate - closetPairDistance, maxCoordinate + closetPairDistance]. Since Ry is already sorted, Sy will also be sorted. It can be prove by geometry that it is enough to check 7
     * positions ahead to identify whether there is any optimal solution in Sy but this boundary needs to be selected base on the size of Sy which is returning from numOfElementsPick because
     * there maybe a case where Sy is having less than 7 elements. The correct value for the boundary will be given from firstEnd and secondEnd.
     * @para Qx The array containing points sorted in x coordinate
     * @para Rx The array containing points sorted in y coordinate
     * @param closetPairDistance The minimum distance calculated from the previous step
     */
    private Pair[] findSplitPair(Pair[] Qx, Pair[] Ry,double closetPairDistance){
        Pair[] closetPair = null;
        double closetSplitDistance = closetPairDistance;
        int maxXCoordinate = Qx[Qx.length-1].getX();
        int numOfElementsPick = -1;
        Pair[] Sy = new Pair[Ry.length];
        for(int i = 0;i < Ry.length ; i++){
            if(!(Ry[i].getX()>maxXCoordinate+closetPairDistance || Ry[i].getX()<maxXCoordinate-closetPairDistance)){
                numOfElementsPick++;
                Sy[numOfElementsPick] = Ry[i];
            }
        }
        int firstEnd = numOfElementsPick > 7 ? numOfElementsPick - 6 : numOfElementsPick;
        int secondEnd = numOfElementsPick > 7 ?7 : numOfElementsPick;
        for(int i = 0; i < firstEnd; i++){
            for(int j = 1; j < secondEnd; j++){
                Pair P = Sy[i];
                Pair Q = Sy[i+j];
                double combinedDistance = findDistanceBetweenPair(P,Q);
                if(closetPair == null && combinedDistance < closetSplitDistance){
                    Pair[] combined = new Pair[2];
                    combined[0] = P;
                    combined[1] = Q;
                    closetPair = combined;
                    closetSplitDistance = combinedDistance;

                }
                else if(closetPair != null && combinedDistance < closetSplitDistance){
                    closetPair[0] = P;
                    closetPair[1] = Q;
                    closetSplitDistance = combinedDistance;
                }
            }
        }
        return closetPair;
    }

    /**
     * This function is being used to calculate the euclidean distance of two pints in the plane
     * @para P The first point which is being used to calculate the distance
     * @para Q The second point which is being used to calculate the distance
     */
    private double findDistanceBetweenPair(Pair P, Pair Q){
        return Math.sqrt(Math.pow((P.getX()-Q.getX()),2.0d) + Math.pow((P.getY()-Q.getY()),2.0d));
    }

    /**
     * @para P The array needed to be sorted using merge sort based on axis
     * @param isXAxes The flag to indicate whether sorting is based on x coordinate or y coordinate
     */
    private void sortArray(Pair[] P, boolean isXAxes){
        mergeSort(P,0,P.length-1,isXAxes);
    }

    /**
     * This subroutine will call recursively to sort the left sub array and right sub array recursively. The base case for the recursive function is an array of input size 2.
     * The start and end parameters are being used to mark the correct positions of sub arrays without creating a new array for each of left sub array and right sub array.
     * The value calculated and saved in variable p will hold the index of the center point of the provided array.
     * @para P The array needed to be sorted using merge sort
     * @param start The start position of the sub array
     * @param end The ending position of sub array
     * @param isXAxes The flag to indicate whether sorting is based on x coordinate or y coordinate
     */
    private void mergeSort(Pair[] P, int start, int end, boolean isXAxes){
        int p = (end-start)/2;
        if(p >= 1){
            mergeSort(P,start,start+p,isXAxes);
            mergeSort(P,start+p+1,end,isXAxes);
        }
        mergeArray(P,start,end,p,isXAxes);
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
     * @para P The array needed to be sorted using merge sort
     * @param start The start position of the sub array
     * @param end The ending position of sub array
     * @param p Index of the center point of the sub array
     * @param isXAxes Parameter to identify whether sorting is operating based on x coordinate or y coordinate
     */
    private void mergeArray(Pair[] P, int start, int end, int p, boolean isXAxes){
        int j = start;
        int k = start+p+1;
        Pair Px[] = new Pair[end-start+1];
        boolean needToMerge = false;
        for(int i = start;i <= end;i++){
            if(j <= start+p && k <= end){
                int firstHalfNumber = isXAxes ? P[j].getX() : P[j].getY();
                int secondHalfNumber= isXAxes ? P[k].getX() : P[k].getY();
                if(firstHalfNumber < secondHalfNumber){
                    Px[i-start] = P[j];
                    j++;
                }
                else if(secondHalfNumber < firstHalfNumber){
                    if(j <= start+p)
                        needToMerge = true;
                    Px[i-start] = P[k];
                    k++;
                }
                else if(secondHalfNumber == firstHalfNumber){
                    needToMerge = true;
                    Px[i-start] = P[k];
                    Px[i-start+1] = P[k];
                    j++;
                    k++;
                    i++;
                }
            }
            else if(k <= end){
                Px[i-start] = P[k];
                k++;
            }
            else if(j <= start+p){
                Px[i-start] = P[j];
                j++;
            }
        }
        if(needToMerge){
            for(int i = start;i <= end;i++){
                P[i] = Px[i-start];
            }
        }

    }
}
