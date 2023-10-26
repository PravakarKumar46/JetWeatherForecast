package com.example.jetweatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import coil.compose.rememberImagePainter
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.Main
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals


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

    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dp_12))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_4))) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_20))
            )

            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.titleMedium
            )

        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_20))
            )

            Text(
                text = "${weather.pressure}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_20))
            )

            Text(
                text = "${weather.humidity} mph",//miles per hour
                style = MaterialTheme.typography.titleMedium
            )
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
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

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

@Composable
fun WeatherDetails(data: Weather) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color(0xFFEEF1EF),
        shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.dp_14))
    ) {
        LazyColumn(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_2)),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dp_1))
        ) {
            items(items = data.weather) {
                WeatherDetailsRow(data)
            }
        }
    }
}

@Composable
fun WeatherDetailsRow(data: Weather) {

    val imageUrl = "https://openweathermap.org/img/w/${data.weather[0].icon}.png"

    Surface(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dp_3))
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(dimensionResource(id = R.dimen.dp_6))),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDate(data.dt).split(",")[0],
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_5))
            )
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_1)),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(
                    text = data.weather[0].description,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_4)),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(formatDecimals(data.main.tempMax) + "°")
                }

                withStyle(style = SpanStyle(color = Color.LightGray)) {
                    append(formatDecimals(data.main.tempMin) + "°")
                }
            })
        }
    }

}