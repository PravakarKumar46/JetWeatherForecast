package com.example.jetweatherforecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jetweatherforecast.model.Favourite
import kotlinx.coroutines.flow.Flow
import com.example.jetweatherforecast.model.Unit

@Dao
interface WeatherDao {

    @Query("select * from fav_table")
    fun getFavourites(): Flow<List<Favourite>>

    @Query("select * from fav_table where city =:city")
    suspend fun getFavById(city: String): Favourite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourite(favourite: Favourite)

    @Query("delete from fav_table")
    suspend fun deleteFavourites()

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    @Query("select * from setting_table")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("delete from setting_table")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)

}