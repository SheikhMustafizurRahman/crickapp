package com.example.cricketoons.model.rankingAPI

data class Team(
    var code: String,
    var country_id: Int,
    var id: Int,
    var image_path: String,
    var name: String,
    var national_team: Boolean,
    var position: Int,
    var ranking: Ranking,
    var resource: String,
    var updated_at: String
)