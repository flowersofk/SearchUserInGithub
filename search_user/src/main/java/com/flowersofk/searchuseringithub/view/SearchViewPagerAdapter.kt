package com.flowersofk.searchuseringithub.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flowersofk.searchuseringithub.fragment.LikeFragment
import com.flowersofk.searchuseringithub.fragment.SearchFragment

/**
 * Fragment ViewPager Adapter
 */
class SearchViewPagerAdapter(fm: FragmentManager?, lifecycle: Lifecycle)
    : FragmentStateAdapter(fm!!, lifecycle) {

    private val items = 2

    override fun createFragment(position: Int): Fragment {

        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = SearchFragment()  // 검색결과 Fragment
            1 -> fragment = LikeFragment()    // 좋아요 Fragment
        }

        return fragment!!

    }

    override fun getItemCount(): Int {
        return items
    }







}
