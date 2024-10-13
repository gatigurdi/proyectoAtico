package com.gurdiel.gestiondesoporte.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gurdiel.gestiondesoporte.presentacion.detail.administrador.Administrador
import com.gurdiel.gestiondesoporte.presentacion.inicio.InicioScreen
import com.gurdiel.gestiondesoporte.presentacion.login.LoginScreen
import com.gurdiel.gestiondesoporte.presentacion.login.LoginViewModel
import com.gurdiel.gestiondesoporte.presentacion.registro.RegistroScreen
import com.gurdiel.gestiondesoporte.presentacion.registro.RegistroViewModel

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    registroViewModel: RegistroViewModel
    ) {
    var start= "inicio"

    //Esto puede venir del login cuando creemos los tres tipos de usuario probamos.

    if(loginViewModel.isLogin()){
        start = "Administrador"
    }

    NavHost(navController = navHostController, startDestination = start) {
        composable("inicio") {
            InicioScreen(
                navigateToLogin = { navHostController.navigate("Login") },
                navigateToRegistro = { navHostController.navigate("Registro") }
            )
        }
        composable("Registro") {
            RegistroScreen(
                registroViewModel,
                navigateToLogin = {navHostController.navigate("Login")}) }
        composable("Login") {
            LoginScreen(
                loginViewModel,
                navigateToAdministrador = { navHostController.navigate("Administrador")}) }

        composable("Administrador") { Administrador()  }
    }

}