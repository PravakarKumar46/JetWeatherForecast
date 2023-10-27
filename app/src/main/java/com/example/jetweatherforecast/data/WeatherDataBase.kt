package com.example.jetweatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetweatherforecast.model.Favourite

@Database(entities = [Favourite::class], version = 1, exportSchema = false)
abstract class WeatherDataBase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}