package com.example.jetweatherforecast.screen.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.navigation.WeatherScreen
import com.example.jetweatherforecast.screen.setting.SettingsViewModel
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widgets.HumidityWindPressureRow
import com.example.jetweatherforecast.widgets.SunsetSunRiseRow
import com.example.jetweatherforecast.widgets.WeatherAppBar
import com.example.jetweatherforecast.widgets.WeatherDetails
import com.example.jetweatherforecast.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {

    val currentCity: String = if (city!!.isBlank()) "Seattle" else city
    val unitFromDB = settingsViewModel.unitList.collectAsState().value
    var unit by remember { mutableStateOf("imperial") }
    var isImperial by remember { mutableStateOf(false) }

    if (!unitFromDB.isNullOrEmpty()) {

        unit = unitFromDB[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"
    }
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(
            city = currentCity,
            units = unit
        )
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        ManinScaffold(weather = weatherData.data!!, navController, isImperial = isImperial)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManinScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.name + " , ${weather.sys.country}",
            /*icon = Icons.Default.ArrowBack,*/
            navController = navController, elevation = dimensionResource(id = R.dimen.dp_5),
            onAddActionClicked = {
                navController.navigate(WeatherScreen.SearchScreen.name)
            }
        ) {
            Log.d("button_clicked", "ManinScaffold: Button Clicked...")
        }
    },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                MainContent(data = weather, isImperial = isImperial)
            }
        })

}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {

    val imageUrl = "https://openweathermap.org/img/w/${data.weather[0].icon}.png"

    Column(
        Modifier
            .padding(dimensionResource(id = R.dimen.dp_4))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatDate(data.dt),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_6))
        )

        Surface(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.dp_4))
                .size(dimensionResource(id = R.dimen.dp_200)),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WeatherStateImage(imageUrl = imageUrl)

                Text(
                    text = formatDecimals(data.main.temp) + "°",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = data.weather[0].main,
                    fontStyle = FontStyle.Italic
                )

            }
        }
        HumidityWindPressureRow(weather = data, isImperial = isImperial)
        Divider()
        SunsetSunRiseRow(weather = data)
        Text(
            text = "This week",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        WeatherDetails(data)
    }
}
