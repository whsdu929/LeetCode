package com.xudadong.leetcode.pattern;

import com.xudadong.leetcode.contract.Model;

/**
 * 单例模式的两种实现
 * <p>
 * Created by didi on 2019-08-02.
 */
public final class SingletonInstance extends Model {

    // 使用volatile修饰是防止19行出现 mInstance不为null，但依然没有完成初始化的情况
    private static volatile SingletonInstance mInstance = null;

//    private SingletonInstance() {}

    @Override
    public String getTitle() {
        return "单例模式";
    }

    /**
     * 第一种实现
     */
    public static SingletonInstance getInstance() {
        if (mInstance == null) {
            synchronized (SingletonInstance.class) {
                // 下面再判空是防止出现 线程1已经判断mInstance == null走到20行等待锁释放，而线程2已经在创建对象了
                if (mInstance == null) {
                    mInstance = new SingletonInstance();
                }
            }
        }
        return mInstance;
    }

    /**
     * 第二种实现
     *
     * 假设线程A和B都试图访问getInstance2()，线程A先使用了静态类SingletonHolder的静态字段INSTANCE，
     * 因此SingletonHolder这个类被初始化了，JVM会保证这个过程是线程互斥的，保证只初始化一次。
     * 而SingletonHolder这个类只有在调getInstance2()方法时才被初始化，因而也相当于是懒加载了。
     */
    private static class SingletonHolder {
        private static SingletonInstance mInstance = new SingletonInstance();
    }

    public static SingletonInstance getInstance2() {
        return SingletonHolder.mInstance;
    }
}
