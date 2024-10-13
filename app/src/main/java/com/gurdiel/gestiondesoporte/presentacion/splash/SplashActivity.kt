package com.gurdiel.gestiondesoporte.presentacion.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gurdiel.gestiondesoporte.navigation.NavigationWrapper
import com.gurdiel.gestiondesoporte.presentacion.login.LoginViewModel
import com.gurdiel.gestiondesoporte.presentacion.registro.RegistroViewModel
import com.gurdiel.gestiondesoporte.ui.theme.GestionDeSoporteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val registroViewModel: RegistroViewModel by viewModels()


    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            navHostController = rememberNavController()
            GestionDeSoporteTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background){

                    NavigationWrapper(navHostController,loginViewModel,registroViewModel)

                }
            }
        }
    }
}