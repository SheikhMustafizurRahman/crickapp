package com.example.cricketoons.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketoons.R
import com.example.cricketoons.model.apiFixture.Batting
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "BattingScorecardAdapter"
class BattingScorecardAdapter(val context: Context, val viewModel: ViewModel, private val localTeam: Boolean):
    RecyclerView.Adapter<BattingScorecardAdapter.BattingViewHolder>() {

    private var players = emptyList<Batting>()
    private val scoreboard= if(localTeam)"S1" else "S2"

    class BattingViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val playerName:TextView= view.findViewById(R.id.player_name)
        val runScore:TextView=view.findViewById(R.id.player_run_score)
        val ballPlayed:TextView=view.findViewById(R.id.player_ball_played)
        val fours:TextView=view.findViewById(R.id.player_fours)
        val sixes:TextView=view.findViewById(R.id.player_sixes)
        val strikeRate:TextView=view.findViewById(R.id.player_SR)
        //val playerStatus:TextView=view.findViewById(R.id.playerStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.batting_score_card, parent, false)
        return BattingViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: BattingViewHolder, position: Int) {
        val player= players[position]
        CoroutineScope(Dispatchers.IO).launch{
            try {
                var batsmanName=viewModel.getPlayerNameByID(player.player_id!!)
                if(batsmanName==null){
                    viewModel.fetchPlayerByIDFromAPI(player.player_id!!)
                    batsmanName=viewModel.getPlayerNameByID(player.player_id)
                }
                holder.playerName.text = batsmanName
            }catch (e:Exception){
                Log.d(TAG, "onBindViewHolder: $e")
            }

        }
        holder.runScore.text=player.score.toString() 
        holder.ballPlayed.text=player.ball.toString()
        holder.fours.text=player.four_x.toString()
        holder.sixes.text=player.six_x.toString()
        holder.strikeRate.text=player.rate.toString()
    }

    fun setDataset(batting: List<Batting>?) {
        Log.d(TAG, "setDataset: $scoreboard")
        if (batting != null) players = batting.filter { it.scoreboard == scoreboard }
        Log.d(TAG, "setDataset: ${players.size}")
    }

}
