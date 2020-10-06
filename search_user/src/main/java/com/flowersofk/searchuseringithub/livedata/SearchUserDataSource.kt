package com.flowersofk.searchuseringithub.livedata

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.flowersofk.searchuseringithub.model.UserInfo
import com.flowersofk.searchuseringithub.service.SearchUserService
import com.flowersofk.searchuseringithub.service.getSearchUser

class SearchUserDataSource (
    private val q: String,
    private val service: SearchUserService
) : PageKeyedDataSource<Int, UserInfo>() {

    val total_count: MutableLiveData<Int> = MutableLiveData()
    val networkErrors: MutableLiveData<String> = MutableLiveData()

    // 리스트뷰 최초 호출
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserInfo>
    ) {

        Log.d("test", "Call SearchUserDataSource loadInitial")

        val curPage = 1
        val nextPage = curPage + 1

        getSearchUser(service, q, curPage, {count, result ->
            total_count.postValue(count)
            callback.onResult(result, null, nextPage)
        }, {error ->  networkErrors.postValue(error)})

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserInfo>) {

        val nextKey = params.key + 1

        getSearchUser(service, q, params.key, {count, result ->
            callback.onResult(result, nextKey)
        }, {error ->  networkErrors.postValue(error)})

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserInfo>) {
        TODO("Not yet implemented")
    }


}