package com.gurdiel.gestiondesoporte.presentacion.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gurdiel.gestiondesoporte.data.network.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

    private val authService: AuthService


) : ViewModel() {


    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    fun isLogin(): Boolean {
        var isLogin = false

        viewModelScope.launch {
            if(authService.currentUser()!=null){
                isLogin = true
            }

        }
        return isLogin
    }

    fun login(email: String, password: String, navigateToDetail: () -> Unit){

        viewModelScope.launch {

            val result = withContext(Dispatchers.IO){
                authService.login(email,password)
                //val id = authService.currentUser()?.uid
            }
            if (result!=null){

                navigateToDetail()

            }else{
                //Error
            }

        }
    }
    private fun isValidEmail(email: String): Boolean  = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length > 5


}