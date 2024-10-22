package com.gurdiel.gestiondesoporte.presentacion.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurdiel.gestiondesoporte.presentacion.detail.administrador.AdministradorViewModel
import com.gurdiel.gestiondesoporte.presentacion.detail.administrador.AveriasItem
import com.gurdiel.gestiondesoporte.ui.theme.naranjaM

@Composable
fun AveriasShow(administradorViewModel: AdministradorViewModel) {

    val averiasState = administradorViewModel.averias.collectAsState()

    Column (){

        Text(
            text = "Averias",
            color = naranjaM,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(36.dp)
        )
        LazyRow {
            items(averiasState.value) {
                AveriasItem(it)
            }
        }

    }
}