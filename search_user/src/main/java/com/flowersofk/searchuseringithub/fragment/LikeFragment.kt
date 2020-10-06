package com.flowersofk.searchuseringithub.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowersofk.searchuseringithub.R
import com.flowersofk.searchuseringithub.model.UserInfo
import com.flowersofk.searchuseringithub.view.LikeUserInfoAdapter
import com.flowersofk.searchuseringithub.viewmodel.LikeUserViewModel
import kotlinx.android.synthetic.main.fragment_like.*
import kotlinx.android.synthetic.main.fragment_like.view.*
import kotlinx.android.synthetic.main.fragment_like.view.fab_top
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * 좋아요 Fragment
 */
class LikeFragment : BaseFragment() {

    private lateinit var likeUserViewModel: LikeUserViewModel
    private lateinit var mAdapter: LikeUserInfoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_like, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        // 좋아요 리스트(likeUserLiveData)변경 관찰
        likeUserViewModel = ViewModelProvider(requireActivity()).get(LikeUserViewModel::class.java)
        likeUserViewModel.likeUserLiveData.observe(requireActivity(), Observer<List<UserInfo>> {
            mAdapter.submitList(it)
        })

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)

        // 전체검색 화면에서 변경된 데이터 UI 갱신
        menuVisible.let{
            mAdapter.notifyDataSetChanged()
        }

    }

    private fun initUI() {

        mAdapter = LikeUserInfoAdapter(requireContext())
        mAdapter.setOnClickLikeListener(object: LikeUserInfoAdapter.Companion.OnClickLikeListener {
            override fun onClick(view: View, user: UserInfo) {

                likeUserViewModel.setLikeUser(user)
                mAdapter.notifyDataSetChanged()
            }

        })

        with(rv_like_userList) {

            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
            adapter = mAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener(){

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    // 스크롤 상태에 따라 '최상단 버튼' Show/Hide 처리
                    when(newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> fab_top.show()
                        else -> {
                            fab_top.hide()
                            HideKeyboard(recyclerView)
                        }
                    }
                }
            })
        }

        // 최상단 이동 FAB
        fab_top.setOnClickListener {
            rv_like_userList.scrollToPosition(0)
        }

    }

}