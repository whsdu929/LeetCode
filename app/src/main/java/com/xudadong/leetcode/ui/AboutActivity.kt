package com.xudadong.leetcode.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.sunfusheng.codeviewer.CodeHtmlGenerator
import com.sunfusheng.codeviewer.CodeView
import com.sunfusheng.codeviewer.CodeViewUtil
import com.xudadong.leetcode.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * @author sunfusheng
 * @since 2020-01-09
 */
class AboutActivity : BaseActivity() {
    private val mAssetsFilePath = "algorithm/QuickSort.java"
    private var mSourceCode: String? = null

    private var isNightMode: Boolean = true
    private var showLineNums: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_code_viewer)

        initActionBar(getString(R.string.title_about), true)
        loadSampleCode()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_source_code, menu)
        menu.getItem(0).isChecked = isNightMode
        menu.getItem(1).isChecked = showLineNums
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_night_mode -> {
                isNightMode = !isNightMode
                item.isChecked = isNightMode
                loadSampleCode()
                return true
            }
            R.id.item_show_linenums -> {
                showLineNums = !showLineNums
                item.isChecked = showLineNums
                loadSampleCode()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 加载Assets下的示例代码
    private fun loadSampleCode() {
        GlobalScope.launch(Dispatchers.Main) {
            val vCodeView = findViewById<CodeView>(R.id.vCodeView)
            val htmlContent = loadCodeFile(applicationContext, mAssetsFilePath)
            vCodeView.loadCodeHtml(htmlContent)
        }
    }

    private suspend fun loadCodeFile(context: Context, fileName: String): String? {
        return GlobalScope.async(Dispatchers.Default) {
            if (mSourceCode == null) {
                mSourceCode = CodeViewUtil.getStringFromAssetsFile(
                    applicationContext,
                    mAssetsFilePath
                )
            }
            return@async CodeHtmlGenerator.generate(
                mAssetsFilePath,
                mSourceCode,
                isNightMode,
                showLineNums
            )
        }.await()
    }
}