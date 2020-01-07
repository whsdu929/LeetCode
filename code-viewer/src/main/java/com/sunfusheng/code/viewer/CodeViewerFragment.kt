package com.sunfusheng.code.viewer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        private const val DEFAULT_REPO_NAME  = "LeetCode"
        private const val DEFAULT_BRANCH_NAME = "master"
        private const val DEFAULT_PATH_PREFIX = "app/src/main/java"
        private const val DEFAULT_SUFFIX = ".java"

        const val KEY_USER_NAME = "user_name"
        const val KEY_REPO_NAME = "repo_name"
        const val KEY_BRANCH_NAME = "branch_name"
        const val KEY_PATH = "path"

        fun instance(
            userName: String,
            repoName: String,
            branchName: String,
            path: String
        ): CodeViewerFragment? {
            val fragment = CodeViewerFragment()
            val bundle = Bundle()
            bundle.putString(KEY_USER_NAME, userName)
            bundle.putString(KEY_REPO_NAME, repoName)
            bundle.putString(KEY_BRANCH_NAME, branchName)
            bundle.putString(KEY_PATH, path)
            fragment.arguments = bundle
            return fragment
        }

        fun instance(canonicalClazzName: String): CodeViewerFragment? {
            val sb = StringBuffer(DEFAULT_PATH_PREFIX).append(File.separator)
                .append(canonicalClazzName.replace(".", File.separator)).append(DEFAULT_SUFFIX)
            return instance(DEFAULT_USER_NAME, DEFAULT_REPO_NAME, DEFAULT_BRANCH_NAME, sb.toString())
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
            val result = CodeFileFetcher.fetch(mCodeFilePath!!)
            val codePage = CodeHtmlGenerator.generate(context, mCodeFilePath, result)
            vCodeWebView.loadPage(codePage)
        }
    }

    override fun onDestroyView() {
        if (mJob?.isActive == true) {
            mJob?.cancel()
        }
        super.onDestroyView()
    }
}