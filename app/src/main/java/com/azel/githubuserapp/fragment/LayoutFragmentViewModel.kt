package com.azel.githubuserapp.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azel.githubuserapp.data.response.ItemsItem
import com.azel.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LayoutFragmentViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean> ()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    fun fetchFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getServiceApi().getListFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = true
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
            }
        }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
}

    fun fetchFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getServiceApi().getListFollowing(username)
        client.enqueue(object : Callback <List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else{
                }
        }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}