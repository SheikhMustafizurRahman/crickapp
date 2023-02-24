package com.example.cricketoons

import android.content.Context
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.cricketoons.databinding.ActivityMainBinding
import com.example.cricketoons.viewmodel.ViewModel
import com.example.cricketoons.worker.NetworkChangeReceiver
import com.example.cricketoons.worker.SyncDataWorker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: ViewModel by viewModels()
    private var receiver: NetworkChangeReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

// Set custom item background
        bottomNavigationView.itemBackground =
            ContextCompat.getDrawable(this, R.drawable.bottom_nav_item_background)

// Set item text and icon colors programmatically
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            // Change text color
            val states = arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            )
            val colors = intArrayOf(
                ContextCompat.getColor(this, R.color.colorOnSecondary),
                ContextCompat.getColor(this, R.color.colorOnSurface)
            )
            val textColors = ColorStateList(states, colors)
            bottomNavigationView.itemTextColor = textColors

            // Change icon color
            val iconColors = ColorStateList(states, colors)
            bottomNavigationView.itemIconTintList = iconColors

            // Return true to update the selected item
            true
        }


        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    findNavController(binding.fragmentHost).navigate(R.id.homeFragment)
                }
                R.id.searchplayer -> {
                    findNavController(binding.fragmentHost).navigate(R.id.searchFragment)
                }
                R.id.ranking -> {
                    findNavController(binding.fragmentHost).navigate(R.id.rankingFragment)
                }
            }
            true
        }
        //sharedPrefwork
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val isNewInstall = sharedPreferences.getBoolean("isNewInstall", true)
        Log.d(TAG, "onCreate: $isNewInstall")

        if (isNewInstall) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isNewInstall", false)
            editor.apply()
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getTeamsFromAPIStoreInRoom()
                viewModel.insertCountryLeagueSeasonVenueStageFromAPIT0Room()
            }
            Log.d(TAG, "onCreate: called")
        }

        //Internet Checker
        receiver = object : NetworkChangeReceiver() {
            override fun onNetworkChange(flag: Boolean) {
                if (flag) {
                    val customSnackbarView = LayoutInflater.from(binding.root.context)
                        .inflate(R.layout.snackbar_custom, null)
                    val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_INDEFINITE)
                    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
                    val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
                    snackbarLayout.setPadding(0, 0, 0, 0)
                    snackbarLayout.addView(customSnackbarView, 0)
                    snackbar.show()
                } else Snackbar.make(binding.root, "Network Connected", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(receiver, filter)

        //PostNotification Worker
        val workManager = WorkManager.getInstance(this)
        val dataLoad =
            PeriodicWorkRequest
                .Builder(SyncDataWorker::class.java, 1, TimeUnit.HOURS)
                .setInitialDelay(2, TimeUnit.MINUTES)
                .addTag("apiCall")
                .build()
        workManager.enqueueUniquePeriodicWork(
            "apiCall",
            ExistingPeriodicWorkPolicy.REPLACE,
            dataLoad
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

}