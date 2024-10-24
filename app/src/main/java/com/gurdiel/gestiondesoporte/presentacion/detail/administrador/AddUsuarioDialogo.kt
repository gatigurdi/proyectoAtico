package com.gurdiel.gestiondesoporte.presentacion.detail.administrador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.gurdiel.gestiondesoporte.R
import com.gurdiel.gestiondesoporte.ui.theme.SelectedField
import com.gurdiel.gestiondesoporte.ui.theme.UnselectedField
import com.gurdiel.gestiondesoporte.ui.theme.amarilloM
import com.gurdiel.gestiondesoporte.ui.theme.azulM
import com.gurdiel.gestiondesoporte.ui.theme.claroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.oscuroGrisM

@Composable
fun AddUsuarioDialogo(
    showDialogo: Boolean,
    onDismiss: () -> Unit,
    administradorViewModel: AdministradorViewModel
) {

    val nombre: String by administradorViewModel.nombre.observeAsState(initial = "")
    val email: String by administradorViewModel.email.observeAsState(initial = "")
    val password: String by administradorViewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by administradorViewModel.loginEnable.observeAsState(initial = false)
    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(value = false) }

    if (showDialogo) {

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
                    text = "Registro usuario:",
                    color = oscuroGrisM,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { administradorViewModel.onLoginChange(it, email, password) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = UnselectedField,
                        focusedContainerColor = SelectedField,
                        focusedTextColor = Color.White,
                    ),
                    label = { Text(text = "Nombre") },
                    placeholder = { Text(text = "Nombre") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { administradorViewModel.onLoginChange(nombre, it, password) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = UnselectedField,
                        focusedContainerColor = SelectedField,
                        focusedTextColor = Color.White,
                    ),
                    label = { Text(text = "Email") },
                    placeholder = { Text(text = "Email") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                //HACER FUERA DE AQUÍ CUANDO TENGA TIEMPO HOSTIA
                val visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
                OutlinedTextField(
                    value = password,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { administradorViewModel.onLoginChange(nombre, email, it) },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = UnselectedField,
                        focusedContainerColor = SelectedField,
                        focusedTextColor = Color.White
                    ),
                    trailingIcon = @Composable {
                        if (showPassword) {
                            IconButton(onClick = { showPassword = false }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibilityoff),
                                    contentDescription = "hide_password",
                                    tint = amarilloM
                                )
                            }
                        } else {
                            IconButton(
                                onClick = { showPassword = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility),
                                    contentDescription = "hide_password",
                                    tint = amarilloM
                                )
                            }
                        }
                    },
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "Password") },
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.clearFocus() }
                    )
                )
                val rol = RadioButtonRol()




                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        administradorViewModel.registroUsuario(nombre, email, password, rol)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = amarilloM,
                        disabledContainerColor = oscuroGrisM,
                    ),
                    enabled = loginEnable
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

//            Column(
//                Modifier
//                    .fillMaxWidth()
//                    .background(Color.White)
//                    .padding(16.dp)
//            ) {
//                Text(
//                    text = "Recordarme de...",
//                    fontSize = 18.sp,
//                    modifier = Modifier.align(Alignment.CenterHorizontally),
//                    fontWeight = FontWeight.Bold
//                )
//                Spacer(modifier = Modifier.size(16.dp))
//                TextField(
//                    value = miTarea,
//                    onValueChange = { miTarea = it },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true,
//                    maxLines = 1
//                )
//                Spacer(modifier = Modifier.size(16.dp))
//                Button(onClick = {
//                    onUsuarioAdd(usuario)
//                    miTarea = ""
//                }, modifier = Modifier.fillMaxWidth()) {
//                    Text(text = "Añadir tú tarea.")
//                }
//            }

        }
    }

}