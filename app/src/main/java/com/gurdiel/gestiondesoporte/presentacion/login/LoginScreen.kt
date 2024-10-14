package com.gurdiel.gestiondesoporte.presentacion.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.gurdiel.gestiondesoporte.R
import com.gurdiel.gestiondesoporte.ui.theme.SelectedField
import com.gurdiel.gestiondesoporte.ui.theme.UnselectedField
import com.gurdiel.gestiondesoporte.ui.theme.amarilloM
import com.gurdiel.gestiondesoporte.ui.theme.azulM
import com.gurdiel.gestiondesoporte.ui.theme.claroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.oscuroGrisM

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navigateToDetail: (String) -> Unit) {

    val email :String by loginViewModel.email.observeAsState(initial = "")
    val password :String by loginViewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by loginViewModel.loginEnable.observeAsState(initial = false)
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

        Image(
            painter = painterResource(id = R.drawable._logueo),
            contentDescription = "Presentation",
            modifier = Modifier
                .size(150.dp, 150.dp)
                .padding(vertical = 30.dp)
        )

        Spacer(modifier = Modifier.weight(0.1f))

        Text(
            text = "Email:",
            color = oscuroGrisM,
            fontSize = 39.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        TextField(
            value = email, onValueChange = { loginViewModel.onLoginChange(it, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField,
                focusedTextColor = Color.White,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Next)}
            )
        )
        Spacer(modifier = Modifier.weight(0.25f))

        Text(
            text = "Contraseña:",
            color = oscuroGrisM,
            fontSize = 39.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        //HACER FUERA DE AQUÍ CUANDO TENGA TIEMPO HOSTIA
        val visualTransformation = if(showPassword){
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        }

        TextField(
            value = password,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { loginViewModel.onLoginChange(email, it) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField,
                focusedTextColor = Color.White
            ),
            trailingIcon = {
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
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.clearFocus() }
            )
        )
//        TextField(
//            value = password,
//            modifier = Modifier.fillMaxWidth(),
//            onValueChange = { loginviewModel.onLoginChange(email, it) },
//            colors = TextFieldDefaults.colors(
//                unfocusedContainerColor = UnselectedField,
//                focusedContainerColor = SelectedField,
//                focusedTextColor = Color.White,
//                cursorColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent
//            ),
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//            keyboardActions = KeyboardActions(
//                onNext = {focusManager.moveFocus(FocusDirection.Next)}
//            )
//        )
        Spacer(modifier = Modifier.weight(0.25f))

        Button(
            onClick = { loginViewModel.login(email,password, navigateToDetail) },

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = amarilloM, disabledContainerColor = oscuroGrisM),
            enabled = loginEnable
        ) {
            Text(
                text = "Loguear",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1f))

    }

}

