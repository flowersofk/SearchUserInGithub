package com.flowersofk.searchuseringithub.network

import com.flowersofk.searchuseringithub.model.UserInfo

data class SearchUserResponse(

    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<UserInfo>

)