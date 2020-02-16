package com.xudadong.leetcode.ui

import android.os.Bundle
import android.util.Log
import com.sunfusheng.code.viewer.CodeHtmlGenerator
import com.sunfusheng.code.viewer.CodeWebView
import com.xudadong.leetcode.R
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
        val webView: CodeWebView = findViewById(R.id.vCodeWebView)
        val start = System.currentTimeMillis()

        val fileName = "algorithm/QuickSort.java"
        val stringContent = getStringFromAssetFile(fileName)
        val htmlContent = CodeHtmlGenerator.generate(this, fileName, stringContent)

        Log.w("sfs", "time consumed: " + (System.currentTimeMillis() - start))
        webView.loadPage(htmlContent)
    }

    private fun getStringFromAssetFile(fileName: String): String {
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