package com.example.jetweatherforecast.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherObject(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
): Parcelable