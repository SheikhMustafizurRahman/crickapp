package com.example.cricketoons.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketoons.R
import com.example.cricketoons.model.apiFixture.Bowling
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "BowlingScorecardAdapter"
class BowlingScorecardAdapter(val context: Context, val viewModel: ViewModel, val localTeam: Boolean):
    RecyclerView.Adapter<BowlingScorecardAdapter.BowlingViewHolder>() {

    private var bowlers = emptyList<Bowling>()
    private val scoreboard= if(localTeam)"S1" else "S2"

    class BowlingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val playerName: TextView = view.findViewById(R.id.player_name)
        val overBowled:TextView = view.findViewById(R.id.player_over)
        val overMaiden:TextView=view.findViewById(R.id.player_maiden)
        val wicketTaken:TextView =view.findViewById(R.id.player_wicket)
        val runGiven:TextView=view.findViewById(R.id.player_run)
        val economy:TextView=view.findViewById(R.id.player_econ)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BowlingViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.bowling_score_card, parent, false)
        return BowlingViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return bowlers.size
    }

    override fun onBindViewHolder(holder: BowlingViewHolder, position: Int) {
        val bowler= bowlers[position]
        CoroutineScope(Dispatchers.IO).launch{
            try {
                var bowlerName=viewModel.getPlayerNameByID(bowler.player_id!!)
                if(bowlerName==null){
                    viewModel.fetchPlayerByIDFromAPI(bowler.player_id!!)
                    bowlerName=viewModel.getPlayerNameByID(bowler.player_id)
                }
                holder.playerName.text = bowlerName
            }catch (e:Exception){
                Log.d(TAG, "onBindViewHolder: $e")
            }
            holder.overBowled.text=bowler.overs.toString()
            holder.overMaiden.text=if (bowler.medians==null) "0" else bowler.medians.toString()
            holder.runGiven.text=bowler.runs.toString()
            holder.economy.text=bowler.rate.toString()
            holder.wicketTaken.text=if(bowler.wickets==null) "0" else bowler.wickets.toString()

        }
    }

    fun setDataset(bowler: List<Bowling>?) {
        if (bowler != null) bowlers = bowler.filter { it.scoreboard== scoreboard }
        Log.d(TAG, "setDataset: ${bowlers.size}")
    }

}