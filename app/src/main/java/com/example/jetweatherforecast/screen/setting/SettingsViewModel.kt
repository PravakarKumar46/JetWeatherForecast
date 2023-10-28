package com.example.jetweatherforecast.screen.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.model.Unit
import com.example.jetweatherforecast.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: WeatherDBRepository
) : ViewModel() {

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().collect { listOfUnit ->
                if (listOfUnit.isNullOrEmpty()) {
                    Log.d("pppppppp", ": Empty fav")
                } else {
                    _unitList.value = listOfUnit
                    Log.d("pppppppp", ": ${unitList.value}")
                }
            }
        }
    }

    fun insertUnits(unit: Unit) = viewModelScope.launch { repository.insertUnit(unit) }

    fun updateUnit(unit: Unit) = viewModelScope.launch { repository.updateUnit(unit) }

    fun deleteUnit(unit: Unit) = viewModelScope.launch { repository.deleteUnit(unit) }

    fun deleteAllUnits() = viewModelScope.launch { repository.deleteAllUnits() }
}