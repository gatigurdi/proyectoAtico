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
import com.gurdiel.gestiondesoporte.presentacion.detail.administrador.UsuarioItem
import com.gurdiel.gestiondesoporte.ui.theme.naranjaM

@Composable
fun UsuariosShow(administradorViewModel: AdministradorViewModel) {

    val usuariosState = administradorViewModel.usuarios.collectAsState()

    Column (){
        Text(
            text = "Usuarios",
            color = naranjaM,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(36.dp)
        )
        LazyRow {
            items(usuariosState.value) {
                UsuarioItem(it)
            }
        }

    }
}