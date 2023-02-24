package com.example.cricketoons.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.model.fixtureWithTeam.FixtureDataWteam
import com.example.cricketoons.model.rankingAPI.RankingData
import com.example.cricketoons.model.room.CricDao
import com.example.cricketoons.model.roomCountry.Country
import com.example.cricketoons.model.roomLeague.League
import com.example.cricketoons.model.roomSeason.Season
import com.example.cricketoons.model.roomStages.Stages
import com.example.cricketoons.model.roomTeams.TeamData
import com.example.cricketoons.model.roomVenue.Venue
import com.example.cricketoons.network.RetrofitInstance.Companion.CrickMonkAPI

private const val TAG = "CricRepo"
class CricRepo(private val cricDao: CricDao) {

    val readTeamData: LiveData<List<TeamData>> = cricDao.getTeamsFromRoom()
    val readSquadData: LiveData<List<Squad>> = cricDao.readSquadPlayersFromRoom()


    suspend fun readUpcoming(timegap: String): List<FixtureDataWteam> = CrickMonkAPI.getUpcomingMatch(timegap).data


    suspend fun getTeams(): List<TeamData> {
        return CrickMonkAPI.getTeamFromAPI().data
    }

    suspend fun fetchRecentSquadFromAPI(teamId: Int): List<Squad>? {
        return CrickMonkAPI.fetchRecentSquadFromAPI(teamId).data.squad
    }
    suspend fun fetchCountrySeasonStageLeagueVenueFromAPI() {
        insertSeasonFromAPI(CrickMonkAPI.fetchSeasonFromAPI().data)
        insertStageFromAPI(CrickMonkAPI.fetchStageFromAPI().data)
        insertLeagueFromAPI(CrickMonkAPI.fetchLeagueFromAPI().data)
        insertVenueFromAPI(CrickMonkAPI.fetchVenueFromAPI().data)
        insertCounty(CrickMonkAPI.fetchCountryFromAPI().data)

    }

    private suspend fun insertVenueFromAPI(data: List<Venue>) {
        cricDao.insertVenue(data)
    }

    private suspend fun insertLeagueFromAPI(leagueList: List<League>) {
        cricDao.insertLeague(leagueList)
    }

    private suspend fun insertStageFromAPI(stageList: List<Stages>) {
        cricDao.insertStages(stageList)
    }

    private suspend fun insertSeasonFromAPI(seasonList: List<Season>) {
        cricDao.insertSeason(seasonList)
    }

    private suspend fun insertCounty(countryList: List<Country>) {
        cricDao.insertCountry(countryList)
    }

    suspend fun insertTeamInRoom(teamData: List<TeamData>) {
        cricDao.insertTeam(teamData)
    }

    suspend fun insertSquadInRoom(squadList: List<Squad>?) {
        cricDao.insertSquad(squadList)
    }

    suspend fun readCountryNameFromRoom(country_id: Int): String {
        return cricDao.readCountryNameByID(country_id)
    }

    suspend fun checkIfTeamExistInRoom(team_id:Int):Int{
        return cricDao.checkIfTeamExistInRoom(team_id)
    }
    suspend fun getTeamLogoFromRoom(team_id:Int):String{
        return cricDao.getTeamLogoFromRoom(team_id)
    }

    suspend fun getTeamNameFromRoom(team_id:Int):String{
        return cricDao.getTeamNameFromRoom(team_id)
    }

    suspend fun readSquadByCountryID(teamId: Int): List<Squad>? {
        return CrickMonkAPI.fetchRecentSquadFromAPI(teamId).data.squad
    }

    suspend fun fetchRecentMatchesFromAPI(timegap: String): List<Fixture> = CrickMonkAPI.getRecentMatch(timegap).data

    suspend fun fetchLive():List<Fixture> = CrickMonkAPI.getlive().data
    suspend fun getPlayerNameByID(playerId: Int?): String? {
        return cricDao.getPlayerNameByID(playerId)
    }

    suspend fun insertSquadTable(squad: Squad){
        cricDao.insertIntoSquadTable(squad)
    }

    suspend fun getLeagueNamebyID(leagueId:Int?):String{
        return cricDao.getLeagueNamebyID(leagueId)
    }

    suspend fun getLeagueLogobyID(leagueId:Int?):String{
        return cricDao.getLeagueLogobyID(leagueId)
    }

    suspend fun fetchPlayerByIDFromAPI(playerId: Int): Squad {
        Log.d(TAG, "fetchPlayerByIDFromAPI: calling")
        val squad=CrickMonkAPI.fetchPlayerByIDFromAPI(playerId).data
        Log.d(TAG, "fetchPlayerByIDFromAPI: $squad")
        return squad
    }

    suspend fun fetchRankingDataFromAPI(type:String):List<RankingData>{
        return CrickMonkAPI.fetchRankingDataFromAPI(type).data
    }

    suspend fun getVenueNameByID(venueId: Int?): String {
        return cricDao.getVenueNameByID(venueId)
    }
}