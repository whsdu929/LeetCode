package com.xudadong.leetcode.ui

import android.content.Context
import android.os.Bundle
import com.sunfusheng.code.viewer.CodeHtmlGenerator
import com.sunfusheng.code.viewer.CodeWebView
import com.xudadong.leetcode.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader


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
            val webView = findViewById<CodeWebView>(R.id.vCodeWebView)
            val htmlContent = loadCodeFile(this@AboutActivity, fileName)
            webView.loadPage(htmlContent)
        }
    }

    private suspend fun loadCodeFile(context: Context, fileName: String): String? {
        return GlobalScope.async(Dispatchers.Default) {
            val stringContent = getStringFromAssetsFile(fileName)
            return@async CodeHtmlGenerator.generate(context, fileName, stringContent)
        }.await()
    }

    private fun getStringFromAssetsFile(fileName: String): String {
        val bufferedReader = BufferedReader(InputStreamReader(assets.open(fileName), "UTF-8"))
        val content = StringBuffer()
        var line: String? = null
        while (bufferedReader.readLine()?.also { line = it } != null) {
            content.append(line)
            content.append("\n")
        }
        return content.toString()
    }
}