package com.example.jetweatherforecast.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.Unit
import com.example.jetweatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    var unitToggleState by remember { mutableStateOf(false) }

    val measurementUnits = listOf("Imperial (F)", "Metric (C)")

    val choiceFromDB = settingsViewModel.unitList.collectAsState().value

    val defaultChoice = if (choiceFromDB.isNullOrEmpty()) measurementUnits[0] else choiceFromDB[0].unit

    var choiceState by remember { mutableStateOf(defaultChoice) }

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Settings",
                icon = Icons.Default.ArrowBack,
                false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        },
    ) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dp_15))
                )

                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        choiceState = if (unitToggleState) {
                            "Imperial (F)"
                        } else {
                            "Metric (C)"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.5f)
                        .clip(shape = RectangleShape)
                        .padding(dimensionResource(id = R.dimen.dp_5))
                        .background(Color.Magenta.copy(alpha = 0.4f))
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit °F" else "Celsius °C")
                }
                Button(
                    onClick = {
                              settingsViewModel.deleteAllUnits()
                        settingsViewModel.insertUnits(Unit(unit = choiceState))
                    },
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dp_3))
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_34)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEFBE42))
                ) {

                    Text(
                        text = "Save",
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_4)),
                        color = Color.White,
                        fontSize = with(LocalDensity.current) {
                            dimensionResource(R.dimen.sp_17).toSp()
                        })
                }
            }
        }
    }

}