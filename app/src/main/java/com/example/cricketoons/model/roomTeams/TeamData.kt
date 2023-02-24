package com.example.cricketoons.model.roomTeams

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad

@Entity(tableName = "teams")
data class TeamData(
    var code: String?,
    var country_id: Int?,
    @PrimaryKey
    var id: Int,
    var image_path: String?,
    var name: String?,
    var national_team: Boolean?,
    var resource: String?,
    @Ignore
    var squad: List<Squad>?,
    var updated_at: String?
){
    constructor() : this(
        null,
        null,
        0,
        null,
        null,
        null,
        null,
        null,
        null,
    )
}