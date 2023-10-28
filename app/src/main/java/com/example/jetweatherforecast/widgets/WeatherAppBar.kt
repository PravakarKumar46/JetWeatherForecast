package com.example.jetweatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.Favourite
import com.example.jetweatherforecast.navigation.WeatherScreen
import com.example.jetweatherforecast.screen.favourite.FavouriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    val showIt = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Surface(shadowElevation = elevation) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = with(LocalDensity.current) {
                            dimensionResource(R.dimen.sp_15).toSp()
                        })
                )
            },
            actions = {
                if (isMainScreen) {
                    IconButton(onClick = {onAddActionClicked.invoke()}) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                    }
                    IconButton(onClick = {
                        showDialog.value = true
                    }) {
                        Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "more icon")
                    }
                } else {
                    Box {}
                }

            },
            navigationIcon = {
                if (icon != null) {
                    Icon(imageVector = icon, contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.clickable {
                            onButtonClicked.invoke()
                        })
                }
                if (isMainScreen) {
                    val isAlreadyFavList = favouriteViewModel.favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }

                    if (isAlreadyFavList.isNullOrEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favourite icon",
                            modifier = Modifier
                                .scale(0.9f)
                                .clickable {
                                    val dataList = title.split(",")
                                    favouriteViewModel
                                        .insertFavourite(
                                            Favourite(
                                                city = dataList[0], // city name
                                                country = dataList[1] // country code
                                            )
                                        ).run { showIt.value = true }
                                },
                            tint = Color.Red.copy(alpha = 0.6f)
                        )
                    } else {
                        showIt.value = false
                        Box {}
                    }

                    ShowToast(context = context, showIt)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)

        )
    }

}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value){
        Toast.makeText(context, "City Added to Favourite", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {
    var expanded by remember {
        mutableStateOf(true)
    }

    val items = listOf("About", "Favourite", "Setting")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .absolutePadding(
                top = dimensionResource(id = R.dimen.dp_45),
                right = dimensionResource(id = R.dimen.dp_20)
            )
    ) {

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.dp_140))
                .background(Color.White)
        ) {

            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {
                        Text(text = text, modifier = Modifier.clickable {
                            navController.navigate(
                                when (text) {
                                    "About" -> WeatherScreen.AboutScreen.name
                                    "Favourite" -> WeatherScreen.FavoriteScreen.name
                                    else -> WeatherScreen.SettingScreen.name
                                }
                            )
                        }, fontWeight = FontWeight.W300)
                    },
                    onClick = {
                        expanded = false
                        showDialog.value = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = when (text) {
                                "About" -> Icons.Default.Info
                                "Favourite" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings

                            }, contentDescription = null,
                            tint = Color.LightGray
                        )
                    })
            }

        }

    }

}
