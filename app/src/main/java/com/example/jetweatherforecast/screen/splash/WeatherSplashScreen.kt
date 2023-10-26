package com.example.jetweatherforecast.screen.splash


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.navigation.WeatherScreen
import kotlinx.coroutines.delay


@Composable
fun WeatherSplashScreen(navController: NavController) {

    val defaultCity = "moscow"
    val scale = remember {
        Animatable(0f)
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800, easing = {
                OvershootInterpolator(8f).getInterpolation(it)
            })
        )

        delay(2000)
        navController.navigate(WeatherScreen.MainScreen.name+"/$defaultCity")
    })

    Surface(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dp_15))
            .size(dimensionResource(id = R.dimen.dp_330))
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = dimensionResource(id = R.dimen.dp_2), color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_1)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "sunny icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_95))
            )

            Text(
                text = context.getString(R.string.title_splash),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.LightGray
            )

        }
    }
}