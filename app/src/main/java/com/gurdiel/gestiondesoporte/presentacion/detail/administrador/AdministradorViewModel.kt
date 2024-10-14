package com.gurdiel.gestiondesoporte.presentacion.detail.administrador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurdiel.gestiondesoporte.data.network.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdministradorViewModel @Inject constructor(
    private val authService: AuthService
):ViewModel() {

    //NECESITAMOS EL VIEWMODEL PERO HHAY COSAS COMPARTIDAS VAMOS REFACTORIZANDO LUEGO? DALE DALE

    fun logout(navigateToLogin: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            authService.logout()
        }
        navigateToLogin()
    }

}