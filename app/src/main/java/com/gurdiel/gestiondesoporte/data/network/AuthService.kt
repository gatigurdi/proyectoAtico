package com.gurdiel.gestiondesoporte.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
){

    suspend fun login(email:String, password:String): FirebaseUser?{

        return firebaseAuth.signInWithEmailAndPassword(email,password).await().user
        //is Succesfull
        //.isFail varios metodos para reconocer el inicio
    }

    fun currentUser(): FirebaseUser?{
        return firebaseAuth.currentUser
    }

    suspend fun register(email: String, password: String): FirebaseUser? {

        return firebaseAuth.createUserWithEmailAndPassword(email, password).await().user

    }

    fun logout() {
        firebaseAuth.signOut()
    }


}