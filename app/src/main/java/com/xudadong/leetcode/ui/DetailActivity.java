package com.xudadong.leetcode.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sunfusheng.code.viewer.CodeViewerFragment;
import com.xudadong.leetcode.R;
import com.xudadong.leetcode.contract.Model;
import com.xudadong.leetcode.contract.RegularModel;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private static final String KEY_MODEL = "model";
    private boolean isHidden = true;
    private CodeViewerFragment fragment;
    private Model model;

    public static Intent getDetailIntent(Context context, Model model) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_MODEL, model);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        model = (Model) getIntent().getSerializableExtra(KEY_MODEL);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(model.getTitle());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView vDesc = findViewById(R.id.tv_desc);
        if (TextUtils.isEmpty((model.getDesc()))) {
            vDesc.setVisibility(View.GONE);
            findViewById(R.id.divider).setVisibility(View.GONE);
        } else {
            vDesc.setText(model.getDesc());
        }
        final TextView vDetail = findViewById(R.id.tv_detail);
        final Button vBtn = findViewById(R.id.btn);

        vBtn.setOnClickListener(v -> {
            if (isHidden) {
                isHidden = false;
                vBtn.setText("隐藏算法");
                setFragmentVisibility(true);
                vDetail.setText(null);
                inflateDetailView(vDetail, model);
                vDetail.setVisibility(View.VISIBLE);
            } else {
                isHidden = true;
                vBtn.setText("显示算法");
                setFragmentVisibility(false);
                vDetail.setVisibility(View.GONE);
            }
        });
    }

    private void loadFragment() {
        fragment = CodeViewerFragment.Companion.instance(model.getClass().getCanonicalName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, Objects.requireNonNull(fragment));
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void setFragmentVisibility(boolean isVisible) {
        if (fragment == null) {
            if (isVisible) {
                loadFragment();
            }
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isVisible) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void inflateDetailView(TextView vDetail, Model model) {
        SpannableString keySpannableString = inflateKeywords(model.getKeywords());
        if (keySpannableString.length() > 0) {
            vDetail.append(keySpannableString);
            vDetail.append("\n\n");
        }
        if (model instanceof RegularModel) {
            RegularModel regularModel = (RegularModel) model;
            vDetail.append(inflateResult(model, regularModel.getResult(regularModel.execute(regularModel.getInput()))));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
