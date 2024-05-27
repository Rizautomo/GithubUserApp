package com.azel.githubuserapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.azel.githubuserapp.fragment.LayoutFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    companion object {
        const val GIT_USERNAME = ""
        const val Position = ""
    }

    var username: String = ""


    override fun createFragment(position: Int): Fragment {

       /* val FragmentBundle = Bundle()
        FragmentBundle.putInt(Position, if (position==0) 0 else 1)
        FragmentBundle.putString(GIT_USERNAME, getTabUsername())
        */

        val fragment = LayoutFragment()
        fragment.arguments = Bundle().apply {
            putInt(LayoutFragment.ARG_POSITION, position +1)
            putString(LayoutFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

    //private fun getTabUsername() : String = username
}