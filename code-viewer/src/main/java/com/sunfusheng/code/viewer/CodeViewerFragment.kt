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
        const val USER_NAME = "user_name"
        const val REPO_NAME = "repo_name"
        const val BRANCH_NAME = "branch_name"
        const val PATH = "path"

        fun instance(
            userName: String,
            repoName: String,
            branchName: String,
            path: String
        ): CodeViewerFragment? {
            val fragment = CodeViewerFragment()
            val bundle = Bundle()
            bundle.putString(USER_NAME, userName)
            bundle.putString(REPO_NAME, repoName)
            bundle.putString(BRANCH_NAME, branchName)
            bundle.putString(PATH, path)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mCodeFilePath: String? = null
    private var mJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp: StringBuilder = StringBuilder("https://raw.githubusercontent.com/")
        temp.append(arguments?.getString(USER_NAME)).append(File.separator)
        temp.append(arguments?.getString(REPO_NAME)).append(File.separator)
        temp.append(arguments?.getString(BRANCH_NAME)).append(File.separator)
        temp.append(arguments?.getString(PATH))

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