package com.example.cricketoons.fragments.viewpagershost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cricketoons.adapter.SearchViewPagerAdapter
import com.example.cricketoons.databinding.FragmentSearchBinding
import com.example.cricketoons.viewmodel.ViewModel
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    private val TabTextArray = arrayOf(
        "Team","Player"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.searchViewPager
        val tabLayout = binding.tabMode
        val adapter = SearchViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout,viewPager){ tab, position ->
           tab.text = TabTextArray[position]
       }.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}