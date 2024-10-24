package com.gurdiel.gestiondesoporte.presentacion.detail.tecnico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gurdiel.gestiondesoporte.ui.theme.amarilloM
import com.gurdiel.gestiondesoporte.ui.theme.azulM
import com.gurdiel.gestiondesoporte.ui.theme.claroGrisM
import com.gurdiel.gestiondesoporte.ui.theme.oscuroGrisM

@Composable
fun TecnicoScreen(navigateToLogin: () -> Unit,tecnicoViewModel: TecnicoViewModel = hiltViewModel()){
    
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
        Text(text = "Hola hemos accedido a TECNICO")
        Button(
            onClick = { tecnicoViewModel.logout { navigateToLogin() } },
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