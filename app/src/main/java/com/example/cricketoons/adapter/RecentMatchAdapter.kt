package com.example.cricketoons.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricketoons.R
import com.example.cricketoons.fragments.viewpagershost.HomeFragmentDirections
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAGR = "RecentMatchAdapter"

class RecentMatchAdapter(val context: Context, val viewModel: ViewModel) :
    RecyclerView.Adapter<RecentMatchAdapter.RecentViewHolder>() {

    private var recent = emptyList<Fixture>()

    class RecentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamOne: TextView = view.findViewById(R.id.team1Code)
        val teamTwo: TextView = view.findViewById(R.id.team2code)
        val teamOneFlag: ImageView = view.findViewById(R.id.team1flag)
        val teamTwoFlag: ImageView = view.findViewById(R.id.team2flag)
        val matchstatus: TextView = view.findViewById(R.id.upcoming)
        val venue:TextView=view.findViewById(R.id.countdown_timer)
        val matchCard: CardView = view.findViewById(R.id.card)
        val leagueLogo: ImageView = view.findViewById(R.id.league_logo)
        val leagueName: TextView = view.findViewById(R.id.leagueNameTV)
        val teamOneScore: TextView = view.findViewById(R.id.team1Score)
        val teamTwoScore: TextView = view.findViewById(R.id.team2Score)
        val teamOneOver:TextView=view.findViewById(R.id.team1Over)
        val teamTwoOver:TextView=view.findViewById(R.id.team2Over)
        val result: TextView = view.findViewById(R.id.match_note)
    }

    fun setDataset(fixtures: List<Fixture>) {
        recent = fixtures
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.upcoming_card_view, parent, false)
        return RecentViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return recent.size
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        val recentMatch = recent[position]
        var localTeam = ""
        var visitorTeam = ""
        var localTeamLogo = ""
        var visitorTeamLogo = ""
        var leagueName = ""
        var leagueLogo = ""
        try {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    if (recentMatch.localteam_id?.let { viewModel.checkIfTeamExistInRoom(it) }!! > 0) {
                        // Data exists in the database
                        localTeam = viewModel.getTeamNameFromRoom(recentMatch.localteam_id!!)
                        visitorTeam = viewModel.getTeamNameFromRoom(recentMatch.visitorteam_id!!)
                        localTeamLogo = viewModel.getTeamLogoFromRoom(recentMatch.localteam_id!!)
                        visitorTeamLogo =
                            viewModel.getTeamLogoFromRoom(recentMatch.visitorteam_id!!)
                        leagueName = viewModel.getLeagueNamebyID(recentMatch.league_id)
                        leagueLogo = viewModel.getLeagueLogobyID(recentMatch.league_id)
                    } else {
                        // Data does not exist in the database
                        Log.e(TAGR, "onBindViewHolder: Data Not Exist")
                    }
                }
                // Update the UI views on the UI thread
                holder.teamOne.text = localTeam
                holder.teamTwo.text = visitorTeam
                Glide.with(context).load(localTeamLogo).error(R.drawable.bdflag)
                    .into(holder.teamOneFlag)
                Glide.with(context).load(visitorTeamLogo).error(R.drawable.japanflag)
                    .into(holder.teamTwoFlag)
                Glide.with(context).load(leagueLogo).error(R.drawable.japanflag)
                    .into(holder.leagueLogo)
                holder.leagueName.text = leagueName
                holder.result.text = recentMatch.note
                val localTeamRun =
                    recentMatch.runs?.filter { it.team_id == recentMatch.localteam_id }
                val visitorTeamRun =
                    recentMatch.runs?.filter { it.team_id == recentMatch.visitorteam_id }
                holder.teamOneScore.text = buildString {
                    append("Total: ")
                    append(localTeamRun?.get(0)?.score)
                    append("/")
                    append(localTeamRun?.get(0)?.wickets)
                }
                holder.teamOneOver.text=buildString {
                    append("Overs: ")
                    append(localTeamRun?.get(0)?.overs)
                }

                holder.teamTwoScore.text = buildString {
                    append("Total: ")
                    append(visitorTeamRun?.get(0)?.score)
                    append("/")
                    append(visitorTeamRun?.get(0)?.wickets)
                }
                holder.teamTwoOver.text=buildString {
                    append("Overs: ")
                    append(visitorTeamRun?.get(0)?.overs)
                }
                holder.matchstatus.text=context.getString(R.string.finished)
            }
            holder.matchCard.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToMatchDetailFragment(recentMatch)
                Navigation.findNavController(holder.itemView).navigate(action)
            }

        } catch (e: Exception) {
            Log.e(TAGR, "onBindViewHolder: ${e.message}")
        }
    }

}
