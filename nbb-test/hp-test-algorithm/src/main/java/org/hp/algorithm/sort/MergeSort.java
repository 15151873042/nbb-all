package org.hp.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 10 };
        int[] newArr = mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(newArr));
    }

    public static int[] mergeSort(int[] arr, int l, int h) {
        if (l == h) {
            return new int[]{ arr[l] };
        }

        int mid = l + (h -l) / 2;  // 对半划分
        int[] leftArr = mergeSort(arr, l, mid); // 左侧有序数组
        int[] rightArr = mergeSort(arr, mid+1, h); // 右侧有序数组
        int[] newArr = new int[leftArr.length + rightArr.length]; // 需要一个空数组

        int m = 0, i = 0, j = 0;
        while(i < leftArr.length && j < rightArr.length) {
            newArr[m++] = leftArr[i] <= rightArr[j] ? leftArr[i++] : rightArr[j++]; //左侧数组和右侧数组从第一个元素依次比较
        }

        // 到此处左右侧数组有一个元素已经全部放入，接下来的步骤是将另一个数组所有元素放入到新数组中
        while(i < leftArr.length) {
            newArr[m++] = leftArr[i++];
        }
        while(j < rightArr.length) {
            newArr[m++] = rightArr[j++];
        }
        return newArr;
    }

}
