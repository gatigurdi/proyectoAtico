package com.gurdiel.gestiondesoporte.presentacion.registro

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurdiel.gestiondesoporte.data.network.AuthService
import com.gurdiel.gestiondesoporte.data.network.DbService
import com.gurdiel.gestiondesoporte.domain.model.Rol
import com.gurdiel.gestiondesoporte.domain.model.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(

    private val authService: AuthService,
    private val dbService: DbService

): ViewModel() {

    private val _showConfirm = MutableLiveData<Boolean>()
    val showConfirm: LiveData<Boolean> = _showConfirm

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    fun onLoginChange(nombre:String, email: String, password: String) {
        _nombre.value = nombre
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)

    }

//    fun onMostrarConfirmacionClick(){
//        _showConfirm.value = true
//    }

    fun onConfirmacionCerrar(navigateToLogin: () -> Unit) {
        //una vez confirmamos el mensaje de registro nos lleva al login
        navigateToLogin()
        _showConfirm.value = false
    }


    private fun isValidEmail(email: String): Boolean  = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length > 5

    fun registro(nombre: String, email: String, password: String, rol: String) {

        var rol:Rol = recuperarRol(rol)
        var usuario:Usuario

        viewModelScope.launch {

            val authR = withContext(Dispatchers.IO){

                authService.register(email, password)

            }

            if(authR!=null){
                val id = authService.currentUser()!!.uid
                usuario = Usuario(id,name = nombre,email= email, rol = rol)
                dbService.registrarUsuario(usuario)
                _showConfirm.value = true

            }else{
                Log.i("Error Login", "Error al crear el usuario.")
            }
        }

    }

    private fun recuperarRol(rol: String): Rol {


        return Rol.EMPRESA
    }

}