package com.xudadong.leetcode

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xudadong.leetcode.adapter.MainAdapter
import com.xudadong.leetcode.ui.AboutActivity
import com.xudadong.leetcode.ui.BaseActivity
import com.xudadong.leetcode.utils.ParseUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var mDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActionBar(getString(R.string.app_name), false)
        initViews()
    }

    private fun initViews() {
        vRecyclerView.layoutManager = LinearLayoutManager(this)
        vRecyclerView.visibility = View.INVISIBLE
        vLoading.visibility = View.VISIBLE

        mDisposable = Observable.defer { Observable.fromArray(ParseUtil.parseData()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vRecyclerView.visibility = View.VISIBLE
                vLoading.visibility = View.INVISIBLE

                val adapter = MainAdapter(this, it)
                vRecyclerView.adapter = adapter
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_about) {
            startActivity(Intent(applicationContext, AboutActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        if (mDisposable?.isDisposed == true) {
            mDisposable?.dispose()
            mDisposable = null
        }
        super.onDestroy()
    }
}

class Bird(name: String, private var age: Int) {

    init {
        this.age = 0
    }

    init {

    }
}