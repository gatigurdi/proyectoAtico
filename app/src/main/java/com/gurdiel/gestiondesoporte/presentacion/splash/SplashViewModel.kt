package com.gurdiel.gestiondesoporte.presentacion.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurdiel.gestiondesoporte.data.network.AuthService
import com.gurdiel.gestiondesoporte.data.network.DbService
import com.gurdiel.gestiondesoporte.domain.model.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authService: AuthService,
    private val db: DbService

) : ViewModel() {

    var _uiState = MutableStateFlow<SplashUiState>(SplashUiState())
    val uiState:StateFlow<SplashUiState> = _uiState

    init {
        viewModelScope.launch {
            if(authService.currentUser()!=null){
                val usuario = async { db.getUnUsuario(authService.currentUser()!!.uid)}.await()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            destino = usuario!!.rol.toString(),
                            currentUser = usuario)
                    }
            }else{
                _uiState.update { it.copy(
                    isLoading = false
                ) }
            }
        }
    }

}

data class SplashUiState(
    val isLoading: Boolean = true,
    val destino: String = "Inicio",
    val currentUser: Usuario? = null

    )
