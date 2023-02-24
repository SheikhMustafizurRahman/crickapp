package com.example.cricketoons.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.model.roomCountry.Country
import com.example.cricketoons.model.roomLeague.League
import com.example.cricketoons.model.roomSeason.Season
import com.example.cricketoons.model.roomStages.Stages
import com.example.cricketoons.model.roomTeams.TeamData
import com.example.cricketoons.model.roomVenue.Venue

@Dao
interface CricDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(teamData: List<TeamData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSquad(squad: List<Squad>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countryList: List<Country>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeague(leagueList: List<League>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeason(seasonList: List<Season>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVenue(venueList: List<Venue>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStages(stageList: List<Stages>)

    @Query("select * from teams where national_team= true order by name")
    fun getTeamsFromRoom():LiveData<List<TeamData>>

    @Query("select * from squadTable order by country_id")
    fun readSquadPlayersFromRoom():LiveData<List<Squad>>

    @Query("select name from country where id=:country_id")
    suspend fun readCountryNameByID(country_id:Int):String

    @Query("select * from squadTable where id=:country_id")
    fun readSquadByCountryID(country_id:Int):LiveData<List<Squad>>

    @Query("SELECT COUNT(*) FROM teams WHERE id = :id")
    suspend fun checkIfTeamExistInRoom(id: Int): Int

    @Query("SELECT name FROM teams WHERE id = :id")
    suspend fun getTeamNameFromRoom(id: Int): String
    @Query("SELECT image_path FROM teams WHERE id = :id")
    suspend fun getTeamLogoFromRoom(id: Int): String
    @Query("SELECT fullname FROM squadTable WHERE id = :playerId")
    suspend fun getPlayerNameByID(playerId: Int?): String?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoSquadTable(squad: Squad)

    @Query("SELECT name FROM leagueTable WHERE id = :leagueId")
    suspend fun getLeagueNamebyID(leagueId:Int?):String

    @Query("SELECT image_path FROM leagueTable WHERE id = :leagueId")
    suspend fun getLeagueLogobyID(leagueId:Int?):String
    @Query("SELECT name FROM Venue WHERE id = :venueId")
    suspend fun getVenueNameByID(venueId: Int?):String
}