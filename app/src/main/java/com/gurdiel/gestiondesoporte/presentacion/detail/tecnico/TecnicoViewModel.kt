package com.gurdiel.gestiondesoporte.presentacion.detail.tecnico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurdiel.gestiondesoporte.data.network.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TecnicoViewModel @Inject constructor(

    private val authService: AuthService

): ViewModel() {

    fun logout(navigateToLogin: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            authService.logout()
        }
        navigateToLogin()
    }


}