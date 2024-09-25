package com.gurdiel.gestiondesoporte

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gurdiel.gestiondesoporte.navigation.NavigationWrapper
import com.gurdiel.gestiondesoporte.ui.theme.GestionDeSoporteTheme

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            GestionDeSoporteTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background){

                    NavigationWrapper(navHostController)

                }
            }
        }
    }
}