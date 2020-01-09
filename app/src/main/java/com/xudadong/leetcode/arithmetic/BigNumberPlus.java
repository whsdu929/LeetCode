package com.xudadong.leetcode.arithmetic;

import com.xudadong.leetcode.contract.RegularModel;

/**
 * 大数相加
 * <p>
 * Created by didi on 2019-08-02.
 */
public class BigNumberPlus extends RegularModel<String[], String> {

    @Override
    public String getTitle() {
        return "大数相加";
    }

    @Override
    public String getDesc() {
        return "输入:\"2345\",\"658097\"，输出:\"660442\"";
    }

    @Override
    public String[] getInput() {
        return new String[]{"2345", "658097"};
    }

    @Override
    public String execute(String[] strArr) {
        int lenA = strArr[0].length();
        int lenB = strArr[1].length();
        if (lenA > lenB) {
            strArr[1] = addZeroOnLeft(strArr[1], lenA - lenB);
        } else {
            strArr[0] = addZeroOnLeft(strArr[0], lenB - lenA);
        }
        int[] arr = new int[strArr[0].length() + 1];
        for (int i = strArr[0].length() - 1; i >= 0; i--) {
            int ai = Integer.parseInt(strArr[0].charAt(i) + "");
            int bi = Integer.parseInt(strArr[1].charAt(i) + "");
            int ci = arr[i+1];
            int tmp = ai + bi + ci;
            arr[i+1] = tmp % 10;
            arr[i] = tmp / 10;
        }
        StringBuffer sb = new StringBuffer();
        boolean isStartWithZero = arr[0] == 0;
        for (int i = isStartWithZero ? 1 : 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    private String addZeroOnLeft(String str, int count) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(0);
        }
        sb.append(str);
        return sb.toString();
    }

    @Override
    public String getResult(String result) {
        return result;
    }
}
