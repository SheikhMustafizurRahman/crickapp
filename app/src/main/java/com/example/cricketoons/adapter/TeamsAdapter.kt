package com.example.cricketoons.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricketoons.R
import com.example.cricketoons.model.roomTeams.TeamData
import com.example.cricketoons.viewmodel.ViewModel

private const val TAG = "TeamsAdapter"
class TeamsAdapter(val context: Context, val viewModel: ViewModel, private val teams: List<TeamData>) :
    RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    private var teamList = teams

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamNameTV: TextView = view.findViewById(R.id.teamName)
        val teamCodeTV: TextView = view.findViewById(R.id.teamCode)
        val teamLogo: ImageView = view.findViewById(R.id.teamLogo)
        val teamCard: CardView = view.findViewById(R.id.cardView)
        val teamrank: TextView = view.findViewById(R.id.rank)
        val rankingpoint: TextView = view.findViewById(R.id.teamRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.team_card, parent, false)
        return TeamViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teamList[position]
        holder.teamNameTV.text = team.name
        holder.teamCodeTV.text = team.code
        Glide.with(context).load(team.image_path).error(R.drawable.no_pictures)
            .into(holder.teamLogo)
        viewModel.getSquadFromAPIStoreInRoom(team.id)
        holder.teamCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("teamId", team.id)
            bundle.putString("teamLogo", team.image_path)
            holder.teamCard.findNavController().navigate(R.id.teamDetailFragment, bundle)
        }
        holder.teamrank.visibility = View.GONE
        holder.rankingpoint.visibility = View.GONE
    }

    fun setDataset(it: List<TeamData>) {
        teamList = it
        Log.d(TAG, "setDataset: $teamList")
        notifyDataSetChanged()
    }

    fun searchTeam(text: String) {
        val searchResult = ArrayList<TeamData>()
        for (team in teams) {
            Log.d(TAG, "searchTeam: $searchResult")
            if (team.name!!.contains(text, true)) searchResult.add(team)
        }
        setDataset(searchResult)
    }
}