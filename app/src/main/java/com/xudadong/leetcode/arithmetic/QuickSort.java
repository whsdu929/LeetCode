package com.xudadong.leetcode.arithmetic;

import com.xudadong.leetcode.contract.Model;

/**
 * 快排
 * <p>
 * Created by didi on 2019-08-01.
 */
public class QuickSort extends Model<int[], int[]> {

    @Override
    public String getTitle() {
        return "快排";
    }

    @Override
    public String getDesc() {
        return "输入: 7 8 0 2 1 9 3 6 5 4";
    }

    @Override
    public int[] getInput() {
        return new int[]{7, 8, 0, 2, 1, 9, 3, 6, 5, 4};
    }

    @Override
    public int[] fun(int[] input) {
        quickSort(input, 0, input.length - 1);
        return input;
    }

    @Override
    public String getResult(int[] result) {
        StringBuffer sb = new StringBuffer("输出: ");
        if (result != null) {
            for (int i : result) {
                sb.append(i).append(" ");
            }
        }
        return sb.toString();
    }

    @Override
    public Complexity getTimeComplexity() {
        Complexity complexity = Complexity.oNlogN;
        complexity.bestComplexity = Complexity.oNlogN;
        complexity.worstComplexity = Complexity.oN2;
        return complexity;
    }

    @Override
    public Complexity getSpaceComplexity() {
        Complexity complexity = Complexity.ologN;
        complexity.bestComplexity = Complexity.ologN;
        complexity.worstComplexity = Complexity.oN;
        return complexity;
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (arr == null || arr.length == 0 || arr.length <= high || low > high) {
            return;
        }

        int i = low, j = high, base = arr[low];
        while (i < j) {
            while (arr[j] > base && j > i) {
                j--;
            }
            while (arr[i] < base && i < j) {
                i++;
            }
            if (i < j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        arr[low] = arr[i];
        arr[i] = base;
        quickSort(arr, low, i);
        quickSort(arr, i + 1, high);
    }
}
