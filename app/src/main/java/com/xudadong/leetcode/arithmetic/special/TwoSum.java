package com.xudadong.leetcode.arithmetic.special;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.xudadong.leetcode.R;
import com.xudadong.leetcode.contract.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 找数凑值
 * <p>
 * Created by didi on 2019-09-24.
 */
public class TwoSum extends Model {

    @Override
    public String getTitle() {
        return "找数凑值";
    }

    @Override
    public String getDesc() {
        return "给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。\n" +
                "你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。\n" +
                "示例:\n" +
                "给定 nums = [2, 7, 11, 15], target = 9\n" +
                "因为 nums[0] + nums[1] = 2 + 7 = 9\n" +
                "所以返回 [0, 1]";
    }

    @Override
    public Drawable getCodeDrawable(Context context) {
        return context.getResources().getDrawable(R.mipmap.code_two_sum);
    }

    public int[] fun(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }
        int[] result = null;
        ArrayList<Integer> list = new ArrayList<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }
        for (int i = 0; i < nums.length; i++) {
            if (list.contains(target - nums[i])) {
                int index = list.indexOf(target - nums[i]);
                if (i != index) {
                    result = new int[]{i, index};
                    break;
                }
            }
        }
        return result;
    }

    public int[] fun2(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }
        int[] result = null;
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(nums[i])) {
                result = new int[2];
                result[0] = hashMap.get(nums[i]);
                result[1] = i;
                break;
            }
            hashMap.put(target - nums[i], i);
        }
        return result;
    }

}
