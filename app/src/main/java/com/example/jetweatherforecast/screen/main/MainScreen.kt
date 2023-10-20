package com.example.jetweatherforecast.screen.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Main
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = "bangalore")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        ManinScaffold(weather = weatherData.data!!, navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManinScaffold(weather: Weather, navController: NavController) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.name + " , ${weather.sys.country}",
            icon = Icons.Default.ArrowBack,
            navController = navController, elevation = dimensionResource(id = R.dimen.dp_5)
        ) {
            Log.d("button_clicked", "ManinScaffold: Button Clicked...")
        }
    },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                MainContent(data = weather)
            }
        })

}

@Composable
fun MainContent(data: Weather) {

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
        HumidityWindPressureRow(weather = data.main)
        Divider()
        SunsetSunRiseRow(weather = data)
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {

    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "image icon",
        modifier = Modifier.size(dimensionResource(id = R.dimen.dp_88))
    )

}

@Composable
fun HumidityWindPressureRow(weather: Main) {

    Row(modifier = Modifier
        .padding(dimensionResource(id = R.dimen.dp_12))
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_4))) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_20)))

            Text(text = "${weather.humidity}%",
                style = MaterialTheme.typography.titleMedium)

        }
        Row {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_20)))

            Text(text = "${weather.pressure}",
                style = MaterialTheme.typography.titleMedium)
        }
        Row {
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_20)))

            Text(text = "${weather.humidity} mph",//miles per hour
                style = MaterialTheme.typography.titleMedium)
        }
    }

}


@Composable
fun SunsetSunRiseRow(weather: Weather) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.dp_15),
                bottom = dimensionResource(id = R.dimen.dp_6)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {

        Row {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise",
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_30))
            )
            Text(
                text = formatDateTime(weather.sys.sunrise),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Row {
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset",
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_30))
            )
            Text(
                text = formatDateTime(weather.sys.sunset),
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}
