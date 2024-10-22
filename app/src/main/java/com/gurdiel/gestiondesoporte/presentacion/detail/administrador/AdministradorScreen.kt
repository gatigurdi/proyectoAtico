package com.gurdiel.gestiondesoporte.presentacion.detail.administrador

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.gurdiel.gestiondesoporte.domain.model.Averia
import com.gurdiel.gestiondesoporte.domain.model.Rol
import com.gurdiel.gestiondesoporte.domain.model.Usuario
import com.gurdiel.gestiondesoporte.presentacion.detail.AveriasShow
import com.gurdiel.gestiondesoporte.presentacion.detail.UsuariosShow
import com.gurdiel.gestiondesoporte.ui.theme.amarilloM
import com.gurdiel.gestiondesoporte.ui.theme.azulM
import com.gurdiel.gestiondesoporte.ui.theme.claroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.verdeM

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdministradorScreen(
    navigateTo: (String) -> Unit,
    navigateToLogin: () -> Unit,
    administradorViewModel: AdministradorViewModel = hiltViewModel()
) {
    val showDialogo: Boolean by administradorViewModel.showDialogo.observeAsState(false)

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
        floatingActionButton = { agregarBtn(selectedItemIndex, administradorViewModel) },
        floatingActionButtonPosition = FabPosition.Center,

        bottomBar = {
            NavigationBar(containerColor = verdeM) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
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
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        listOf(claroGrisM, azulM),
                        startY = 0f,
                        endY = 1200f
                    )
                )
                .fillMaxSize()
        ) {
            AddUsuarioDialogo(
                showDialogo,
                onDismiss = { administradorViewModel.onDialogoCerrarUsuario() },
                onUsuarioAdd = { administradorViewModel.onUsuarioNew(it) }
            )
            if (selectedItemIndex == 0) {
                UsuariosShow(administradorViewModel)
            } else AveriasShow(administradorViewModel)
        }


    }
}

@Composable
fun AddUsuarioDialogo(
    showDialogo: Boolean,
    onDismiss: () -> Unit,
    onUsuarioAdd: (Usuario) -> Unit
) {

    var miTarea by remember { mutableStateOf("") }
    var usuario: Usuario = Usuario(id = "s", name = "", email = "", rol = Rol.EMPRESA)

    if(showDialogo) {
        Dialog(onDismissRequest = { onDismiss }) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Recordarme de...",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = miTarea,
                    onValueChange = { miTarea = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    onUsuarioAdd(usuario)
                    miTarea = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir tú tarea.")
                }
            }

        }
    }

}

@Composable
fun agregarBtn(selectedItemIndex: Int, administradorViewModel: AdministradorViewModel) {

    FloatingActionButton(
        onClick = {
            if (selectedItemIndex == 0) {

                administradorViewModel.onMostrarDialogoClickUsuario()

            } else administradorViewModel.onMostrarDialogoClickAveria()
        },
        containerColor = amarilloM
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Añadir")
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


//    val usuariosState = administradorViewModel.usuarios.collectAsState()
//    val averiasState = administradorViewModel.averias.collectAsState()

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Brush.verticalGradient(
//                    listOf(claroGrisM, azulM),
//                    startY = 0f,
//                    endY = 1200f
//                )
//            )
//            .padding(horizontal = 32.dp, vertical = 40.dp)
//    ) {
//        Button(
//            onClick = { administradorViewModel.logout { navigateToLogin() } },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 32.dp, vertical = 10.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = amarilloM,
//                disabledContainerColor = oscuroGrisM
//            ),
//        ) {
//            Text(
//                text = "LOGOUT",
//                color = Color.Black,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }