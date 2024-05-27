package com.azel.githubuserapp.data.retrofit

import com.azel.githubuserapp.data.response.DetailUserResponse
import com.azel.githubuserapp.data.response.FollowerResponse
import com.azel.githubuserapp.data.response.FollowingResponse
import com.azel.githubuserapp.data.response.GithubResponse
import com.azel.githubuserapp.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //search User: https://api.github.com/search/users?q=sidiqpermana
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers (
        @Query("q") Query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getListFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getListFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}