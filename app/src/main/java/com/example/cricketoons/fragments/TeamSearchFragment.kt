package com.example.cricketoons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.adapter.TeamsAdapter
import com.example.cricketoons.databinding.FragmentTeamSearchBinding
import com.example.cricketoons.viewmodel.ViewModel

private const val TAG ="TeamSearchFragment"

class TeamSearchFragment : Fragment() {

    private var _binding: FragmentTeamSearchBinding?=null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()
    private lateinit var adapter: TeamsAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentTeamSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchTeamRV.layoutManager = LinearLayoutManager(requireContext())
        binding.searchTeamRV.setHasFixedSize(true)
        try {
            viewModel.getTeamsFromRoom().observe(viewLifecycleOwner){
                val recyclerViewState = binding.searchTeamRV.layoutManager?.onSaveInstanceState()
                // Restore state
                binding.searchTeamRV.layoutManager?.onRestoreInstanceState(recyclerViewState)
                adapter = TeamsAdapter(requireContext(), viewModel,it)
                binding.searchTeamRV.adapter = adapter
                adapter.setDataset(it)
                searchView = binding.searchView
                searchTeam()
            }
        }catch (e:Exception){
            Log.e(TAG, "onViewCreated: ${e.message}" )
        }

    }

    private fun searchTeam() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if (query != null) {
                    adapter.searchTeam(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.searchTeam(newText)
                }
                return false
            }
        })
    }

}
