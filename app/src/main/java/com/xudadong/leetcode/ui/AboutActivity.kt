package com.xudadong.leetcode.ui

import android.content.Context
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_code_viewer)

        initActionBar(getString(R.string.title_about), true)

        testLoadCodeFile()
    }

    private fun testLoadCodeFile() {
        val fileName = "algorithm/QuickSort.java"

        GlobalScope.launch(Dispatchers.Main) {
            val webView = findViewById<CodeView>(R.id.vCodeWebView)
            val htmlContent = loadCodeFile(this@AboutActivity, fileName)
            webView.loadCodeHtml(htmlContent)
        }
    }

    private suspend fun loadCodeFile(context: Context, fileName: String): String? {
        return GlobalScope.async(Dispatchers.Default) {
            val stringContent = CodeViewUtil.getStringFromAssetsFile(this@AboutActivity, fileName)
            return@async CodeHtmlGenerator.generate(
                fileName,
                stringContent,
                isNightMode = true,
                showLineNums = false
            )
        }.await()
    }
}