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
import com.example.cricketoons.model.rankingAPI.Team
import com.example.cricketoons.viewmodel.ViewModel

private const val TAG = "RankingAdapter"
class RankingAdapter(val context: Context,val viewModel: ViewModel) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    private var teams= emptyList<Team>()

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val teamNameTV: TextView =view.findViewById(R.id.teamName)
        val teamCodeTV: TextView =view.findViewById(R.id.teamCode)
        val teamLogo: ImageView =view.findViewById(R.id.teamLogo)
        val teamCard: CardView = view.findViewById(R.id.cardView)
        val teamrank:TextView =view.findViewById(R.id.rank)
        val rankingpoint:TextView=view.findViewById(R.id.teamRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout= LayoutInflater.from(context).inflate(R.layout.team_card,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team=teams[position]
        holder.teamNameTV.text=team.name
        holder.teamCodeTV.text=team.code
        holder.teamrank.text= (position+1).toString()
        holder.rankingpoint.text= team.ranking.points.toString()
        Log.d(TAG, "onBindViewHolder: $team")
        Glide.with(context).load(team.image_path).error(R.drawable.no_pictures).into(holder.teamLogo)

        holder.teamCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("teamId", team.id)
            bundle.putString("teamLogo",team.image_path)
            holder.teamCard.findNavController().navigate(R.id.teamDetailFragment,bundle)
        }
    }

    fun setDataset(filteredTeams: List<Team>) {
        teams=filteredTeams
    }

}
