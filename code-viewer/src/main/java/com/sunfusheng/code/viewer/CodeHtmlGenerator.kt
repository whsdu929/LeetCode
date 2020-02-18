package com.sunfusheng.code.viewer

import android.text.TextUtils
import android.webkit.MimeTypeMap

/**
 * @author sunfusheng
 * @since 2020-01-03
 */
object CodeHtmlGenerator {
    const val DAY_MODE_COLOR = "#ffffff"
    const val NIGHT_MODE_COLOR = "#373a41"

    fun getFileExtension(filePath: String?): String? {
        return MimeTypeMap.getFileExtensionFromUrl(filePath)
    }

    fun generate(
        filePath: String?,
        sourceCode: String?,
        isNightMode: Boolean = false,
        showLineNums: Boolean = false
    ): String? {
        val extension = getFileExtension(filePath)
        val code = if (TextUtils.isEmpty(sourceCode)) {
            "No Data!"
        } else {
            sourceCode!!.replace("<", "&lt;").replace(">", "&gt;")
        }
        val backgroundColor = if (isNightMode) NIGHT_MODE_COLOR else DAY_MODE_COLOR
        val skin = if (isNightMode) "desert" else "prettify"

        return """
            <html>
                <head>
                    <meta charset="utf-8" />
                    <title>Code Viewer</title>
                    <meta name="viewport" content="width=device-width; initial-scale=1.0;"/>
                    <script src="./run_prettify.js?autoload=true&amp;skin=$skin&amp;lang=$extension&amp;" defer></script>
                    <style>
                        body {background: $backgroundColor;}
                        pre.prettyprint {background: $backgroundColor;}
                        pre.prettyprint {word-wrap: "normal";white-space: "no-wrap";}
                    </style>
                </head>
                <body>
                    <?prettify lang=$extension linenums=$showLineNums?>
                    <pre class="prettyprint">
$code
                    </pre>
                </body>
            </html>
        """
    }
}