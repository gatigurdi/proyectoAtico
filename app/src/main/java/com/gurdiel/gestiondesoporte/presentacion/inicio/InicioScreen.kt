package com.gurdiel.gestiondesoporte.presentacion.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurdiel.gestiondesoporte.R
import com.gurdiel.gestiondesoporte.ui.theme.azulM
import com.gurdiel.gestiondesoporte.ui.theme.claroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.oscuroGrisM

@Composable
fun InicioScreen(navigateToLogin: ()-> Unit, navigateToRegistro: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(claroGrisM, azulM),
                    startY = 0f,
                    endY = 1200f
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.weight(0.5f))
        Image(
            painter = painterResource(id = R.drawable.averia),
            contentDescription = "Presentacion",
            modifier = Modifier.size(150.dp, 150.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "IT SUPPORT",
            color = oscuroGrisM,
            fontSize = 39.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(0.5f))
        Button(
            onClick = { navigateToRegistro() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = claroGrisM)
        ) {
            Text(
                text = "Registro",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = { navigateToLogin() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = claroGrisM)
        ) {
            Text(
                text = "Entrar",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1f))

    }

}