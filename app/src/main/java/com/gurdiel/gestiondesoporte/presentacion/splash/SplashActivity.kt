package com.gurdiel.gestiondesoporte.presentacion.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gurdiel.gestiondesoporte.navigation.NavigationWrapper
import com.gurdiel.gestiondesoporte.ui.theme.GestionDeSoporteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val splashViewModel:SplashViewModel by viewModels()
    private lateinit var navHostController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()

            GestionDeSoporteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SplashScreen(splashViewModel = splashViewModel, navHostController = navHostController)
                }
            }
        }
    }
}

@Composable
fun SplashScreen(splashViewModel: SplashViewModel, navHostController: NavHostController) {

    val uiState by splashViewModel.uiState.collectAsState()

    if (uiState.isLoading){
        CircularProgressIndicator()
    }else{
        NavigationWrapper(navHostController = navHostController,uiState.destino)
    }


}
