package com.example.cricketoons.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricketoons.fragments.LiveAndUpcomingFragment
import com.example.cricketoons.fragments.RecentFragment
import com.example.cricketoons.util.Constants.Companion.NUM_HOME_TABS

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_HOME_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LiveAndUpcomingFragment()
            else -> RecentFragment()
        }
    }
}
