package com.flowersofk.searchuseringithub.service

import android.util.Log
import com.flowersofk.searchuseringithub.Constants
import com.flowersofk.searchuseringithub.model.UserInfo
import com.flowersofk.searchuseringithub.network.SearchUserResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchUserService {

    @GET("/search/users")
    fun getSearchUser(
        @Query("q") keyword: String,
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ): Call<SearchUserResponse>

    companion object {

        fun create(): SearchUserService {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(SearchUserService::class.java)

        }

    }

}

/**
 * 유저검색 API 호출 정의
 */
fun getSearchUser(
    service: SearchUserService,
    keyword: String,
    page: Int,
    onSuccess: (count: Int, response: List<UserInfo>) -> Unit,
    onFailure: (error: String) -> Unit) {

    service.getSearchUser(keyword, page, Constants.PAGE_SIZE).enqueue(
        object : Callback<SearchUserResponse> {
            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {

                Log.e("Test", "got a response $t.message")

                onFailure(t.message ?: "unknown error")

            }

            override fun onResponse(call: Call<SearchUserResponse>, @NotNull response: Response<SearchUserResponse>) {
                Log.d("Test", "got a response $response")

                if(response != null && response.isSuccessful) {

                    onSuccess(response.body()?.total_count ?: 0, response.body()?.items ?: mutableListOf())

                } else {

                    onFailure(response.errorBody()?.string() ?: "Unknown error")

                }

            }

        })

}