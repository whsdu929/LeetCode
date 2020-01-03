package com.sunfusheng.code.viewer

import android.content.Context
import android.webkit.MimeTypeMap

/**
 * @author sunfusheng
 * @since 2020-01-03
 */
object CodeHtmlGenerator {

    fun generate(context: Context?, codeFilePath: String?, sourceCode: String?): String? {
        if (context == null || sourceCode?.length == 0) return null

        val extension = MimeTypeMap.getFileExtensionFromUrl(codeFilePath)
        val whiteColor = context.applicationContext.resources.getColor(android.R.color.white)
        val backgroundColor = "#${Integer.toHexString(whiteColor)}"
        return generateCodeHtml(sourceCode!!, extension, backgroundColor, false, true)
    }

    private fun generateCodeHtml(
        sourceCode: String,
        extension: String?,
        backgroundColor: String,
        wrap: Boolean,
        lineNums: Boolean
    ): String? {
        return "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\" />\n" +
                "<title>Code Viewer</title>\n" +
                "<meta name=\"viewport\" content=\"width=device-width; initial-scale=1.0;\"/>" +
                "<script src=\"./core/run_prettify.js?autoload=true&amp;skin=prettify&amp;" +
                "lang=" + extension + "&amp;\" defer></script>\n" +
                "<style>" +
                "body {background: " + backgroundColor + ";}" +
                ".prettyprint {background: " + backgroundColor + ";}" +
                "pre.prettyprint {" +
                " word-wrap: " + (if (wrap) "break-word" else "normal") + "; " +
                " white-space: " + (if (wrap) "pre-wrap" else "no-wrap") + "; " +
                "}" +
                "</style>" +
                "</head>\n" +
                "<body>\n" +
                "<?prettify lang=" + extension + " linenums=" + lineNums + "?>\n" +
                "<pre class=\"prettyprint\">\n" +
                sourceCode.replace("<", "&lt;").replace(">", "&gt;") +
                "</pre>\n" +
                "</body>\n" +
                "</html>"
    }
}