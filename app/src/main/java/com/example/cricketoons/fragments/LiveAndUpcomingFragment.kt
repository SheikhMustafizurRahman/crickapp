package com.example.cricketoons.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.adapter.LiveMatchAdapter
import com.example.cricketoons.adapter.UpcomingMatchAdapter
import com.example.cricketoons.databinding.FragmentLiveAndUpcomingBinding
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.model.fixtureWithTeam.FixtureDataWteam
import com.example.cricketoons.util.Constants.Companion.checkConnectivity
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class LiveAndUpcomingFragment : Fragment() {
    private var _binding: FragmentLiveAndUpcomingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveAndUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                binding.progressBar.visibility= View.VISIBLE
                binding.swipeRefreshLayout.visibility=View.GONE
                binding.swipeRefreshLayout.setOnRefreshListener {
                    if (checkConnectivity(requireContext())) {
                        Log.d("LiveUpdateFragment", "onViewCreated: No Error Here2")
                        CoroutineScope(Dispatchers.IO).launch {
                            displayLive()
                            Log.d("LiveUpdateFragment", "onViewCreated: No Error Here1")
                            displayUpcoming()
                        }
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                binding.liveMatchRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                binding.liveMatchRv.setHasFixedSize(true)
                binding.upcomingMatchRv.layoutManager = LinearLayoutManager(requireContext())
                binding.upcomingMatchRv.setHasFixedSize(true)
                Log.d("LiveUpdateFragment", "onViewCreated: No Error Here3")
                displayLive()
                displayUpcoming()
            } catch (e: Exception) {
                Log.d("LiveUpdateFragment", "onViewCreated: ${e.message}")
            }
        }
    }

    private suspend fun displayUpcoming() {
        val timegap = getTimegap()
        val list = viewModel.readUpcomingMatches(timegap)
        withContext(Dispatchers.Main) {
            val liveDataList: MutableLiveData<List<FixtureDataWteam>> =
                MutableLiveData<List<FixtureDataWteam>>().apply {
                    value = list
                }
            liveDataList.observe(viewLifecycleOwner) {
                val recyclerViewState = binding.upcomingMatchRv.layoutManager?.onSaveInstanceState()
                // Restore state
                binding.upcomingMatchRv.layoutManager?.onRestoreInstanceState(recyclerViewState)
                val adapter = UpcomingMatchAdapter(requireContext(), viewModel)
                adapter.setDataset(it)
                binding.upcomingMatchRv.adapter = adapter
            }
            binding.swipeRefreshLayout.visibility=View.VISIBLE
            binding.progressBar.visibility= View.GONE
        }
    }

    private fun getTimegap(): String {
        val today = Calendar.getInstance().time
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 28)
        val upcomingTwoWeek = calendar.time

        @SuppressLint("ConstantLocale")
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return "${formatter.format(today)},${formatter.format(upcomingTwoWeek)}"
    }

    private suspend fun displayLive() {
        val fixture = viewModel.getLive()
        withContext(Dispatchers.Main) {
            val liveDataList: MutableLiveData<List<Fixture>> =
                MutableLiveData<List<Fixture>>().apply {
                    value = fixture
                }
            liveDataList.observe(viewLifecycleOwner) {
                val recyclerViewState = binding.liveMatchRv.layoutManager?.onSaveInstanceState()
                // Restore state
                binding.liveMatchRv.layoutManager?.onRestoreInstanceState(recyclerViewState)
                val adapter = LiveMatchAdapter(requireContext(), viewModel)
                Log.d("TAGx", "DisplayItem: ")
                adapter.setDataset(it)
                binding.liveMatchRv.visibility = if (adapter.itemCount == 0) View.GONE else View.VISIBLE
                binding.liveMatchRv.adapter = adapter
            }
        }
    }
}