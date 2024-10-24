package com.gurdiel.gestiondesoporte.presentacion.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurdiel.gestiondesoporte.domain.model.Usuario
import com.gurdiel.gestiondesoporte.ui.theme.naranjaM

@Composable
fun UsuariosShow(usuarios:List<Usuario>) {

    Column (){
        Text(
            text = "Usuarios",
            color = naranjaM,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(36.dp)
        )
        LazyRow {
            items(usuarios) {
                UsuarioItem(it)
            }
        }

    }
}

@Composable
fun UsuarioItem(usuario: Usuario) {

    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onLongPress = {
//                            //tareasViewModel.onMostrarConfirmacionClick()
//                        },
//                        onDoubleTap = {
//                            //Viajar a otra pantalla. Con el tareaModel y editarlo.
//                        })
//                }
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = usuario.name, modifier = Modifier
                    .padding(horizontal = 4.dp)
            )
        }
    }


}