package com.azel.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import coil.transform.CircleCropTransformation
import com.azel.githubuserapp.R
import com.azel.githubuserapp.data.response.DetailUserResponse
import com.azel.githubuserapp.data.retrofit.ApiConfig
import com.azel.githubuserapp.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Response

import retrofit2.Callback

class DetailUserActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val username = "String"
    }
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gitUsername = intent.getStringExtra(username)

        //Log.d("DetailUserActivity", testUserName.toString())

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        showLoading(true)
        if (gitUsername != null) {
            getUserDetail(gitUsername)
        }
    }

    private fun getUserDetail (username: String) {
        val client = ApiConfig.getServiceApi().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        updateNewUser(it)
                    }
                } else {
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
            }

        })
    }

    private fun updateNewUser(user: DetailUserResponse) {
        binding.apply {
            val into = Glide.with(this@DetailUserActivity)
                .load(user.avatarUrl)
                .into(userAvatar)

            userName.text = user.name
            userUsername.text = user.login
            userFollowing.text = getString(R.string.following_count, user.following)
            userFollower.text = getString(R.string.followers_count, user.followers)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}