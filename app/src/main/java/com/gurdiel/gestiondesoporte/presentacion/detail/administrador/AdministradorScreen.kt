package com.gurdiel.gestiondesoporte.presentacion.detail.administrador

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.gurdiel.gestiondesoporte.R
import com.gurdiel.gestiondesoporte.domain.model.Averia
import com.gurdiel.gestiondesoporte.domain.model.Rol
import com.gurdiel.gestiondesoporte.domain.model.Usuario
import com.gurdiel.gestiondesoporte.presentacion.detail.AveriasShow
import com.gurdiel.gestiondesoporte.presentacion.detail.UsuariosShow
import com.gurdiel.gestiondesoporte.presentacion.registro.Dialogo
import com.gurdiel.gestiondesoporte.ui.theme.SelectedField
import com.gurdiel.gestiondesoporte.ui.theme.UnselectedField
import com.gurdiel.gestiondesoporte.ui.theme.amarilloM
import com.gurdiel.gestiondesoporte.ui.theme.azulM
import com.gurdiel.gestiondesoporte.ui.theme.claroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.oscuroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.verdeM

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdministradorScreen(
    navigateToLogin: () -> Unit,//BOTON LOGOUT SALIR A LA PANTALLA DE LOGIN
    administradorViewModel: AdministradorViewModel = hiltViewModel()
) {
    val showDialogoUsuario: Boolean by administradorViewModel.showDialogoUsuario.observeAsState(false)
    val showDialogoAveria: Boolean by administradorViewModel.showDialogoAveria.observeAsState(false)
    val uiState by administradorViewModel.uiState.collectAsState()

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

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        floatingActionButton = { AgregarBtn(selectedItemIndex, administradorViewModel) },
        floatingActionButtonPosition = FabPosition.Center,

        bottomBar = {
            NavigationBar(containerColor = verdeM) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = { selectedItemIndex = index },
                        label = { Text(text = item.title) },
                        icon = {
                            BadgedBox(badge = {
                                if (item.hasNews) {
                                    Badge()
                                }
                            })
                            {
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
                showDialogoUsuario,
                onDismiss = { administradorViewModel.onDialogoCerrarUsuario() },
                administradorViewModel = administradorViewModel
            )
            AddAveriaDialogo(
                showDialogoAveria,
                onDismiss = { administradorViewModel.onDialogoCerrarAveria() },
                administradorViewModel = administradorViewModel
            )
            if (selectedItemIndex == 0) {
                UsuariosShow(uiState.usuarios)
            } else AveriasShow(uiState.averias)
        }


    }
}



@Composable
fun RadioButtonRol(): Rol {

    val radioOptions = listOf(Rol.EMPRESA, Rol.TECNICO, Rol.ADMINISTRADOR)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Column {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text.toString(),
                    style = MaterialTheme.typography.bodyMedium.merge(),
                    modifier = Modifier.padding(start = 8.dp, top = 14.dp)
                )
            }
        }
    }
    return selectedOption
}


@Composable
fun AgregarBtn(selectedItemIndex: Int, administradorViewModel: AdministradorViewModel) {

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