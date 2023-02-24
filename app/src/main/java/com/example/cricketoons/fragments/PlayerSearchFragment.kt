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
import com.example.cricketoons.adapter.PlayerSearchAdapter
import com.example.cricketoons.databinding.FragmentPlayerSearchBinding
import com.example.cricketoons.viewmodel.ViewModel

private const val TAG = "PlayerSearchFragment"

class PlayerSearchFragment : Fragment() {
    private var _binding: FragmentPlayerSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()
    private lateinit var adapter: PlayerSearchAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchPlayerRV.layoutManager = LinearLayoutManager(requireContext())
        binding.searchPlayerRV.setHasFixedSize(true)
        try {
            viewModel.getALlSquadPlayerFromRoom().observe(viewLifecycleOwner) {
                val recyclerViewState = binding.searchPlayerRV.layoutManager?.onSaveInstanceState()
                // Restore state
                binding.searchPlayerRV.layoutManager?.onRestoreInstanceState(recyclerViewState)
                adapter = PlayerSearchAdapter(requireContext(), viewModel,it)
                //adapter.setDataset(it)
                binding.searchPlayerRV.adapter = adapter
                searchView = binding.searchView
                searchPlayer()
            }
        } catch (e: Exception) {
            Log.e(TAG, "onViewCreated: ${e.message}")
        }

    }

    private fun searchPlayer() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if (query != null) {
                    adapter.searchPlayer(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.searchPlayer(newText)
                }
                return false
            }
        })
    }

}
