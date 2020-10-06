package com.flowersofk.searchuseringithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.flowersofk.searchuseringithub.network.SearchUserRequest
import com.flowersofk.searchuseringithub.utils.StringUtils
import com.flowersofk.searchuseringithub.view.SearchViewPagerAdapter
import com.flowersofk.searchuseringithub.viewmodel.LikeUserViewModel
import com.flowersofk.searchuseringithub.viewmodel.SearchUserViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 메인 Activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var searchUserViewModel: SearchUserViewModel
    private lateinit var likeUserViewModel: LikeUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        initVM()

    }

    private fun initVM() {

        likeUserViewModel = ViewModelProvider(this).get(LikeUserViewModel::class.java)

        searchUserViewModel = ViewModelProvider(this).get(SearchUserViewModel::class.java)
        searchUserViewModel.total_count.observe(this, Observer<Int> {
            // 총 검색 결과 출력
            tv_totalCount.text = "${StringUtils.getNumberFormat(it)} Result"
        })

        searchUserViewModel.networkErrors.observe(this, Observer<String> {
            // 오류 발생 시 Toast 출력
            makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }

    private fun initUI() {

        // 에디트텍스트 Search Action 설정
        et_keyword.setOnEditorActionListener { v, actionId, event ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {

                    et_keyword.text.toString()?.let {

                        when(viewPager.currentItem) {

                            // 전체검색 요청
                            0 -> searchUserViewModel.searchUserInfo(SearchUserRequest(it))

                            1 -> likeUserViewModel.searchLikeUser(it)

                        }

                    }

                    true
                }
                else -> false
            }

        }

        viewPager.adapter = SearchViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout!!, viewPager!!, TabLayoutMediator.TabConfigurationStrategy{tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_result)
                1 -> tab.text = getString(R.string.tab_like)
            }
        }).attach()

        // Sort Spinner 초기화
//        spinner_sort.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item)
//        spinner_sort.onItemSelectedListener= object: AdapterView.OnItemSelectedListener {
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//
//                val selectedItem = parent?.getItemAtPosition(position).toString()
//
//                // 중복호출 방지
////                if(filter.equals(selectedItem)) {
////                    return
////                }
//
//                when(position) {
//                    0 -> filter = "All"                                   // 전체검색
//                    else -> filter = selectedItem                      // collection 필터값
//
//                }
//
//                requestImage()
//            }
//        }
    }

}