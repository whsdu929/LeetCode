package com.xudadong.leetcode.ui

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.xudadong.leetcode.R
import com.xudadong.leetcode.contract.Model
import com.xudadong.leetcode.contract.RegularModel
import kotlinx.android.synthetic.main.fragment_detail_desc.*
import java.io.Serializable

/**
 * @author sunfusheng
 * @since 2020-01-10
 */
class MultiTabDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_desc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val model: Model = arguments?.getSerializable(DetailActivity.KEY_MODEL) as Model
        if (model.desc.isNotBlank()) vDesc.text = model.desc
        else {
            vDesc.visibility = View.GONE
            vDivider.visibility = View.GONE
        }
        inflateDetailView(vResult, model)
    }

    private fun inflateDetailView(vDetail: TextView, model: Model) {
        val keySpannableString = inflateKeywords(model.keywords)
        if (keySpannableString.isNotEmpty()) {
            vDetail.append(keySpannableString)
            vDetail.append("\n\n")
        }
        if (model is RegularModel<*, *>) {
            val regularModel = model as RegularModel<Serializable?, Serializable?>
            vDetail.append(
                inflateResult(
                    model,
                    regularModel.getResult(regularModel.execute(regularModel.input))
                )
            )
        }
    }

    private fun inflateKeywords(keywords: Array<String>?): SpannableString {
        if (keywords != null && keywords.isNotEmpty()) {
            val prefix = "算法提示: "
            val div = ", "
            val sb = StringBuffer(prefix)
            for (keyword in keywords) {
                sb.append(keyword).append(div)
            }
            val str = sb.substring(0, sb.length - div.length)
            return createSpannableString(str, prefix.length, str.length)
        }
        return SpannableString("")
    }

    private fun inflateResult(model: Model, string: String): SpannableString {
        val sb = StringBuffer().append("代码执行结果: ")
        val start = sb.length
        sb.append(string)
        val end = sb.length
        sb.append("\n")
        if (model.timeComplexity != null) {
            sb.append("\n").append("时间复杂度: ").append(model.timeComplexity!!.toString())
                .append("\n")
            if (model.timeComplexity!!.bestComplexity != null) {
                sb.append("最优时间复杂度: ").append(model.timeComplexity!!.bestComplexity.toString())
                    .append("\n")
            }
            if (model.timeComplexity!!.worstComplexity != null) {
                sb.append("最差时间复杂度: ").append(model.timeComplexity!!.worstComplexity.toString())
                    .append("\n")
            }
        }
        if (model.spaceComplexity != null) {
            sb.append("\n").append("空间复杂度: ").append(model.spaceComplexity!!.toString())
                .append("\n")
            if (model.spaceComplexity!!.bestComplexity != null) {
                sb.append("最优空间复杂度: ").append(model.spaceComplexity!!.bestComplexity.toString())
                    .append("\n")
            }
            if (model.spaceComplexity!!.worstComplexity != null) {
                sb.append("最差空间复杂度: ")
                    .append(model.spaceComplexity!!.worstComplexity.toString()).append("\n")
            }
        }
        return createSpannableString(sb.toString(), start, end)
    }

    private fun createSpannableString(txt: String, start: Int, end: Int): SpannableString {
        val spannableString = SpannableString(txt)
        val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#228B22"))
        spannableString.setSpan(
            foregroundColorSpan,
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

    companion object {
        fun instance(model: Model): MultiTabDetailFragment {
            val fragment = MultiTabDetailFragment()
            fragment.arguments = bundleOf(
                DetailActivity.KEY_MODEL to model
            )
            return fragment
        }
    }
}