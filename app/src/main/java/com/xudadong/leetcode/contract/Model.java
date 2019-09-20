package com.xudadong.leetcode.contract;

import android.content.Context;

import java.io.Serializable;

/**
 * Model基类
 * <p>
 * Created by didi on 2019-07-10.
 */
public abstract class Model<T, V> implements Serializable {

    protected Args mArgs;

    /**
     * 设置参数
     */
    public void setArgs(Args args) {
        mArgs = args;
    }

    /**
     * 标题
     */
    public abstract String getTitle();

    /**
     * 详细描述
     */
    public String getDesc() {
        return "";
    }

    /**
     * 代码实现
     */
    protected String getCode() {
        return "";
    }

    /**
     * 输入数据
     */
    public abstract T getInput();

    /**
     * 实现方案1
     */
    public abstract V fun(T input);

    /**
     * 实现方案2
     */
    public V fun2(T input) {
        return fun(input);
    }

    /**
     * 输出结果
     */
    public abstract String getResult(V result);

    /**
     * 获取时间复杂度
     */
    public Complexity getTimeComplexity(){
        return null;
    }

    /**
     * 获取空间复杂度
     */
    public Complexity getSpaceComplexity() {
        return null;
    }

    public enum Complexity {
        o1, ologN, oN, oNlogN, oN2;

        public Complexity bestComplexity, worstComplexity;

        @Override
        public String toString() {
            switch (this) {
                case o1:
                    return "o(1)";
                case ologN:
                    return "o(log n)";
                case oN:
                    return "o(n)";
                case oNlogN:
                    return "o(n*log n)";
                case oN2:
                    return "o(n^2)";
            }
            return "Unknown";
        }
    }

    public static class Args implements Serializable{
        public Context mApplicationContext;
    }
}
