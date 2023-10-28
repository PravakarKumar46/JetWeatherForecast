package com.example.jetweatherforecast.screen.favourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.Favourite
import com.example.jetweatherforecast.navigation.WeatherScreen
import com.example.jetweatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    navController: NavController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Favourite title",
                icon = Icons.Default.ArrowBack,
                false,
                navController = navController){
                navController.popBackStack()
            }
        },
        content = { padding ->

            Surface(modifier = Modifier
                .padding(dimensionResource(id = R.dimen.dp_5))
                .fillMaxWidth()) {

                Column(
                    modifier = Modifier.padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val list = favouriteViewModel.favList.collectAsState().value

                    LazyColumn {
                        items(items = list) { items ->
                            CityRow(items, navController = navController, favouriteViewModel)
                        }
                    }
                }
            }
        })

}

@Composable
fun CityRow(
    favourite: Favourite,
    navController: NavController,
    favouriteViewModel: FavouriteViewModel
) {

    Surface(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dp_3))
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dp_50))
            .clickable {
                navController.navigate(WeatherScreen.MainScreen.name + "/${favourite.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(dimensionResource(id = R.dimen.dp_6))),
        color = Color(0xFFB2DFDB)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = favourite.city,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp_4))
            )

            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    text = favourite.country,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_4)),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier.clickable {
                    favouriteViewModel.deleteFavourite(favourite)
                },
                tint = Color.Red.copy(alpha = 0.3f)
            )

        }

    }
}