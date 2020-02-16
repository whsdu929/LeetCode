package com.xudadong.leetcode.algorithm.special;

import androidx.lifecycle.MutableLiveData;

import com.xudadong.leetcode.contract.Model;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 多线程顺序打印
 * <p>
 * Created by didi on 2019-08-03.
 */
public final class MultiThreadPrinter extends Model {

    private static final int THREAD_COUNT = 3;
    private static final int MIN_NUM = 1;
    private static final int MAX_NUM = 100;
    private static int mCurrNum = MIN_NUM;
    private static int mCurrThreadId = 1;

    private final Integer mLock = Integer.valueOf(13);
    private MutableLiveData<int[]> mMutableResultArray;
    private AtomicIntegerArray mAtomicResultArray;
    private volatile int mCurrIndex;
    private volatile boolean mIsPrintFinished;

    public String getTitle() {
        return "多线程顺序打印";
    }

    public String getDesc() {
        return "3个线程顺序打印数字1-100";
    }

    public MutableLiveData<int[]> fun() {
        mAtomicResultArray = new AtomicIntegerArray(MAX_NUM - MIN_NUM + 1);
        mMutableResultArray = new MutableLiveData<>();
        mCurrIndex = 0;
        mIsPrintFinished = false;

        for (int i = 1; i <= THREAD_COUNT; i++) {
            new Thread(new Printer(i)).start();
        }
        return mMutableResultArray;
    }

    class Printer implements Runnable {
        private int id;

        Printer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (mCurrNum < MAX_NUM) {
                synchronized (mLock) {
                    if (mCurrNum < MAX_NUM) {
                        if (mCurrThreadId == id) {
                            mAtomicResultArray.addAndGet(mCurrIndex++, mCurrNum);
                            if (mCurrThreadId == THREAD_COUNT) {
                                mCurrThreadId = 1;
                            } else {
                                mCurrThreadId++;
                            }
                        }
                        mLock.notifyAll();
                    }
                }
            }

            synchronized (mLock) {
                if (!mIsPrintFinished) {
                    mIsPrintFinished = true;
                    int[] resultArray = new int[mAtomicResultArray.length()];
                    for (int i = 0; i < resultArray.length; i++) {
                        resultArray[i] = mAtomicResultArray.get(i);
                    }
                    mMutableResultArray.setValue(resultArray);
                }
                mLock.notifyAll();
            }
        }
    }

    public String getResult(MutableLiveData<int[]> result) {
        StringBuffer sb = new StringBuffer();
        int[] array = result.getValue();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]).append(" ");
        }
        return sb.toString();
    }
}
