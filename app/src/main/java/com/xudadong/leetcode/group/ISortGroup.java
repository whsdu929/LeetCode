package com.xudadong.leetcode.group;

/**
 * @author sunfusheng
 * @since 2020-01-08
 */
public interface ISortGroup extends IGroup {
    @Override
    default String getGroupName() {
        return "排序";
    }
}
