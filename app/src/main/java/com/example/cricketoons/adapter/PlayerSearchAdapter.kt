package com.example.cricketoons.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricketoons.R
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerSearchAdapter(val context: Context, val viewModel: ViewModel, private val squads: List<Squad>) :
    RecyclerView.Adapter<PlayerSearchAdapter.PlayerSearchViewHolder>() {

    private var playersList = squads

    class PlayerSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerName:TextView=view.findViewById(R.id.player_name)
        val playerCountry:TextView=view.findViewById(R.id.player_country)
        val playerPosition:TextView=view.findViewById(R.id.player_role)
        val playerImage:ImageView=view.findViewById(R.id.player_image)
        val playerCard:CardView=view.findViewById(R.id.player_card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerSearchViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.playersview, parent, false)
        return PlayerSearchViewHolder(layout)
    }

    override fun getItemCount(): Int {
       return playersList.size
    }

    override fun onBindViewHolder(holder: PlayerSearchViewHolder, position: Int) {
        val player= playersList[position]
        holder.playerName.text=player.fullname
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            holder.playerCountry.text= player.country_id?.let { viewModel.getCountryNameFromRoom(it) }
        }
        holder.playerPosition.text=player.battingstyle
        Glide.with(context).load(player.image_path).error(R.drawable.no_pictures).into(holder.playerImage)
    }

    fun setDataset(it: List<Squad>) {
        playersList = it
        notifyDataSetChanged()
    }

    fun searchPlayer(text: String) {
        val searchResult = ArrayList<Squad>()
        for(player in squads){
            if (player.fullname!!.contains(text,true)) searchResult.add(player)
        }
        setDataset(searchResult)
    }

}
