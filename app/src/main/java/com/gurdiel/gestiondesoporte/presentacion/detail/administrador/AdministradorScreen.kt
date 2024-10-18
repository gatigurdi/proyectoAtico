package com.gurdiel.gestiondesoporte.presentacion.detail.administrador

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gurdiel.gestiondesoporte.domain.model.Averia
import com.gurdiel.gestiondesoporte.domain.model.Usuario
import com.gurdiel.gestiondesoporte.ui.theme.amarilloM
import com.gurdiel.gestiondesoporte.ui.theme.azulM
import com.gurdiel.gestiondesoporte.ui.theme.claroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.naranjaM
import com.gurdiel.gestiondesoporte.ui.theme.oscuroGrisM

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdministradorScreen(
    navigateToLogin: () -> Unit,
    administradorViewModel: AdministradorViewModel = hiltViewModel()
) {

    val usuariosState = administradorViewModel.usuarios.collectAsState()
    val averiasState = administradorViewModel.averias.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(claroGrisM, azulM),
                    startY = 0f,
                    endY = 1200f
                )
            )
            .padding(horizontal = 32.dp, vertical = 40.dp)
    ) {

        Column (modifier = Modifier.weight(1f)){


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


        Column (modifier = Modifier.weight(1f)){

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


        Button(
            onClick = { administradorViewModel.logout { navigateToLogin() } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = amarilloM,
                disabledContainerColor = oscuroGrisM
            ),
        ) {
            Text(
                text = "LOGOUT",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



@Composable
fun AveriasItem(averia: Averia) {
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
                text = averia.titulo, modifier = Modifier
                    .padding(horizontal = 4.dp)
            )
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
