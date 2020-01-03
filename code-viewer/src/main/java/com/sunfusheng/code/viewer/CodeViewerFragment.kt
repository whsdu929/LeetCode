package com.sunfusheng.code.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_code_viewer.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author sunfusheng
 * @since 2020-01-03
 */
class CodeViewerFragment : Fragment() {

    companion object {
        fun instance(codeFilePath: String): CodeViewerFragment? {
            val fragment = CodeViewerFragment()
            val bundle = Bundle()
            bundle.putString("codeFilePath", codeFilePath)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var codeFilePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        codeFilePath = arguments?.getString("codeFilePath")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_code_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCodeFile()
    }

    private var mJob: Job? = null

    private fun fetchCodeFile() {
        mJob = GlobalScope.launch(Dispatchers.Main) {
            val result = CodeFileFetcher.fetch(codeFilePath!!)
            val codePage = CodeHtmlGenerator.generate(context, codeFilePath, result)
            vCodeWebView.loadPage(codePage)
        }
    }

    override fun onDestroy() {
        if (mJob?.isActive == true) {
            mJob?.cancel()
        }
        super.onDestroy()
    }
}