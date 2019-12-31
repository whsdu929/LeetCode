package com.xudadong.leetcode.contract;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Model基类
 * <p>
 * Created by didi on 2019-07-10.
 */
public abstract class Model implements Serializable {

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
    public abstract Drawable getCodeDrawable(Context context);

    /**
     * 算法关键词
     * @return
     */
    public String[] getKeywords() {
        return null;
    }

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
}
