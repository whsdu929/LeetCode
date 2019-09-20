package com.xudadong.leetcode.arithmetic;

import com.xudadong.leetcode.contract.Model;

/**
 * 二分查找
 * <p>
 * Created by didi on 2019-08-01.
 */
public class BinarySearch extends Model<int[], Integer> {

    @Override
    public String getTitle() {
        return "二分查找";
    }

    @Override
    public String getDesc() {
        return "二分查找，输入一个有序数组，数组最后一位是要查找的数字: 0, 1, 2, 4, 6, 7, 8, 9, 2";
    }

    @Override
    public int[] getInput() {
        return new int[]{0, 1, 2, 4, 6, 7, 8, 9, 2};
    }

    @Override
    public Integer fun(int[] array) {
        int resultIndex = -1;
        if (array != null && array.length > 1) {
            int findValue = array[array.length - 1];
            int low = 0, high = array.length - 2;

            while (low <= high) {
                int mid = (low + high) / 2;
                if (array[mid] > findValue) {
                    high = mid - 1;
                } else if (array[mid] < findValue) {
                    low = mid + 1;
                } else {
                    resultIndex = mid;
                    break;
                }
            }
        }
        return resultIndex;
    }

    @Override
    public String getResult(Integer result) {
        return "下标: " + result;
    }

    @Override
    public Complexity getTimeComplexity() {
        return Complexity.ologN;
    }

    @Override
    public Complexity getSpaceComplexity() {
        return Complexity.o1;
    }
}
