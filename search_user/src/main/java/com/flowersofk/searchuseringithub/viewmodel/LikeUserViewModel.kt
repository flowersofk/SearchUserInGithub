package com.flowersofk.searchuseringithub.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flowersofk.searchuseringithub.model.UserInfo
import com.flowersofk.searchuseringithub.view.LikeUserInfoAdapter.Companion.selectedList

/**
 * 좋아요 ViewModel 정의
 */
class LikeUserViewModel : ViewModel() {

    private val likeUserList = ArrayList<UserInfo>()

    val likeUserLiveData = MutableLiveData<List<UserInfo>>()

    /**
     * 좋아요 등록/삭제
     */
    fun setLikeUser(user: UserInfo) {

//        val _likeUserList = (likeUserLiveData.value as? List<UserInfo>)?.toMutableList()

        if(user.isFavorite) {
            likeUserList.add(user)
        } else {
            likeUserList.remove(user)
        }

        likeUserLiveData.postValue(likeUserList)

    }

    /**
     * 좋아요 사용자 검색
     */
    fun searchLikeUser(name: String) {

        // 좋아요 검색어가 없을경우 좋아요 전체리스트 출력
        if(name.isNullOrEmpty()) {
            likeUserLiveData.postValue(likeUserList)
        } else {

            selectedList[name]?.let {
                var result = ArrayList<UserInfo>()
                result.add(it)
                likeUserLiveData.postValue(result)
            }

        }

    }

}