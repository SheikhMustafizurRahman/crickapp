package com.example.cricketoons.adapter

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cricketoons.R
import com.example.cricketoons.model.fixtureWithTeam.FixtureDataWteam
import com.example.cricketoons.util.Constants
import com.example.cricketoons.util.Constants.Companion.MATCH_DAY
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val TAG = "UpcomingMatchAdapter"

class UpcomingMatchAdapter(val context: Context,val viewModel: ViewModel) :
    RecyclerView.Adapter<UpcomingMatchAdapter.UpcomingViewHolder>() {

    private var upcoming = emptyList<FixtureDataWteam>()
    private var timerIsRunning = false
    private var countdownTimer: CountDownTimer? = null

    class UpcomingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamOne: TextView = view.findViewById(R.id.team1Code)
        val teamTwo: TextView = view.findViewById(R.id.team2code)
        val teamOneFlag: ImageView = view.findViewById(R.id.team1flag)
        val teamTwoFlag: ImageView = view.findViewById(R.id.team2flag)
        val countdownTime: TextView = view.findViewById(R.id.countdown_timer)
        val matchStatus:TextView =view.findViewById(R.id.upcoming)
        val note:TextView=view.findViewById(R.id.match_note)
        val leagueLogo:ImageView=view.findViewById(R.id.league_logo)
        val leagueName:TextView=view.findViewById(R.id.leagueNameTV)
        val teamOneScore: TextView = view.findViewById(R.id.team1Score)
        val teamTwoScore: TextView = view.findViewById(R.id.team2Score)
        val teamOneOver:TextView=view.findViewById(R.id.team1Over)
        val teamTwoOver:TextView=view.findViewById(R.id.team2Over)
        val relative:RelativeLayout=view.findViewById(R.id.relative)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.upcoming_card_view, parent, false)
        return UpcomingViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return upcoming.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val upcomingMatch = upcoming[position]
        holder.teamOne.text = upcomingMatch.localteam.name
        holder.teamTwo.text = upcomingMatch.visitorteam.name
        Glide.with(context).load(upcomingMatch.localteam.image_path).apply( RequestOptions().override(80, 80)).error(R.drawable.bdflag)
            .into(holder.teamOneFlag)
        Glide.with(context).load(upcomingMatch.visitorteam.image_path).apply( RequestOptions().override(80, 80)).error(R.drawable.japanflag)
            .into(holder.teamTwoFlag)
        countdownTimer(holder.countdownTime, upcomingMatch.starting_at, Constants.getCurrentDate())
        CoroutineScope(Dispatchers.IO).launch {
            val leagueName=viewModel.getLeagueNamebyID(upcomingMatch.league_id)
            val leagueLogo=viewModel.getLeagueLogobyID(upcomingMatch.league_id)
            withContext(Dispatchers.Main){
                Glide.with(context).load(leagueLogo).error(R.drawable.japanflag)
                    .into(holder.leagueLogo)
                holder.leagueName.text=leagueName
            }
        }
        holder.teamOneScore.visibility=View.GONE
        holder.teamOneOver.visibility=View.GONE
        holder.teamTwoScore.visibility=View.GONE
        holder.teamTwoOver.visibility=View.GONE
        holder.matchStatus.text=context.getString(R.string.Upcoming)
        if(holder.countdownTime.equals(MATCH_DAY)){
            holder.relative.visibility=View.GONE
        }
        else{
            holder.relative.visibility=View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun countdownTimer(countdownTime: TextView, startingAt: String?, currentDate: String) {
        val zonedDateTime = ZonedDateTime.parse(startingAt)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDate = zonedDateTime.format(formatter)
        val timeDiff = getTimeDifferenceInMillis(formattedDate, currentDate)
        Log.d(TAG, "countdownTimer: $timeDiff")
        countdownTimer = object : CountDownTimer(timeDiff, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerIsRunning = true
                val secondsRemaining = millisUntilFinished / 1000
                val timerText = String.format(
                    "%02d days, %02d:%02d:%02d",
                    secondsRemaining / 86400, // number of seconds in a day
                    secondsRemaining % 86400 / 3600, // number of hours in remaining seconds
                    secondsRemaining % 3600 / 60, // number of minutes in remaining seconds
                    secondsRemaining % 60 // remaining seconds
                )
                countdownTime.text = timerText

            }

            override fun onFinish() {
                countdownTime.text = MATCH_DAY
                timerIsRunning = false
            }
        }
        countdownTimer?.start()

    }

    fun setDataset(it: List<FixtureDataWteam>) {
        upcoming = it
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTimeDifferenceInMillis(date1String: String, date2String: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        try {
            val date1 = dateFormat.parse(date1String)
            val date2 = dateFormat.parse(date2String)
            return date1!!.time - date2!!.time
        } catch (e: Exception) {
            Log.e(TAG, "getTimeDifferenceInMillis: ${e.stackTrace}")
        }
        return 0
    }

}
