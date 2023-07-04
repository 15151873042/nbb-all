package org.hp.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
        quickSort(arr, 0, arr.length -1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        int pivotIndex = doublePointerSwap(arr, startIndex, endIndex); // 确定中间点

        quickSort(arr, startIndex, pivotIndex-1); // 左半边继续递归
        quickSort(arr, pivotIndex+1, endIndex); // 右半边继续递归
    }

    private static int doublePointerSwap(int[] arr, int startIndex, int endIndex) {
        // 使用首元素的值作为基准值
        int base = arr[startIndex];
        int leftPoint = startIndex;
        int rightPoint = endIndex;

        while (leftPoint < rightPoint) {
            // 从右向左扫描，找出小于基准值的数据
            while(leftPoint < rightPoint && arr[rightPoint] > base) {
                rightPoint--;
            }

            // 从左向右扫描，找出大于基准值的数据
            while(leftPoint < rightPoint && arr[leftPoint] <= base) {
                leftPoint++;
            }

            // 没有越界则交换数据
            if (leftPoint < rightPoint) {
                int temp = arr[leftPoint];
                arr[leftPoint] = arr[rightPoint];
                arr[rightPoint] = temp;
            }
        }

        // 一次大循环结束后，将将基准值放入应在所在的位置（交换位置）
        arr[startIndex] = arr[rightPoint];
        arr[rightPoint] = base;
        return rightPoint;
    }

}
