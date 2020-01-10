package com.xudadong.leetcode.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.sunfusheng.code.viewer.CodeViewerFragment
import com.xudadong.leetcode.R
import com.xudadong.leetcode.contract.Model
import kotlinx.android.synthetic.main.activity_new_detail.*

class DetailActivity : BaseActivity() {

    companion object {
        const val KEY_MODEL = "key_model"

        fun instance(context: Context, model: Model): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(KEY_MODEL, model)
            return intent
        }
    }

    private var mModel: Model? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_detail)

        mModel = intent?.getSerializableExtra(KEY_MODEL) as Model
        mModel ?: finish()

        initActionBar(mModel?.title!!, true)

        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2

            override fun createFragment(position: Int): Fragment {
                return (if (position == 0) MultiTabDetailFragment.instance(mModel!!)
                else CodeViewerFragment.instance(mModel!!.javaClass.canonicalName!!))
            }
        }

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = if (position == 0) "描述" else "代码"
        }.attach()
    }
}
