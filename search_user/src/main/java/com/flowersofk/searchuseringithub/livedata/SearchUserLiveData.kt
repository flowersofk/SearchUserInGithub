package com.flowersofk.searchuseringithub.livedata

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.flowersofk.searchuseringithub.model.UserInfo

/**
 * LiveData 정의
 */
data class SearchUserLiveData(

    val count: LiveData<Int>,
    val result: LiveData<PagedList<UserInfo>>,
    val networkErrors: LiveData<String>

)