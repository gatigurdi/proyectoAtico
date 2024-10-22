package com.gurdiel.gestiondesoporte.presentacion.detail.administrador

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PanelAdmin(
    navigateTo: (String) -> Unit,
    administradorViewModel: AdministradorViewModel,

    ) {

    val items = listOf(
        BottomNavigationItem(
            title = "Usuarios",
            selectedIcon = Icons.Filled.AccountCircle,
            unslectedIcon = Icons.Filled.AccountCircle,
            hasNews = true
        ),
        BottomNavigationItem(
            title = "Averias",
            selectedIcon = Icons.Filled.Build,
            unslectedIcon = Icons.Filled.Build,
            hasNews = false
        )
    )

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }


    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex == index
                            navigateTo(item.title)


                        },
                        label = { Text(text = item.title) },
                        icon = {
                            BadgedBox(badge = {
                                if (item.hasNews) {
                                    Badge()
                                }

                            }) {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unslectedIcon,
                                    contentDescription = item.title
                                )

                            }
                        })
                }
            }
        }
    ) {


    }

}


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unslectedIcon: ImageVector,
    val hasNews: Boolean
)