package com.gurdiel.gestiondesoporte.presentacion.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.gurdiel.gestiondesoporte.R
import com.gurdiel.gestiondesoporte.ui.theme.SelectedField
import com.gurdiel.gestiondesoporte.ui.theme.UnselectedField
import com.gurdiel.gestiondesoporte.ui.theme.azulM
import com.gurdiel.gestiondesoporte.ui.theme.claroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.oscuroGrisM

@Composable
fun LoginScreen(
    loginviewModel: LoginViewModel,
    navigateToAdministrador: () -> Unit
) {

    val email :String by loginviewModel.email.observeAsState(initial = "")
    val password :String by loginviewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by loginviewModel.loginEnable.observeAsState(initial = false)

    val focusRequester = remember {
        FocusRequester()
    }


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
            contentDescription = "Presentacion",
            modifier = Modifier
                .size(150.dp, 150.dp)
                .padding(vertical = 30.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Email:",
            color = oscuroGrisM,
            fontSize = 39.sp,
            fontWeight = FontWeight.Bold
        )
        TextField(
            value = email, onValueChange = { loginviewModel.onLoginChange(it, password) },
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )
        Spacer(modifier = Modifier.weight(0.5f))

        Text(
            text = "Contraseña:",
            color = oscuroGrisM,
            fontSize = 39.sp,
            fontWeight = FontWeight.Bold
        )
        TextField(
            value = password,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { loginviewModel.onLoginChange(email, it) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )
        Spacer(Modifier.height(48.dp))

        Button(
            onClick = {
                loginviewModel.login(email,password,navigateToAdministrador) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = claroGrisM),
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