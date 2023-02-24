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
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.adapter.RecentMatchAdapter
import com.example.cricketoons.databinding.FragmentRecentBinding
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.util.Constants
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "RecentFragment"

class RecentFragment : Fragment() {
    private var _binding: FragmentRecentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            try {
                binding.progressBar.visibility = View.VISIBLE
                binding.recentRefresh.visibility = View.GONE
                binding.recentRefresh.setOnRefreshListener {
                    if (Constants.checkConnectivity(requireContext())) {
                        Log.d("TAG", "onViewCreated: NetWorkAvailable")
                        CoroutineScope(Dispatchers.Main).launch {
                            displayRecent()
                        }
                    }
                    binding.recentRefresh.isRefreshing = false
                }
                binding.recentMatchRv.layoutManager = LinearLayoutManager(requireContext())
                binding.recentMatchRv.setHasFixedSize(true)

                displayRecent()
            } catch (e: Exception) {
                Log.d(TAG, "onViewCreated: ${e.message}")
            }
        }


    }

    private suspend fun displayRecent() {
        val timegap = getTimegap()
        val list = viewModel.readRecentMatches(timegap)
        try {
            withContext(Dispatchers.Main) {
                if (view != null) { // check if the fragment's view is not null
                    val liveDataList: MutableLiveData<List<Fixture>> =
                        MutableLiveData<List<Fixture>>().apply {
                            value = list
                        }

                    liveDataList.observe(viewLifecycleOwner) {
                        val recyclerViewState =
                            binding.recentMatchRv.layoutManager?.onSaveInstanceState()
                        binding.recentMatchRv.layoutManager?.onRestoreInstanceState(
                            recyclerViewState
                        )

                        val adapter = RecentMatchAdapter(requireContext(), viewModel)
                        adapter.setDataset(it)
                        binding.recentMatchRv.adapter = adapter
                    }
                    binding.recentRefresh.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "displayRecent: ${e.message}")
        }
    }

    private fun getTimegap(): String {
        val today = Calendar.getInstance().time
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -28)
        val upcomingTwoWeek = calendar.time

        @SuppressLint("ConstantLocale")
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return "${formatter.format(upcomingTwoWeek)},${formatter.format(today)}"
    }
}
