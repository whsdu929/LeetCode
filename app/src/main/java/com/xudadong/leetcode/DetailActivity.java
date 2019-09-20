package com.xudadong.leetcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xudadong.leetcode.contract.Model;

public class DetailActivity extends AppCompatActivity {

    private static final String KEY_MODEL = "model";

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

        vBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDetail.setText(getResult(model, model.getResult(model.fun(model.getInput()))));
                vBtn.setEnabled(false);
            }
        });
    }

    private String getResult(Model model, String string) {
        StringBuffer sb = new StringBuffer("\n");
        sb.append("\n").append("输出结果: ").append(string).append("\n");
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
        return sb.toString();
    }
}
