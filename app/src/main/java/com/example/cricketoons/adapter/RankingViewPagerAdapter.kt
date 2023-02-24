package com.example.cricketoons.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricketoons.fragments.RankFragment

class RankingViewPagerAdapter(
    childFragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(childFragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RankFragment("T20I")
            1 -> RankFragment("ODI")
            else -> RankFragment("TEST")
        }
    }

}
