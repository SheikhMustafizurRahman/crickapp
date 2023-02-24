package com.example.cricketoons.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cricketoons.R
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.viewmodel.ViewModel

class SquadAdapter(val context: Context,val viewModel: ViewModel) :
    RecyclerView.Adapter<SquadAdapter.PlayerViewHolder>() {

    private var playersList = emptyList<Squad>()

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerName:TextView=view.findViewById(R.id.player_name)
        val playerCountry:TextView=view.findViewById(R.id.player_country)
        val playerPosition:TextView=view.findViewById(R.id.player_role)
        val playerImage:ImageView=view.findViewById(R.id.player_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.playersview, parent, false)
        return PlayerViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return playersList.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player= playersList[position]
        holder.playerName.text=player.fullname
        holder.playerName.textSize= 12F
        holder.playerCountry.visibility=View.GONE
        holder.playerPosition.text=player.position?.name
        Glide.with(context).load(player.image_path).apply( RequestOptions().override(80, 80)).error(R.drawable.no_pictures).into(holder.playerImage)
    }

    fun setDataset(it: List<Squad>) {
        playersList = it
        notifyDataSetChanged()
    }
}
