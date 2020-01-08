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

    // 根据分组标志接口，获取分组算法类路径列表
    public static <T extends IGroup> List<String> getGroup(Class<T> groupClass) {
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

    // 根据分组标志接口，获取分组名字
    public static <T extends IGroup> String getGroupName(Class<T> groupClass) {
        try {
            T groupClassImpl = ServiceProvider.getProvider(groupClass);
            if (groupClassImpl == null) {
                throw new IllegalArgumentException("You should at least provide an implementation class for " + groupClass.getSimpleName() + " interface!");
            }
            IGroup groupClassImplInstance = groupClassImpl.getClass().newInstance();
            return groupClassImplInstance.getGroupName();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // 排序分组
    public static List<String> getSortGroup() {
        return getGroup(ISortGroup.class);
    }
}
