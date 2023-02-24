package com.example.cricketoons.model.rankingAPI

data class RankingData(
    var gender: String,
    var points: Any?,
    var position: Any?,
    var rating: Any?,
    var resource: String,
    var team: List<Team>,
    var type: String,
    var updated_at: String
)