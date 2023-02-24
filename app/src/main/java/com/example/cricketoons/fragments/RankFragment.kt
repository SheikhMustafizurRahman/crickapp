package com.example.cricketoons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.R
import com.example.cricketoons.adapter.RankingAdapter
import com.example.cricketoons.databinding.FragmentRankBinding
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "RankFragment"

class RankFragment(val type: String) : Fragment() {
    private var _binding: FragmentRankBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()
    constructor() : this("T20I")

    private var genderMen=true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            selectGender()
            binding.teamsRv.layoutManager = LinearLayoutManager(requireContext())
            binding.teamsRv.setHasFixedSize(true)
            displayRanking()

            binding.men.setOnClickListener {
                genderMen = true
                selectGender()
                displayRanking()
            }
            binding.women.setOnClickListener {
                genderMen = false
                selectGender()
                displayRanking()
            }

        } catch (e: Exception) {
            Log.d(TAG, "onViewCreated: ${e.message}")
        }

    }

    private fun displayRanking() {
        CoroutineScope(Dispatchers.IO).launch {
            val rankingData = viewModel.fetchRankingFromAPI(type)
            val genderToFilter = if(genderMen){
                "men" // specify the gender to filter by
            }else "women"

            val filtered = rankingData.filter { it.gender == genderToFilter }
            val filteredTeams = filtered.flatMap { it.team }
            withContext(Dispatchers.Main) {
                val adapter = RankingAdapter(requireContext(), viewModel)
                Log.d(TAG, "onViewCreated: $filteredTeams")
                adapter.setDataset(filteredTeams)
                binding.teamsRv.adapter = adapter
            }
        }
    }

    private fun selectGender() {
        if (genderMen) {
            binding.men.isSelected = true
            binding.men.setBackgroundResource(R.color.colorSelected)

            binding.women.isSelected = false
            binding.women.setBackgroundResource(R.color.colorUnselectedText)
        } else {
            binding.women.isSelected = true
            binding.women.setBackgroundResource(R.color.colorSelected)

            binding.men.isSelected = false
            binding.men.setBackgroundResource(R.color.colorUnselectedText)
        }
    }

}
