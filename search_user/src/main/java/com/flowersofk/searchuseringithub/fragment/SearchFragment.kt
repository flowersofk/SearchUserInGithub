package com.flowersofk.searchuseringithub.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowersofk.searchuseringithub.R
import com.flowersofk.searchuseringithub.model.UserInfo
import com.flowersofk.searchuseringithub.view.SearchUserInfoAdapter
import com.flowersofk.searchuseringithub.viewmodel.LikeUserViewModel
import com.flowersofk.searchuseringithub.viewmodel.SearchUserViewModel
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * 검색결과 Fragment
 */
class SearchFragment : BaseFragment() {

    private lateinit var searchUserViewModel: SearchUserViewModel
    private lateinit var likeUserViewModel: LikeUserViewModel
    private lateinit var mAdapter: SearchUserInfoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initVM()

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)

        // 전체검색 화면에서 변경된 데이터 UI 갱신
        menuVisible.let{
            mAdapter.notifyDataSetChanged()
        }

    }

    private fun initVM() {

        likeUserViewModel = ViewModelProvider(requireActivity()).get(LikeUserViewModel::class.java)

        searchUserViewModel = ViewModelProvider(requireActivity()).get(SearchUserViewModel::class.java)
        searchUserViewModel.resultList.observe(requireActivity(), Observer<PagedList<UserInfo>> {
            mAdapter.submitList(it)
        })

    }

    private fun initUI() {

        mAdapter = SearchUserInfoAdapter(requireContext())
        mAdapter.setOnClickLikeListener(object: SearchUserInfoAdapter.Companion.OnClickLikeListener {
            override fun onClick(view: View, user: UserInfo) {
                // 좋아요 등록
                likeUserViewModel.setLikeUser(user)
            }
        })

        with(rv_userList) {

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
            rv_userList.scrollToPosition(0)
        }

    }

}