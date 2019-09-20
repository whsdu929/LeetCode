package com.xudadong.leetcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xudadong.leetcode.adapter.MainAdapter;
import com.xudadong.leetcode.contract.Model;
import com.xudadong.leetcode.utils.ParseUtil;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        getSupportActionBar().hide();

        Toolbar vToolbar = findViewById(R.id.toolbar);
        vToolbar.setTitle(getResources().getString(R.string.app_name));

        final RecyclerView vRecyclerView = findViewById(R.id.recyclerView);
        final TextView vLoading = findViewById(R.id.loading);
        vRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vRecyclerView.setVisibility(View.INVISIBLE);
        vLoading.setVisibility(View.VISIBLE);

        disposable = Observable.defer(new Callable<ObservableSource<List<Model>>>() {
            @Override
            public ObservableSource<List<Model>> call() {
                return Observable.fromArray(ParseUtil.parseData(MainActivity.this));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Model>>() {
                    @Override
                    public void accept(List<Model> leetModels) {
                        vRecyclerView.setVisibility(View.VISIBLE);
                        vLoading.setVisibility(View.INVISIBLE);

                        MainAdapter adapter = new MainAdapter(MainActivity.this, leetModels);
                        vRecyclerView.setAdapter(adapter);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
