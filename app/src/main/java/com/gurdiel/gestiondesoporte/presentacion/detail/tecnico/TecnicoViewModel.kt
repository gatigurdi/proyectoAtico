package com.gurdiel.gestiondesoporte.presentacion.detail.tecnico

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurdiel.gestiondesoporte.data.network.AuthService
import com.gurdiel.gestiondesoporte.data.network.DbService
import com.gurdiel.gestiondesoporte.domain.model.Averia
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TecnicoViewModel @Inject constructor(

    private val authService: AuthService,
    private val dbService: DbService

) : ViewModel() {

    private val _showDialogoAveria = MutableLiveData<Boolean>()
    val showDialogoAveria: LiveData<Boolean> = _showDialogoAveria

    private val _titulo = MutableLiveData<String>()
    val titulo: LiveData<String> = _titulo

    private val _descripcion = MutableLiveData<String>()
    val descripcion: LiveData<String> = _descripcion

    private val _averiaEnable = MutableLiveData<Boolean>()
    val averiaEnable: LiveData<Boolean> = _averiaEnable

    var _uiState = MutableStateFlow<TecnicoUiState>(TecnicoUiState())
    val uiState: StateFlow<TecnicoUiState> = _uiState

    init {
        viewModelScope.launch {
            dbService.getAverias().collect { av ->
                _uiState.update {
                    it.copy(averias = averiasFiltradas(av))
                }
            }
        }
    }

    private fun averiasFiltradas(av: List<Averia>): List<Averia> {
        val idBuscado = authService.currentUser()!!.uid

        return av.filter { it.usuarioCreador == idBuscado }
    }

    fun logout(navigateToLogin: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            authService.logout()
        }
        navigateToLogin()
    }

    fun registroAveria() {

        val averia: Averia = Averia(titulo = titulo.value!!, descripcion = descripcion.value!!)

        viewModelScope.launch {
            dbService.registrarAveria(averia)
        }
        _showDialogoAveria.value = false
        _titulo.value = ""
        _descripcion.value = ""

    }

    fun onAveriaChange(titulo: String, descripcion: String) {
        _titulo.value = titulo
        _descripcion.value = descripcion
        _averiaEnable.value = isValidTitulo(titulo) && isValidDescripcion(descripcion)

    }

    private fun isValidTitulo(titulo: String): Boolean = titulo.length > 0

    private fun isValidDescripcion(descripcion: String): Boolean = descripcion.length > 0

    fun onMostrarDialogoClickAveria() {
        _showDialogoAveria.value = true
    }

    fun onDialogoCerrarAveria() {
        _showDialogoAveria.value = false
    }
}

data class TecnicoUiState(
    val isLoading: Boolean = false,
    val averias: List<Averia> = emptyList()
)
