package com.example.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherforecast.screen.main.MainScreen
import com.example.jetweatherforecast.screen.main.MainViewModel
import com.example.jetweatherforecast.screen.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreen.SplashScreen.name) {

        composable(WeatherScreen.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreen.MainScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, mainViewModel)
        }

    }
}