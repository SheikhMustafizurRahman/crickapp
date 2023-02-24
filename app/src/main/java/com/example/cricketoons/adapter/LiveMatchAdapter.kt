package com.example.cricketoons.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricketoons.R
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LiveMatchAdapter(val context: Context, val viewModel: ViewModel) :
    RecyclerView.Adapter<LiveMatchAdapter.LiveViewHolder>() {

    private var liveMatches = emptyList<Fixture>()

    class LiveViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val teamOne: TextView = view.findViewById(R.id.team1Score)
        val teamTwo: TextView = view.findViewById(R.id.team2Score)
        val teamOneFlag: ImageView = view.findViewById(R.id.team1flag)
        val teamTwoFlag: ImageView = view.findViewById(R.id.team2flag)
    }


    override fun onBindViewHolder(holder: LiveViewHolder, position: Int) {
        val live=liveMatches[position]
        CoroutineScope(Dispatchers.Main).launch {
            var localTeamLogo =""
            var visitorTeamLogo =""
            withContext(Dispatchers.IO) {
                holder.teamTwo.text = viewModel.getTeamNameFromRoom(live.localteam_id!!)
                holder.teamOne.text = viewModel.getTeamNameFromRoom(live.visitorteam_id!!)

                localTeamLogo=viewModel.getTeamLogoFromRoom(live.localteam_id!!)
                visitorTeamLogo=viewModel.getTeamLogoFromRoom(live.visitorteam_id!!)

            }
            Glide.with(context).load(localTeamLogo).error(R.drawable.bdflag)
                .into(holder.teamOneFlag)
            Glide.with(context).load(visitorTeamLogo).error(R.drawable.japanflag)
                .into(holder.teamTwoFlag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.live_card_view, parent, false)
        return LiveViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return liveMatches.size
    }

    fun setDataset(it: List<Fixture>) {
        liveMatches=it
    }

}
