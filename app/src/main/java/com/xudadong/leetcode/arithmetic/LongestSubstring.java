package com.xudadong.leetcode.arithmetic;

import com.xudadong.leetcode.contract.RegularModel;

/**
 * 无重复字符的最长子串
 * <p>
 * Created by didi on 2019-09-26.
 */
public class LongestSubstring extends RegularModel<String, Integer> {

    @Override
    public String getTitle() {
        return "无重复字符的最长子串长度";
    }

    @Override
    public String getDesc() {
        return "给定一个字符串，请你找出其中不含有重复字符的最长子串的长度。\n" +
                "示例:\n" +
                "输入: \"pwwkew\"\n" +
                "输出: 3\n" +
                "解释: 因为无重复字符的最长子串是 \"wke\"，所以其长度为 3。\n" +
                "请注意，你的答案必须是 子串 的长度，\"pwke\" 是一个子序列，不是子串。\n";
    }

    @Override
    public String getInput() {
        return "pwwkew";
    }

    @Override
    public Integer execute(String input) {
        if (input == null || input.length() == 0) {
            return 0;
        }
        char[] chars = input.toCharArray();
        int max = 1;
        int start = 0;
        int end = 0;
        while (end + 1 < chars.length) {
            int i;
            for (i = start; i <= end; i++) {
                if (chars[i] == chars[end+1]) {
                    break;
                }
            }
            if (i < end + 1) {
                start = i+1;
            }
            int len = end + 1 - start + 1;
            if (max < len) {
                max = len;
            }
            end ++;
        }
        return max;
    }

    @Override
    public String getResult(Integer result) {
        return "" + result;
    }

    @Override
    public String[] getKeywords() {
        return new String[]{"滑动窗口"};
    }
}
