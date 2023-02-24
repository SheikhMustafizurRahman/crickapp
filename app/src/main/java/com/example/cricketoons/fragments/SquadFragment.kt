package com.example.cricketoons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.adapter.SquadAdapter
import com.example.cricketoons.databinding.FragmentMatchDetailSquadBinding
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "SquadFragment"
class SquadFragment(val fixture: Fixture) : Fragment() {
    private var _binding: FragmentMatchDetailSquadBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    private var squad= fixture.lineup!!
    private var teamOneSquad=squad.filter { it.lineup?.team_id==fixture.localteam_id }
    private var teamtwoSquad=squad.filter { it.lineup?.team_id==fixture.visitorteam_id}

        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentMatchDetailSquadBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.teamOneSquad.layoutManager=LinearLayoutManager(requireContext())
        binding.teamOneSquad.setHasFixedSize(true)
        binding.teamTwoSquad.layoutManager=LinearLayoutManager(requireContext())
        binding.teamTwoSquad.setHasFixedSize(true)
        try {
            val teamoneadapter=SquadAdapter(requireContext(),viewModel)
            Log.d(TAG, "onViewCreated: $squad,${fixture.localteam_id}")
            teamoneadapter.setDataset(teamOneSquad)
            binding.teamOneSquad.adapter=teamoneadapter
            val teamtwoadapter=SquadAdapter(requireContext(),viewModel)
            teamtwoadapter.setDataset(teamtwoSquad)
            binding.teamTwoSquad.adapter=teamtwoadapter
            CoroutineScope(Dispatchers.IO).launch {
                val localteamName = viewModel.getTeamNameFromRoom(fixture.localteam_id!!)
                val visitorTeamName = viewModel.getTeamNameFromRoom(fixture.visitorteam_id!!)
                withContext(Dispatchers.Main){
                    binding.teamOne.text=localteamName
                    binding.teamTwo.text=visitorTeamName
                }
            }
        }catch (e:Exception){
            Log.e(TAG, "onViewCreated: ${e.message}", )
        }
    }
}
