package com.gurdiel.gestiondesoporte.presentacion.detail.empresa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.gurdiel.gestiondesoporte.presentacion.detail.AveriasShow
import com.gurdiel.gestiondesoporte.presentacion.detail.administrador.AddAveriaDialogo
import com.gurdiel.gestiondesoporte.ui.theme.SelectedField
import com.gurdiel.gestiondesoporte.ui.theme.UnselectedField
import com.gurdiel.gestiondesoporte.ui.theme.amarilloM
import com.gurdiel.gestiondesoporte.ui.theme.azulM
import com.gurdiel.gestiondesoporte.ui.theme.claroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.oscuroGrisM

@Composable
fun EmpresaScreen(
    navigateToLogin: () -> Unit,
    empresaViewModel: EmpresaViewModel = hiltViewModel()
){

    val showDialogoAveria: Boolean by empresaViewModel.showDialogoAveria.observeAsState(false)
    val uiState by empresaViewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = { AgregarBtnE(empresaViewModel) },
        floatingActionButtonPosition = FabPosition.Center,

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
            //Estas cosas se podrán reutilizar y así no se repite código, es difícil...verlo. OJO!
            AddAveriaDialogoE(
                showDialogoAveria,
                onDismiss = { empresaViewModel.onDialogoCerrarAveria() },
                empresaViewModel = empresaViewModel
            )
            AveriasShow(uiState.averias)
            //LogOut(navigateToLogin = { empresaViewModel.logout { navigateToLogin() } })

        }
    }
}

@Composable
fun LogOut(navigateToLogin: () -> Unit){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                listOf(claroGrisM, azulM),
                startY = 0f,
                endY = 1200f
            )
        )
        .padding(horizontal = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Hola hemos accedido a EMPRESA")
        Button(
            onClick = { navigateToLogin() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = amarilloM, disabledContainerColor = oscuroGrisM),
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
fun AddAveriaDialogoE(showDialogoAveria: Boolean, onDismiss: ()-> Unit, empresaViewModel: EmpresaViewModel) {

    val titulo: String by empresaViewModel.titulo.observeAsState(initial = "")
    val descripcion: String by empresaViewModel.descripcion.observeAsState(initial = "")
    val averiaEnable: Boolean by empresaViewModel.averiaEnable.observeAsState(initial = false)
    val focusManager = LocalFocusManager.current

    if (showDialogoAveria) {

        Dialog(onDismissRequest = { onDismiss() }) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(claroGrisM, azulM),
                            startY = 0f,
                            endY = 1200f
                        )
                    )
                    .padding(horizontal = 32.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = "Registro avería:",
                    color = oscuroGrisM,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { empresaViewModel.onAveriaChange(it,descripcion) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = UnselectedField,
                        focusedContainerColor = SelectedField,
                        focusedTextColor = Color.White,
                    ),
                    label = { Text(text = "Título") },
                    placeholder = { Text(text = "Título") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { empresaViewModel.onAveriaChange(titulo, it) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = UnselectedField,
                        focusedContainerColor = SelectedField,
                        focusedTextColor = Color.White,
                    ),
                    label = { Text(text = "Descripción") },
                    placeholder = { Text(text = "Descripción") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        empresaViewModel.registroAveria()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = amarilloM,
                        disabledContainerColor = oscuroGrisM,
                    ),
                    enabled = averiaEnable
                ) {
                    Text(
                        text = "Confirmar",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))

            }
        }
    }

}

@Composable
fun AgregarBtnE(empresaViewModel: EmpresaViewModel) {

    FloatingActionButton(
        onClick = {empresaViewModel.onMostrarDialogoClickAveria()},
        containerColor = amarilloM
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Añadir")
    }

}