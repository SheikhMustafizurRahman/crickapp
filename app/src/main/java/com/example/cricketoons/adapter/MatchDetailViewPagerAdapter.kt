package com.example.cricketoons.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricketoons.fragments.*
import com.example.cricketoons.model.apiFixture.Fixture

class MatchDetailViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    var fixture: Fixture
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MatchInfoFragment(fixture)
            2-> SquadFragment(fixture)
            else -> ScorecardFragment(fixture)
        }
    }
}