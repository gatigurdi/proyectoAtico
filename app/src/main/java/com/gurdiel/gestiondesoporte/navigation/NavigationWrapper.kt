package com.gurdiel.gestiondesoporte.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gurdiel.gestiondesoporte.presentacion.detail.administrador.AdministradorScreen
import com.gurdiel.gestiondesoporte.presentacion.detail.empresa.EmpresaScreen
import com.gurdiel.gestiondesoporte.presentacion.detail.tecnico.TecnicoScreen
import com.gurdiel.gestiondesoporte.presentacion.inicio.InicioScreen
import com.gurdiel.gestiondesoporte.presentacion.login.LoginScreen
import com.gurdiel.gestiondesoporte.presentacion.registro.RegistroScreen


@Composable
fun NavigationWrapper(
    navHostController: NavHostController,// Single source of truth
    destino: String
) {

    NavHost(
        navController = navHostController,
        startDestination = destino
    ) {

        composable("Inicio") {
            InicioScreen(
                navigateToLogin = { navHostController.navigate("Login") },
                navigateToRegistro = { navHostController.navigate("Registro") }
            )
        }
        composable("Registro") {
            RegistroScreen(
                navigateToLogin = { navHostController.navigate("Login") })
        }

        composable("Login") {
            LoginScreen(
                navigateToDetail = { destino -> navHostController.navigate(destino) }
            )
        }

        composable("ADMINISTRADOR") {
            AdministradorScreen(
                navigateTo = {
                    destino -> navHostController.navigate(destino)

                },
                navigateToLogin = {
                    navHostController.navigate("Login") {
                        popUpTo(navHostController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }

            )
        }
        composable("TECNICO") {
            TecnicoScreen(
                navigateToLogin = {
                    navHostController.navigate("Login") {
                        popUpTo(navHostController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable("EMPRESA") {
            EmpresaScreen(
                navigateToLogin = {
                    navHostController.navigate("Login") {
                        popUpTo(navHostController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
