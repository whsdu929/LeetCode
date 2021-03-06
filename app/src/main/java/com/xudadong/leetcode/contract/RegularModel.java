package com.xudadong.leetcode.contract;

import java.io.Serializable;

/**
 * <title>
 * <p>
 * Created by didi on 2019-12-31.
 */
public abstract class RegularModel<T extends Serializable, V extends Serializable> extends Model {

    /**
     * 输入数据
     */
    public abstract T getInput();

    /**
     * 实现方案1
     */
    public abstract V execute(T input);

    /**
     * 实现方案2
     */
    public V execute2(T input) {
        return execute(input);
    }

    /**
     * 输出结果
     */
    public abstract String getResult(V result);

}
