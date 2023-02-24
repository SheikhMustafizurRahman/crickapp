package com.example.cricketoons.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cricketoons.database.CricketDB
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.model.fixtureWithTeam.FixtureDataWteam
import com.example.cricketoons.model.rankingAPI.RankingData
import com.example.cricketoons.model.roomTeams.TeamData
import com.example.cricketoons.repository.CricRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ViewModel"

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val _teams = MutableLiveData<List<TeamData>>()

    //private val fixtureDataList: LiveData<List<FixtureData>>
    private val teams: LiveData<List<TeamData>> = _teams
    private val repository: CricRepo
    private var flag: Boolean = true

    init {
        val dao = CricketDB.getDatabase(application).cricketDao()
        repository = CricRepo(dao)
        //fixtureDataList = repository.readFixtureData
    }

    suspend fun readUpcomingMatches(timegap: String): List<FixtureDataWteam> = repository.readUpcoming(timegap)


    fun getTeamsFromAPIStoreInRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _teams.postValue(repository.getTeams())
                Log.d(TAG, "getTeamsFromAPIStoreInRoom: ${repository.getTeams()}")
                teams.value?.let {
                    Log.d(TAG, "Storing Data in Room: called")
                    repository.insertTeamInRoom(it)
                }
            } catch (e: Exception) {
                Log.e(TAG, "getTeamsFromAPIAndStoreInRoom:${e.message}")
            }
        }
    }

    fun getTeamsFromRoom(): LiveData<List<TeamData>> = repository.readTeamData

    fun getALlSquadPlayerFromRoom(): LiveData<List<Squad>> = repository.readSquadData

    suspend fun getCountryNameFromRoom(country_id: Int): String =
        repository.readCountryNameFromRoom(country_id)

    suspend fun checkIfTeamExistInRoom(teamId: Int):Int=repository.checkIfTeamExistInRoom(teamId)
    suspend fun getTeamNameFromRoom(teamId: Int):String=repository.getTeamNameFromRoom(teamId)
    suspend fun getTeamLogoFromRoom(teamId: Int):String=repository.getTeamLogoFromRoom(teamId)

    fun getSquadFromAPIStoreInRoom(teamId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //fetching Squad data from api using the TeamID
                val currentSquad = repository.fetchRecentSquadFromAPI(teamId)
                repository.insertSquadInRoom(currentSquad)

            } catch (e: Exception) {
                Log.e(TAG, "getTeamsFromRoom:${e.message}")
            }
        }
    }

    suspend fun readSquadByCountryID(teamId: Int): List<Squad>? =
        repository.readSquadByCountryID(teamId)

    suspend fun insertCountryLeagueSeasonVenueStageFromAPIT0Room(){
        repository.fetchCountrySeasonStageLeagueVenueFromAPI()
    }

    suspend fun readRecentMatches(timegap: String): List<Fixture> =repository.fetchRecentMatchesFromAPI(timegap)
    suspend fun getLive():List<Fixture> = repository.fetchLive()
    suspend fun getPlayerNameByID(playerId: Int?): String? {
        return repository.getPlayerNameByID(playerId)
    }

    suspend fun fetchPlayerByIDFromAPI(playerId: Int) {
        val player=repository.fetchPlayerByIDFromAPI(playerId)
        Log.d(TAG, "fetchPlayerByIDFromAPI: calling fine")
        repository.insertSquadTable(player)
    }

    suspend fun getLeagueLogobyID(leagueId: Int?):String{
        return repository.getLeagueLogobyID(leagueId)
    }

    suspend fun getLeagueNamebyID(leagueId: Int?):String{
        return repository.getLeagueNamebyID(leagueId)
    }

    suspend fun fetchRankingFromAPI(type:String):List<RankingData>{
        return repository.fetchRankingDataFromAPI(type)
    }

    suspend fun getVenueNameByID(venueId: Int?): String {
        return repository.getVenueNameByID(venueId)
    }
}