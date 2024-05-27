package com.azel.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.azel.githubuserapp.data.response.GithubResponse
import com.azel.githubuserapp.data.response.ItemsItem
import com.azel.githubuserapp.data.retrofit.ApiConfig
import com.azel.githubuserapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        private const val TAG = "MainActivity"
        private const val GITHUB_ID = "Arif"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, actionId, _ ->
                    if(actionId == EditorInfo.IME_ACTION_SEARCH){
                        searchView.hide()
                        findGithub(searchView.text.toString())
                        true
                    } else {
                        false
                    }
                }
        }


        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        findGithub("Arif")

    }

    private fun findGithub(query: String = GITHUB_ID) {
        showLoading(true)
        val client = ApiConfig.getServiceApi().getUsers(query)
        client.enqueue(object: Callback<GithubResponse>{
        override fun onResponse (call: Call<GithubResponse>, response: Response<GithubResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUsersList((responseBody.items))
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

                override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

   private fun setUsersList(userData: List<ItemsItem>) {
        val adapter = UsersAdapter()
        adapter.submitList(userData)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading (isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
