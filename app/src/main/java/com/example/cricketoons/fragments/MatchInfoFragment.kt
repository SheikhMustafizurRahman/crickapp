package com.example.cricketoons.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cricketoons.databinding.FragmentMatchDetailInfoBinding
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.util.Constants
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.*

class MatchInfoFragment(val fixture: Fixture) : Fragment() {
    private var _binding: FragmentMatchDetailInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.matchdate.text = Constants.convertDateTimeToDateString(fixture.starting_at!!)
        CoroutineScope(Dispatchers.IO).launch {
            val toss = viewModel.getTeamNameFromRoom(fixture.toss_won_team_id!!)
            val manoftheMatch=viewModel.getPlayerNameByID(fixture.man_of_match_id)
            val seriesName=viewModel.getLeagueNamebyID(fixture.league_id)
            val venue=viewModel.getVenueNameByID(fixture.venue_id)
            withContext(Dispatchers.Main){
                binding.toss.text = buildString {
                    append("Toss won by ")
                    append(toss)
                    append(" and chose to ")
                    append(fixture.elected)
                    append(" first")
                }
                binding.manofTheMatch.text=manoftheMatch
                binding.seriesName.text = seriesName
                binding.venue.text=venue
            }
        }

    }
}
