package com.flowersofk.searchuseringithub.livedata

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.flowersofk.searchuseringithub.model.UserInfo
import com.flowersofk.searchuseringithub.service.SearchUserService

class SearchUserFactory (
    private val q: String,
    private val service: SearchUserService
) : DataSource.Factory<Int, UserInfo>() {

    val mutableLiveData: MutableLiveData<SearchUserDataSource> = MutableLiveData<SearchUserDataSource>()

    private var searchUserDataSource: SearchUserDataSource? = null

    override fun create(): DataSource<Int, UserInfo> {

        Log.d("test", "Call SearchUserFactory create")

        searchUserDataSource = SearchUserDataSource(q, service)
        mutableLiveData.postValue(searchUserDataSource)

        return searchUserDataSource!!
    }


}