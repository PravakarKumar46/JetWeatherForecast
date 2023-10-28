package com.example.jetweatherforecast.repository

import com.example.jetweatherforecast.data.WeatherDao
import com.example.jetweatherforecast.model.Favourite
import com.example.jetweatherforecast.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavourites(): Flow<List<Favourite>> = weatherDao.getFavourites()

    suspend fun insertFavourite(favourite: Favourite) = weatherDao.insertFavourite(favourite)

    suspend fun updateFavourite(favourite: Favourite) = weatherDao.updateFavourite(favourite)

    suspend fun deleteAllFavourites() = weatherDao.deleteFavourites()

    suspend fun deleteFavourite(favourite: Favourite) = weatherDao.deleteFavourite(favourite)

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)

}