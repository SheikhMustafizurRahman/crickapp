package com.example.cricketoons.fragments.viewpagershost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cricketoons.adapter.RankingViewPagerAdapter
import com.example.cricketoons.databinding.FragmentRankingBinding
import com.example.cricketoons.viewmodel.ViewModel
import com.google.android.material.tabs.TabLayoutMediator

class RankingFragment : Fragment() {
    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    private val tabTextArray = arrayOf(
        "T20", "ODI", "TEST"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.RankingVewPager
        val tabLayout = binding.RankingtabMode
        val adapter = RankingViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTextArray[position]
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}