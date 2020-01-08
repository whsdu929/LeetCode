package com.xudadong.leetcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xudadong.leetcode.adapter.MainAdapter;
import com.xudadong.leetcode.contract.Model;
import com.xudadong.leetcode.utils.ParseUtil;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
        }

        final RecyclerView vRecyclerView = findViewById(R.id.recyclerView);
        final TextView vLoading = findViewById(R.id.loading);
        vRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vRecyclerView.setVisibility(View.INVISIBLE);
        vLoading.setVisibility(View.VISIBLE);

        mDisposable = Observable.defer((Callable<ObservableSource<List<Model>>>) () -> Observable.fromArray(ParseUtil.parseData()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(leetModels -> {
                    vRecyclerView.setVisibility(View.VISIBLE);
                    vLoading.setVisibility(View.INVISIBLE);

                    MainAdapter adapter = new MainAdapter(this, leetModels);
                    vRecyclerView.setAdapter(adapter);
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }
}
