package com.xudadong.leetcode.utils;

import com.xudadong.leetcode.algorithm.BigNumberPlus;
import com.xudadong.leetcode.algorithm.BinarySearch;
import com.xudadong.leetcode.algorithm.LongestSubstring;
import com.xudadong.leetcode.algorithm.MedianInSortedArrays;
import com.xudadong.leetcode.algorithm.QuickSort;
import com.xudadong.leetcode.algorithm.ReverseLinkedList;
import com.xudadong.leetcode.algorithm.TwoSumLinkedList;
import com.xudadong.leetcode.algorithm.special.DepthOfViewGroup;
import com.xudadong.leetcode.algorithm.special.MultiThreadPrinter;
import com.xudadong.leetcode.algorithm.special.TwoSum;
import com.xudadong.leetcode.contract.Model;
import com.xudadong.leetcode.pattern.ProducerConsumer;
import com.xudadong.leetcode.pattern.SingletonInstance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <title>
 * <p>
 * Created by didi on 2019-07-30.
 */
public class ParseUtil {

    private static Set<Class<? extends Model>> testSets;
    private static List<Model> leetModels;

    static {
        //todo 使用注解获取Model所有子类
        testSets = new HashSet<>();
        testSets.add(ReverseLinkedList.class);
        testSets.add(DepthOfViewGroup.class);
        testSets.add(QuickSort.class);
        testSets.add(BinarySearch.class);
        testSets.add(MultiThreadPrinter.class);
        testSets.add(BigNumberPlus.class);
        testSets.add(TwoSumLinkedList.class);
        testSets.add(LongestSubstring.class);
        testSets.add(MedianInSortedArrays.class);
        testSets.add(TwoSum.class);
        testSets.add(ProducerConsumer.class);
        testSets.add(SingletonInstance.class);
    }

    public static List<Model> parseData() {
        if (leetModels != null && leetModels.size() > 0) {
            return leetModels;
        }

        leetModels = new ArrayList<>();
        Iterator<Class<? extends Model>> iterator = testSets.iterator();
        while(iterator.hasNext()) {
            Class<? extends Model> clz = iterator.next();
            try {
                Model model = (Model) Class.forName(clz.getName()).newInstance();
                leetModels.add(model);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        testSets.clear();
        testSets = null;
        return leetModels;
    }
}
