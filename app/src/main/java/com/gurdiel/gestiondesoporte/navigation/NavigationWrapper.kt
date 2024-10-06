package com.gurdiel.gestiondesoporte.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.gurdiel.gestiondesoporte.presentacion.detail.usuario.Administrador
import com.gurdiel.gestiondesoporte.presentacion.inicio.InicioScreen
import com.gurdiel.gestiondesoporte.presentacion.login.LoginScreen
import com.gurdiel.gestiondesoporte.presentacion.login.LoginViewModel
import com.gurdiel.gestiondesoporte.presentacion.registro.RegistroScreen

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    auth: FirebaseAuth,

    ) {
    var start= "inicio"

    if(auth.currentUser == null ){
        start = "Administrador"
    }

    NavHost(navController = navHostController, startDestination = start) {
        composable("inicio") {
            InicioScreen(
                navigateToLogin = { navHostController.navigate("Login") },
                navigateToRegistro = { navHostController.navigate("Registro") }
            )
        }
        composable("Registro") { RegistroScreen() }
        composable("Login") { LoginScreen(loginViewModel, navigateToAdministrador = { navHostController.navigate("Administrador")}) }
        composable("Administrador") { Administrador()  }
    }

}