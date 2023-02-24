package com.example.cricketoons.model.apiFixture

data class FixtureResponse(
    var `data`: List<Fixture>,
    var links: Links?,
    var meta: Meta?
)