package com.xudadong.leetcode.arithmetic;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.xudadong.leetcode.R;
import com.xudadong.leetcode.contract.RegularModel;

import java.io.Serializable;

/**
 * 寻找两个有序数组的中位数
 * <p>
 * Created by didi on 2019-09-27.
 */
public class MedianInSortedArrays extends RegularModel<MedianInSortedArrays.Input, Double> {

    @Override
    public String getTitle() {
        return "寻找两个有序数组的中位数";
    }

    @Override
    public String getDesc() {
        return "示例 1:\n" +
                "nums1 = [1, 3]，nums2 = [2]，则中位数是 2.0\n" +
                "\n" +
                "示例 2:\n" +
                "nums1 = [1, 2]，nums2 = [3, 4]，则中位数是 (2 + 3)/2 = 2.5";
    }

    @Override
    public Drawable getCodeDrawable(Context context) {
        return context.getResources().getDrawable(R.mipmap.code_median_in_sorted_arrays);
    }

    @Override
    public Input getInput() {
        int[] nums1 = new int[]{1, 2};
        int[] nums2 = new int[]{3, 4};
        return new Input(nums1, nums2);
    }

    @Override
    public Double fun(Input input) {
        return findMedianSortedArrays(input.num1, input.num2);
    }

    private double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int count = nums1.length + nums2.length;
        boolean isSingle;
        if ((nums1.length + nums2.length) % 2 == 0) {
            isSingle = false;
        } else {
            isSingle = true;
        }
        int currIndex = -1;
        int i = 0, j = 0;
        double result1 = 0, result2;
        int tmp = 0;
        while (currIndex < count / 2) {
            if (i < nums1.length && j < nums2.length) {
                if (nums1[i] <= nums2[j]) {
                    tmp = nums1[i];
                    i++;
                } else {
                    tmp = nums2[j];
                    j++;
                }
            } else if (i >= nums1.length) {
                tmp = nums2[j];
                j++;
            } else {
                tmp = nums1[i];
                i++;
            }
            currIndex++;
            if (!isSingle && currIndex == (count / 2 - 1)) {
                result1 = tmp;
            }
        }
        result2 = tmp;
        if (isSingle) {
            return result2;
        } else {
            return (result1 + result2) /2;
        }
    }

    @Override
    public String getResult(Double result) {
        return "" + result;
    }

    static class Input implements Serializable {
        int[] num1;
        int[] num2;

        public Input(int[] num1, int[] num2) {
            this.num1 = num1;
            this.num2 = num2;
        }
    }
}
