package com.xudadong.leetcode.pattern;

import com.xudadong.leetcode.contract.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产者消费者模式
 * <p>
 * Created by didi on 2019-08-02.
 */
public class ProducerConsumer extends Model {

    private static final int MAX_SIZE = 10;
    private List<Object> mProductList = new ArrayList<>();

    @Override
    public String getTitle() {
        return "生产者消费者模式";
    }

    class Producer extends Thread {
        @Override
        public void run() {
            super.run();
            // 下面死循环表明不只生产一个产品
            while (true) {
                synchronized (mProductList) {
                    while (mProductList.size() >= MAX_SIZE) {
                        try {
                            mProductList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mProductList.add(new Object());
                    mProductList.notifyAll();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ConsumerRunnable implements Runnable {

        @Override
        public void run() {
            // 下面死循环表明不只消费一个产品
            while (true) {
                synchronized (mProductList) {
                    while (mProductList.size() <= 0) {
                        try {
                            mProductList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mProductList.remove(0);
                    mProductList.notifyAll();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
