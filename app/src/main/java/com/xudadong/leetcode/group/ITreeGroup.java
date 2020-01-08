package com.xudadong.leetcode.group;

/**
 * @author sunfusheng
 * @since 2020-01-08
 */
public interface ITreeGroup extends IGroup {
    @Override
    default String getGroupName() {
        return "树";
    }
}
