package com.xudadong.leetcode.ui

import android.os.Bundle
import com.xudadong.leetcode.R

/**
 * @author sunfusheng
 * @since 2020-01-09
 */
class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_framelayout)

        initActionBar(getString(R.string.title_about), true)
    }

}