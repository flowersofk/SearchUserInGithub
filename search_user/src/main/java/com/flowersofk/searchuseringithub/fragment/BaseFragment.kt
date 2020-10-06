package com.flowersofk.searchuseringithub.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

/**
 * 공통 Fragment
 */
open class BaseFragment : Fragment() {

//    lateinit var contentView: View
    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return contentView
//
//    }
//
//    fun setContentView(layoutId: Int) {
//        contentView =  View.inflate(activity, layoutId, null)
//    }

    override fun getContext() : Context {
        return mContext
    }

    /**
     * 키보드 숨김처리 (스크롤 시)
     */
    fun HideKeyboard(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}