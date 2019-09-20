package com.xudadong.leetcode.arithmetic;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xudadong.leetcode.contract.Model;

import java.util.LinkedList;

/**
 * ViewGroup的深度
 * <p>
 * Created by didi on 2019-07-13.
 */
public class DepthOfViewGroup extends Model<ViewGroup, Integer> {

    @Override
    public String getTitle() {
        return "ViewGroup的深度";
    }

    @Override
    public String getDesc() {
        return "一个ViewGroup A，嵌套 View B1 和 ViewGroup B2，B2 又嵌套 ViewGroup C，求 A 的深度";
    }

    @Override
    public ViewGroup getInput() {
        FrameLayout A = new FrameLayout(mArgs.mApplicationContext);
        TextView B1 = new TextView(mArgs.mApplicationContext);
        LinearLayout B2 = new LinearLayout(mArgs.mApplicationContext);
        RelativeLayout C = new RelativeLayout(mArgs.mApplicationContext);
        B2.addView(C);
        A.addView(B1);
        A.addView(B2);
        return A;
    }

    @Override
    public Integer fun(ViewGroup input) {
        int depth = 0;
        LinkedList<View> queue = new LinkedList<>();
        queue.add(input);

        while (!queue.isEmpty()) {
            depth++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                View view = queue.removeFirst();
                ViewGroup tmp;
                if (view instanceof ViewGroup && (tmp = (ViewGroup) view).getChildCount() > 0) {
                    for (int j = 0; j < tmp.getChildCount(); j++) {
                        queue.addLast(tmp.getChildAt(j));
                    }
                }
            }
        }

        return depth;
    }

    @Override
    public String getResult(Integer result) {
        return "深度: " + result;
    }
}
