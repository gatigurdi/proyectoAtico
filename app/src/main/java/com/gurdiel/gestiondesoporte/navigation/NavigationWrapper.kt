package com.gurdiel.gestiondesoporte.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gurdiel.gestiondesoporte.presentacion.inicio.InicioScreen
import com.gurdiel.gestiondesoporte.presentacion.login.LoginScreen
import com.gurdiel.gestiondesoporte.presentacion.registro.RegistroScreen

@Composable
fun NavigationWrapper(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = "inicio") {
        composable("inicio") {
            InicioScreen(
                navigateToLogin = { navHostController.navigate("Login") },
                navigateToRegistro = {navHostController.navigate("Registro")}
            )
        }
        composable("Registro") { RegistroScreen() }
        composable("Login") { LoginScreen() }
    }

}