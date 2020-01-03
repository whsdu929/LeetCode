package com.sunfusheng.code.viewer

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView

/**
 * @author sunfusheng
 * @since 2020-01-03
 */
@SuppressLint("SetJavaScriptEnabled")
class CodeWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    init {
        setBackgroundColor(resources.getColor(android.R.color.white))
        val settings = settings
        settings.javaScriptEnabled = true
        settings.setAppCachePath(context.cacheDir.path)
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        settings.defaultTextEncodingName = "utf-8"
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        setOnLongClickListener { v: View? -> false }
    }

    fun loadPage(page: String?) {
        if (page?.length == 0) return

        post {
            loadDataWithBaseURL(
                "file:///android_asset/code_prettify/",
                page,
                "text/html",
                "utf-8",
                null
            )
        }
    }
}