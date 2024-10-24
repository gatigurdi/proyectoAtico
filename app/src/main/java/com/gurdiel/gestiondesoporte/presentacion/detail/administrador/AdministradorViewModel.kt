package com.gurdiel.gestiondesoporte.presentacion.detail.administrador

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurdiel.gestiondesoporte.data.network.AuthService
import com.gurdiel.gestiondesoporte.data.network.DbService
import com.gurdiel.gestiondesoporte.domain.model.Averia
import com.gurdiel.gestiondesoporte.domain.model.Rol
import com.gurdiel.gestiondesoporte.domain.model.Usuario
import com.gurdiel.gestiondesoporte.presentacion.splash.SplashUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdministradorViewModel @Inject constructor(
    private val authService: AuthService,
    private val dbService: DbService
) : ViewModel() {

    //Quitar luego
//    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
//    val usuarios: StateFlow<List<Usuario>> = _usuarios

    var _uiState = MutableStateFlow<AdministradorUiState>(AdministradorUiState())
    val uiState:StateFlow<AdministradorUiState> = _uiState

    private val _showDialogoUsuario = MutableLiveData<Boolean>()
    val showDialogoUsuario: LiveData<Boolean> = _showDialogoUsuario

    private val _showDialogoAveria = MutableLiveData<Boolean>()
    val showDialogoAveria: LiveData<Boolean> = _showDialogoAveria

    private val _titulo = MutableLiveData<String>()
    val titulo: LiveData<String> = _titulo

    private val _descripcion = MutableLiveData<String>()
    val descripcion: LiveData<String> = _descripcion

    //Borrar luego si tal
//    private val _nombre = MutableLiveData<String>()
//    val nombre: LiveData<String> = _nombre

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _averiaEnable = MutableLiveData<Boolean>()
    val averiaEnable: LiveData<Boolean> = _averiaEnable


    init {
        viewModelScope.launch {
            dbService.getUsuarios().collect { us ->
                _uiState.update {
                    it.copy(usuarios = us)
                }
            }
        }
        viewModelScope.launch {
            dbService.getAverias().collect { av ->
                _uiState.update {
                    it.copy(averias = av)
                }
            }
        }
    }

    fun registroUsuario(nombre: String, email: String, password: String, rol: Rol) {

        var usuario: Usuario

        viewModelScope.launch {
            val authR = withContext(Dispatchers.IO) {
                authService.crear(email, password)
            }

            if (authR != null) {

                val id = authService.currentUser()!!.uid
                usuario = Usuario(id, name = nombre, email = email, rol = rol)
                dbService.registrarUsuario(usuario)
                _showDialogoUsuario.value = false
                _nombre.value = ""
                _email.value = ""
                _password.value = ""

            } else {
                Log.i("Error Login", "Error al crear el usuario.")
            }
        }

    }

    fun registroAveria(){

        val averia : Averia = Averia(titulo = titulo.value!!, descripcion = descripcion.value!!)

        viewModelScope.launch {
            dbService.registrarAveria(averia)
        }
        _showDialogoAveria.value = false
        _titulo.value = ""
        _descripcion.value = ""

    }

    fun onLoginChange(nombre: String, email: String, password: String) {
        _nombre.value = nombre
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)

    }

    fun onAveriaChange(titulo: String, descripcion: String) {
        _titulo.value = titulo
        _descripcion.value = descripcion
        _averiaEnable.value = isValidTitulo(titulo) && isValidDescripcion(descripcion)

    }

    private fun isValidTitulo(titulo: String): Boolean = titulo.length > 0

    private fun isValidDescripcion(descripcion: String): Boolean = descripcion.length > 0

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 5


    //Lo hacemos con flows ahora luego eliminamos.
    private fun getUsuarios() {
        viewModelScope.launch {
            val usuarios: Flow<List<Usuario>> = withContext(Dispatchers.IO) {
                dbService.getUsuarios()
            }
            val averias: List<Averia> = withContext(Dispatchers.IO) {
                dbService.getAllAverias()
            }

            //_usuarios.value = usuarios
        }
    }

    fun logout(navigateToLogin: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            authService.logout()
        }
        navigateToLogin()
    }

    fun onMostrarDialogoClickUsuario() {
        _showDialogoUsuario.value = true
    }

    fun onDialogoCerrarUsuario() {
        _showDialogoUsuario.value = false
    }
    fun onDialogoCerrarAveria() {
        _showDialogoAveria.value = false
    }

    fun onMostrarDialogoClickAveria() {
        _showDialogoAveria.value = true
    }

}

data class AdministradorUiState(
    val isLoading: Boolean = false,
    val usuarios: List<Usuario> = emptyList(),
    val averias: List<Averia> = emptyList()
)