package com.flowersofk.searchuseringithub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.flowersofk.searchuseringithub.livedata.SearchUserLiveData
import com.flowersofk.searchuseringithub.livedata.SearchUserRepository
import com.flowersofk.searchuseringithub.model.UserInfo
import com.flowersofk.searchuseringithub.network.SearchUserRequest

class SearchUserViewModel : ViewModel() {

    private val repository: SearchUserRepository = SearchUserRepository()

    private val queryLiveData = MutableLiveData<SearchUserRequest>()

    private val response: LiveData<SearchUserLiveData> = Transformations.map(queryLiveData, { repository.search(it) })

    val resultList: LiveData<PagedList<UserInfo>> = Transformations.switchMap(response, { it -> it.result })

    val networkErrors: LiveData<String> = Transformations.switchMap(response, { it -> it.networkErrors })

    val total_count: LiveData<Int> = Transformations.switchMap(response, { it -> it.count })

    /**
     * 유저 검색 요청
     */
    fun searchUserInfo(request: SearchUserRequest) {
        Log.d("test", "Call SearchUserViewModel searchUserInfo")
        queryLiveData.postValue(request)

    }

}