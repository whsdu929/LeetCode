package com.xudadong.leetcode.group;

import android.text.TextUtils;

import com.sunfusheng.spi.core.ServiceProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunfusheng
 * @since 2020-01-08
 */
public class DataSourceUtil {

    /**
     * @param groupClass 分组标志接口
     * @return 获取分组算法类路径列表
     */
    public static <T> List<String> getGroup(Class<T> groupClass) {
        List<String> result = new ArrayList<>();
        List<T> items = ServiceProvider.getProviders(groupClass);
        if (items.size() > 0) {
            for (T item : items) {
                String canonicalName = item.getClass().getCanonicalName();
                if (!TextUtils.isEmpty(canonicalName)) {
                    result.add(canonicalName.replace(".", File.separator));
                }
            }
        }
        return result;
    }

    // 排序分组
    public static List<String> getSortGroup() {
        return getGroup(ISortGroup.class);
    }
}
