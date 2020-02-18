package com.xudadong.leetcode.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sunfusheng.codeviewer.CodeHtmlGenerator
import com.xudadong.leetcode.R
import com.xudadong.leetcode.utils.CodeFileFetcher
import kotlinx.android.synthetic.main.fragment_code_viewer.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

/**
 * @author sunfusheng
 * @since 2020-01-03
 */
class CodeViewerFragment : Fragment() {

    companion object {
        private const val DEFAULT_USER_NAME = "whsdu929"
        private const val DEFAULT_REPO_NAME = "LeetCode"
        private const val DEFAULT_BRANCH_NAME = "master"

        const val KEY_USER_NAME = "user_name"
        const val KEY_REPO_NAME = "repo_name"
        const val KEY_BRANCH_NAME = "branch_name"
        const val KEY_PATH = "path"

        fun instance(
            userName: String = DEFAULT_USER_NAME,
            repoName: String = DEFAULT_REPO_NAME,
            branchName: String = DEFAULT_BRANCH_NAME,
            canonicalClazzPath: String = ""
        ): CodeViewerFragment {
            val fragment = CodeViewerFragment()
            val path = "app/src/main/java/${canonicalClazzPath.replace(".", File.separator)}.java"
            fragment.arguments = bundleOf(
                KEY_USER_NAME to userName,
                KEY_REPO_NAME to repoName,
                KEY_BRANCH_NAME to branchName,
                KEY_PATH to path
            )
            return fragment
        }
    }

    private var mCodeFilePath: String? = null
    private var mJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp: StringBuilder = StringBuilder("https://raw.githubusercontent.com/")
        temp.append(arguments?.getString(KEY_USER_NAME)).append(File.separator)
        temp.append(arguments?.getString(KEY_REPO_NAME)).append(File.separator)
        temp.append(arguments?.getString(KEY_BRANCH_NAME)).append(File.separator)
        temp.append(arguments?.getString(KEY_PATH))

        mCodeFilePath = temp.toString()
        Log.d("sfs", "mCodeFilePath: " + mCodeFilePath)
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

    private fun fetchCodeFile() {
        mJob = GlobalScope.launch(Dispatchers.Main) {
            val result =
                CodeFileFetcher.fetch(mCodeFilePath!!)
            val codePage = CodeHtmlGenerator.generate(
                mCodeFilePath,
                result!!
            )
            vCodeWebView.loadCodeHtml(codePage)
        }
    }

    override fun onDestroyView() {
        if (mJob?.isActive == true) {
            mJob?.cancel()
        }
        super.onDestroyView()
    }
}