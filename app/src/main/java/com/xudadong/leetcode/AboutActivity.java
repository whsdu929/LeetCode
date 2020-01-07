package com.xudadong.leetcode;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sunfusheng.code.viewer.CodeViewerFragment;

import java.util.Objects;

/**
 * @author sunfusheng
 * @since 2020-01-03
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_framelayout);

        initActionBar();
        loadFragment();
    }

    void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_about);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    void loadFragment() {
        CodeViewerFragment codeViewerFragment = CodeViewerFragment.Companion.instance(
                "whsdu929",
                "LeetCode",
                "master",
                "app/src/main/java/com/xudadong/leetcode/arithmetic/BigNumberPlus.java"
        );

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, Objects.requireNonNull(codeViewerFragment));
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
