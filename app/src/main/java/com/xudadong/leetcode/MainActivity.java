package com.xudadong.leetcode;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
        vToolbar.setTitleTextColor(Color.WHITE);
        vToolbar.setTitle(getResources().getString(R.string.app_name));

        final RecyclerView vRecyclerView = findViewById(R.id.recyclerView);
        final TextView vLoading = findViewById(R.id.loading);
        vRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vRecyclerView.setVisibility(View.INVISIBLE);
        vLoading.setVisibility(View.VISIBLE);

        disposable = Observable.defer((Callable<ObservableSource<List<Model>>>) () -> Observable.fromArray(ParseUtil.parseData()))
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
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
