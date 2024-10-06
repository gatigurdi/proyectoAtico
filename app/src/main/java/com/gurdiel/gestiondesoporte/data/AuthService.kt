package com.gurdiel.gestiondesoporte.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthService @Inject constructor(private val firebaseAuth: FirebaseAuth){

    suspend fun login(email:String, password:String): FirebaseUser?{

        return firebaseAuth.signInWithEmailAndPassword(email,password).await().user
        //is Succesfull
        //.isFail varios metodos para reconocer el inicio
    }

    suspend fun currentUser(): FirebaseUser?{
        return firebaseAuth.currentUser
    }

}