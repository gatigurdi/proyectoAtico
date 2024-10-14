package com.gurdiel.gestiondesoporte.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gurdiel.gestiondesoporte.presentacion.detail.administrador.Administrador
import com.gurdiel.gestiondesoporte.presentacion.detail.empresa.EmpresaScreen
import com.gurdiel.gestiondesoporte.presentacion.detail.tecnico.TecnicoScreen
import com.gurdiel.gestiondesoporte.presentacion.inicio.InicioScreen
import com.gurdiel.gestiondesoporte.presentacion.login.LoginScreen
import com.gurdiel.gestiondesoporte.presentacion.login.LoginViewModel
import com.gurdiel.gestiondesoporte.presentacion.registro.RegistroScreen


@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    ) {

    if(loginViewModel.isLogin()){
        val usuario = loginViewModel.iniciada()
        if (usuario != null) {
            loginViewModel.logininiciada(usuario.uid) { destino ->
                navHostController.navigate(
                    destino
                )
            }
        }
    }

    NavHost(navController = navHostController, startDestination = "Inicio") {

        composable("Inicio") {
            InicioScreen(
                navigateToLogin = { navHostController.navigate("Login") },
                navigateToRegistro = { navHostController.navigate("Registro") }
            )
        }
        composable("Registro") {
            RegistroScreen(
                navigateToLogin = { navHostController.navigate("Login") }) }

        composable("Login") {
            LoginScreen(
                loginViewModel = loginViewModel,
                navigateToDetail = { destino -> navHostController.navigate(destino)}
            ) }

        composable("ADMINISTRADOR") {
            Administrador(

                navigateToLogin = {
                    navHostController.navigate("Login"){
                        popUpTo(navHostController.graph.startDestinationId){
                            inclusive = true
                        } } }
            )
        }
        composable("TECNICO") {
            TecnicoScreen(
                navigateToLogin = {
                    navHostController.navigate("Login"){
                        popUpTo(navHostController.graph.startDestinationId){
                            inclusive = true
                        } } }
            )
        }
        composable("EMPRESA") {
            EmpresaScreen(
                navigateToLogin = {
                    navHostController.navigate("Login"){
                        popUpTo(navHostController.graph.startDestinationId){
                            inclusive = true
                        } } }
            )
        }
    }

}
