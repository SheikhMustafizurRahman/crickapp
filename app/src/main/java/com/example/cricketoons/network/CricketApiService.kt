package com.example.cricketoons.network

import com.example.cricketoons.model.apiFixture.FixtureResponse
import com.example.cricketoons.model.apiSpecificTeamwithSquad.TeamSquadFromAPI
import com.example.cricketoons.model.fixtureWithTeam.FixturewithTeam
import com.example.cricketoons.model.playerApiResponse.SquadResponse
import com.example.cricketoons.model.rankingAPI.RankingResponse
import com.example.cricketoons.model.roomCountry.CountryResponse
import com.example.cricketoons.model.roomLeague.LeagueResponse
import com.example.cricketoons.model.roomSeason.SeasonResponse
import com.example.cricketoons.model.roomStages.StageResponse
import com.example.cricketoons.model.roomTeams.TeamSquad
import com.example.cricketoons.model.roomVenue.VenueResponse
import com.example.cricketoons.util.Constants.Companion.API_KEY
import com.example.cricketoons.util.Constants.Companion.RECENT_MATCH_PARAMS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CricketApiService {

    @GET("fixtures?api_token=${API_KEY}")
    suspend fun getFixture(): FixtureResponse

    @GET("livescores")
    suspend fun getlive(
        @Query("include") params: String = "localteam,visitorteam",
        @Query("api_token") api_token: String = API_KEY
    ): FixtureResponse

    @GET("leagues?api_token=${API_KEY}")
    suspend fun fetchLeagueFromAPI(): LeagueResponse

    @GET("seasons?api_token=${API_KEY}")
    suspend fun fetchSeasonFromAPI(): SeasonResponse

    @GET("stages?api_token=${API_KEY}")
    suspend fun fetchStageFromAPI(): StageResponse

    @GET("venues?api_token=${API_KEY}")
    suspend fun fetchVenueFromAPI(): VenueResponse

    @GET("countries")
    suspend fun fetchCountryFromAPI(@Query("api_token") api_token: String = API_KEY): CountryResponse

    @GET("teams?api_token=${API_KEY}")
    suspend fun getTeamFromAPI(): TeamSquad

    @GET("fixtures")
    suspend fun getUpcomingMatch(
        @Query("filter[starts_between]") duration: String,
        @Query("include") params: String = "localteam,visitorteam",
        @Query("sort") date:String= "starting_at",
        @Query("api_token") api_token: String = API_KEY
    ): FixturewithTeam

    @GET("fixtures")
    suspend fun getRecentMatch(
        @Query("filter[starts_between]") duration: String ,
        @Query("include") params: String = RECENT_MATCH_PARAMS,
        @Query("sort") date:String= "starting_at",
        @Query("api_token") api_token: String = API_KEY
    ): FixtureResponse

    @GET("teams/{teamId}/squad/23")
    suspend fun fetchRecentSquadFromAPI(
        @Path("teamId") teamId: Int,
        @Query("api_token") api_token: String = API_KEY
    ): TeamSquadFromAPI

    @GET("players/{playerId}")
    suspend fun fetchPlayerByIDFromAPI(
        @Path("playerId") playerId: Int,
        @Query("api_token") api_token: String = API_KEY
    ): SquadResponse

    @GET("team-rankings")
    suspend fun fetchRankingDataFromAPI(
        @Query("filter[type]") format:String,
        @Query("api_token") api_token: String = API_KEY
    ):RankingResponse

}