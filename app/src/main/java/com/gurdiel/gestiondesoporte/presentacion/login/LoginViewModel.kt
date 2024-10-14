package com.gurdiel.gestiondesoporte.presentacion.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.gurdiel.gestiondesoporte.data.network.AuthService
import com.gurdiel.gestiondesoporte.data.network.DbService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

    private val authService: AuthService,
    private val db: DbService


) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading


    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }
    fun prueba():Boolean{
        return true
    }

    fun destino(param: (String) -> Unit) {

        viewModelScope.launch {
            if (authService.currentUser() != null) {
                val destino = async{
                    db.getUnUsuario(id = authService.currentUser()?.uid.toString())?.rol.toString()
                }.await()
                param(destino)
                Log.i("ROL5", destino)
            }
        }

    }
    fun isLogin():Boolean {

        var logueado = false
        viewModelScope.launch {
            if (authService.currentUser() != null) {
                logueado = true
            }
        }
        return logueado
    }

    fun iniciada(): FirebaseUser? {
        return authService.currentUser()
    }


    fun login(email: String, password: String, navigateToDetail: (String) -> Unit) {

        viewModelScope.launch {
                authService.login(email, password)
                navigateToDetail(db.getUnUsuario(id = authService.currentUser()?.uid.toString())?.rol.toString())
            }
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 5


    fun logininiciada(uid: String, destino: (String) -> Unit) {
        viewModelScope.launch { destino(db.getUnUsuario(uid)?.rol.toString()) }
    }


}