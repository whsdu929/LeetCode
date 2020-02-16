package com.xudadong.leetcode.algorithm.special;

import android.content.Context;
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
public class DepthOfViewGroup extends Model {

    public String getTitle() {
        return "ViewGroup的深度";
    }

    public String getDesc() {
        return "一个ViewGroup A，嵌套 View B1 和 ViewGroup B2，B2 又嵌套 ViewGroup C，求 A 的深度";
    }

    public ViewGroup getInput(Context context) {
        FrameLayout A = new FrameLayout(context);
        TextView B1 = new TextView(context);
        LinearLayout B2 = new LinearLayout(context);
        RelativeLayout C = new RelativeLayout(context);
        B2.addView(C);
        A.addView(B1);
        A.addView(B2);
        return A;
    }

    public int fun(ViewGroup input) {
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

    public String getResult(Integer result) {
        return "深度: " + result;
    }
}
