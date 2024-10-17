package com.gurdiel.gestiondesoporte.presentacion.registro

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.gurdiel.gestiondesoporte.domain.model.Rol
import com.gurdiel.gestiondesoporte.ui.theme.SelectedField
import com.gurdiel.gestiondesoporte.ui.theme.UnselectedField
import com.gurdiel.gestiondesoporte.ui.theme.amarilloM
import com.gurdiel.gestiondesoporte.ui.theme.azulM
import com.gurdiel.gestiondesoporte.ui.theme.claroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.oscuroGrisM

@Composable
fun RegistroScreen(
    navigateToLogin: () -> Unit,
    registroViewModel: RegistroViewModel = hiltViewModel()
) {

    val nombre: String by registroViewModel.nombre.observeAsState(initial = "")
    val showConfirmacion by registroViewModel.showConfirm.observeAsState(false)
    val email: String by registroViewModel.email.observeAsState(initial = "")
    val password: String by registroViewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by registroViewModel.loginEnable.observeAsState(initial = false)
    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(value = false) }

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
            .padding(horizontal = 32.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.weight(0.5f))
        Text(
            text = "Registro usuario:",
            color = oscuroGrisM,
            fontSize = 39.sp,
            fontWeight = FontWeight.Bold,
        )

        Image(
            painter = painterResource(id = R.drawable.registro),
            contentDescription = "Registro",
            modifier = Modifier
                .size(150.dp, 150.dp)
                .padding(vertical = 30.dp)
        )

        Spacer(modifier = Modifier.weight(0.1f))

//        Text(
//            text = "Email:",
//            color = oscuroGrisM,
//            fontSize = 10.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.align(Alignment.Start)
//        )

        Dialogo(
            showConfirmacion,
            onDismiss = { registroViewModel.onConfirmacionCerrar(navigateToLogin) })
        OutlinedTextField(
            value = nombre, onValueChange = { registroViewModel.onLoginChange(it,email, password) },
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
        Spacer(modifier = Modifier.weight(0.01f))

        OutlinedTextField(
            value = email, onValueChange = { registroViewModel.onLoginChange(nombre, it, password) },
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
        Spacer(modifier = Modifier.weight(0.01f))

//        Text(
//            text = "Contraseña:",
//            color = oscuroGrisM,
//            fontSize = 10.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.align(Alignment.Start)
//        )

        //HACER FUERA DE AQUÍ CUANDO TENGA TIEMPO HOSTIA
        val visualTransformation = if(showPassword){
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        }
        OutlinedTextField(
            value = password,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { registroViewModel.onLoginChange(nombre, email, it) },
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
        Spacer(modifier = Modifier.weight(0.15f))

        Button(
            onClick = {
                registroViewModel.registro(nombre,email,password, Rol.EMPRESA)
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
                text = "Registrar",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1f))

    }

}

@Composable
fun Dialogo(showConfirmacion: Boolean, onDismiss: () -> Unit) {

    if (showConfirmacion) {

        Dialog(onDismissRequest = { onDismiss() }) {

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                TituloDialogo(text = "¡Atención!")
                Mensaje("Se ha creado el usuario logueate, por favor",R.drawable.visibilityazul)
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Confirmar")
                }
            }
        }
    }

}
@Composable
fun Mensaje(text: String, @DrawableRes drawable: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = "Eliminar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Yellow)
                .size(40.dp)
                .clip(CircleShape)
        )
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}
@Composable
fun TituloDialogo(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        color = Color.Red,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}
