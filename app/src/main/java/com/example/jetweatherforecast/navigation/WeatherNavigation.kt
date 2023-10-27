package com.example.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetweatherforecast.screen.about.AboutScreen
import com.example.jetweatherforecast.screen.favourite.FavouriteScreen
import com.example.jetweatherforecast.screen.main.MainScreen
import com.example.jetweatherforecast.screen.main.MainViewModel
import com.example.jetweatherforecast.screen.search.SearchScreen
import com.example.jetweatherforecast.screen.setting.SettingScreen
import com.example.jetweatherforecast.screen.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreen.SplashScreen.name) {

        composable(WeatherScreen.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        //www.google.com/cityName="seattle"
        val route = WeatherScreen.MainScreen.name
        composable(
            "$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                })
        ) { navBackStackEntry->
            navBackStackEntry.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = city)
            }
        }

        composable(WeatherScreen.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreen.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreen.FavoriteScreen.name) {
            FavouriteScreen(navController = navController)
        }

        composable(WeatherScreen.SettingScreen.name) {
            SettingScreen(navController = navController)
        }

    }
}