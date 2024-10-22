package com.gurdiel.gestiondesoporte.presentacion.detail.administrador

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurdiel.gestiondesoporte.data.network.AuthService
import com.gurdiel.gestiondesoporte.data.network.DbService
import com.gurdiel.gestiondesoporte.domain.model.Averia
import com.gurdiel.gestiondesoporte.domain.model.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdministradorViewModel @Inject constructor(
    private val authService: AuthService,
    private val dbService: DbService
):ViewModel() {

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios

    private val _averias = MutableStateFlow<List<Averia>>(emptyList())
    val averias: StateFlow<List<Averia>> = _averias

    private val _showDialogo = MutableLiveData<Boolean>()
    val showDialogo: LiveData<Boolean> = _showDialogo

    init {
        getUsuarios()
    }

    private fun getUsuarios() {
        viewModelScope.launch {
            val usuarios: List<Usuario> = withContext(Dispatchers.IO){
                dbService.getAllUsuarios()
            }
            val averias: List<Averia> = withContext(Dispatchers.IO){
                dbService.getAllAverias()
            }

            _usuarios.value = usuarios
            _averias.value = averias
        }
    }

    fun logout(navigateToLogin: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            authService.logout()
        }
        navigateToLogin()
    }

    fun onMostrarDialogoClickUsuario() {
        _showDialogo.value = true
    }
    fun onDialogoCerrarUsuario() {
        _showDialogo.value = false
    }

    fun onMostrarDialogoClickAveria() {
        TODO("Not yet implemented")
    }

    fun onUsuarioNew(usuario: Usuario) {
        _showDialogo.value = false
        viewModelScope.launch {

        }
    }

}