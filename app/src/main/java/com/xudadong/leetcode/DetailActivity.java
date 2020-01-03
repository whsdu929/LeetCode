package com.xudadong.leetcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xudadong.leetcode.contract.Model;
import com.xudadong.leetcode.contract.RegularModel;

public class DetailActivity extends AppCompatActivity {

    private static final String KEY_MODEL = "model";
    private boolean isHidden = true;

    public static Intent getDetailIntent(Context context, Model model) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_MODEL, model);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Model model = (Model) getIntent().getSerializableExtra(KEY_MODEL);
        setTitle(model.getTitle());

        TextView vDesc = findViewById(R.id.tv_desc);
        vDesc.setText(model.getDesc());
        final TextView vDetail = findViewById(R.id.tv_detail);
        final Button vBtn = findViewById(R.id.btn);
        final ImageView vIv = findViewById(R.id.iv);

        vBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    isHidden = false;
                    vBtn.setText("隐藏算法");
                    if (model.getCodeDrawable(DetailActivity.this) != null) {
                        vIv.setVisibility(View.VISIBLE);
                        vIv.setImageDrawable(model.getCodeDrawable(DetailActivity.this));
                    } else {
                        vIv.setVisibility(View.GONE);
                    }
                    vDetail.setText(null);
                    inflateDetailView(vDetail, model);
                    vDetail.setVisibility(View.VISIBLE);
                } else {
                    isHidden = true;
                    vBtn.setText("显示算法");
                    vIv.setVisibility(View.GONE);
                    vDetail.setVisibility(View.GONE);
                }
            }
        });
    }

    private void inflateDetailView(TextView vDetail, Model model) {
        SpannableString keySpannableString = inflateKeywords(model.getKeywords());
        if (keySpannableString.length() > 0) {
            vDetail.append(keySpannableString);
            vDetail.append("\n\n");
        }
        if (model instanceof RegularModel) {
            RegularModel regularModel = (RegularModel) model;
            vDetail.append(inflateResult(model, regularModel.getResult(regularModel.fun(regularModel.getInput()))));
        }
    }

    private SpannableString inflateKeywords(String[] keywords) {
        if (keywords != null && keywords.length > 0) {
            String prefix = "算法提示: ";
            String div = ", ";
            StringBuffer sb = new StringBuffer(prefix);
            for (String keyword : keywords) {
                sb.append(keyword).append(div);
            }
            String str = sb.substring(0, sb.length() - div.length());
            return createSpannableString(str, prefix.length(), str.length());
        }
        return new SpannableString("");
    }

    private SpannableString inflateResult(Model model, String string) {
        StringBuffer sb = new StringBuffer().append("输出结果: ");
        int start = sb.length();
        sb.append(string);
        int end = sb.length();
        sb.append("\n");
        if (model.getTimeComplexity() != null) {
            sb.append("\n").append("时间复杂度: ").append(model.getTimeComplexity().toString()).append("\n");
            if (model.getTimeComplexity().bestComplexity != null) {
                sb.append("最优时间复杂度: ").append(model.getTimeComplexity().bestComplexity.toString()).append("\n");
            }
            if (model.getTimeComplexity().worstComplexity != null) {
                sb.append("最差时间复杂度: ").append(model.getTimeComplexity().worstComplexity.toString()).append("\n");
            }
        }
        if (model.getSpaceComplexity() != null) {
            sb.append("\n").append("空间复杂度: ").append(model.getSpaceComplexity().toString()).append("\n");
            if (model.getSpaceComplexity().bestComplexity != null) {
                sb.append("最优空间复杂度: ").append(model.getSpaceComplexity().bestComplexity.toString()).append("\n");
            }
            if (model.getSpaceComplexity().worstComplexity != null) {
                sb.append("最差空间复杂度: ").append(model.getSpaceComplexity().worstComplexity.toString()).append("\n");
            }
        }
        return createSpannableString(sb.toString(), start, end);
    }

    private SpannableString createSpannableString(String txt, int start, int end) {
        SpannableString spannableString = new SpannableString(txt);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#228B22"));
        spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
