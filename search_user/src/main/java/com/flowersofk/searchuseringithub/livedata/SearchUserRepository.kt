package com.flowersofk.searchuseringithub.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.flowersofk.searchuseringithub.Constants.PAGE_SIZE
import com.flowersofk.searchuseringithub.livedata.SearchUserLiveData
import com.flowersofk.searchuseringithub.model.UserInfo
import com.flowersofk.searchuseringithub.network.SearchUserRequest
import com.flowersofk.searchuseringithub.service.SearchUserService
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SearchUserRepository {

    private val service: SearchUserService = SearchUserService.create()
    private val executor: Executor = Executors.newFixedThreadPool(5)

    fun search(request: SearchUserRequest): SearchUserLiveData {

        Log.d("test", "Call SearchUserRepository search")

        val dataSourceFactory = SearchUserFactory(request.q, service)

        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)                 // 페이지 기본 사이즈
            .setInitialLoadSizeHint(PAGE_SIZE)      // 최초 호출시 사이즈
            .setPrefetchDistance(10)                // 호출 시점이 맨 마지막 행에서 얼마만큼 떨어져있는지 설정(ex. 10이면 맨 마지막에서 위로 10번재 스크롤시점에 호출
            .setEnablePlaceholders(true)
            .build()

        // 라이브 데이터 생성
        val data: LiveData<PagedList<UserInfo>> = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
            .setFetchExecutor(executor)
            .build()

        val networkErrors = Transformations.switchMap(dataSourceFactory.mutableLiveData, { dataSource -> dataSource.networkErrors })
        val total_count = Transformations.switchMap(dataSourceFactory.mutableLiveData, { dataSource -> dataSource.total_count })

        return SearchUserLiveData(total_count, data, networkErrors)

    }

}