package com.example.cricketoons.model.fixtureWithTeam

data class Meta(
    var current_page: Int?,
    var from: Int?,
    var last_page: Int?,
    var links: List<Link?>?,
    var path: String?,
    var per_page: Int?,
    var to: Int?,
    var total: Int?
)