package com.xudadong.leetcode.utils;

import com.xudadong.leetcode.arithmetic.BigNumberPlus;
import com.xudadong.leetcode.arithmetic.BinarySearch;
import com.xudadong.leetcode.arithmetic.LongestSubstring;
import com.xudadong.leetcode.arithmetic.QuickSort;
import com.xudadong.leetcode.arithmetic.ReverseLinkedList;
import com.xudadong.leetcode.arithmetic.TwoSumLinkedList;
import com.xudadong.leetcode.contract.Model;

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
        //testSets.add(DepthOfViewGroup.class);
        testSets.add(QuickSort.class);
        testSets.add(BinarySearch.class);
        //testSets.add(MultiThreadPrinter.class);
        testSets.add(BigNumberPlus.class);
        testSets.add(TwoSumLinkedList.class);
        testSets.add(LongestSubstring.class);
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
