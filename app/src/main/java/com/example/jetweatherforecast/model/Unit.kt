package com.example.jetweatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "setting_table")
data class Unit(
    @Nonnull
    @PrimaryKey
    @ColumnInfo(name = "unit")
    val unit: String
)
