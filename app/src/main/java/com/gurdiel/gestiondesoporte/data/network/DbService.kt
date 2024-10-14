package com.gurdiel.gestiondesoporte.data.network

import android.util.Log
import androidx.compose.ui.input.key.Key.Companion.U
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.gurdiel.gestiondesoporte.data.response.AveriasResponse
import com.gurdiel.gestiondesoporte.data.response.UsuarioResponse
import com.gurdiel.gestiondesoporte.presentacion.model.Usuario
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DbService @Inject constructor(
    private val db: FirebaseFirestore
) {

    companion object {
        const val USUARIOS_PATH = "usuarios"
        const val AVERIAS_PATH = "averias"
        const val AVERIAS_USUARIO_DOCUMENT = "averiasCreadas"

    }

    suspend fun getAllUsuarios(): List<Usuario> {
        return db.collection("usuarios").get().await().map { usuario ->
            usuario.toObject(UsuarioResponse::class.java).toDomain()
        }
    }

    suspend fun getLastUsuario(): Usuario? {

     return   db
            .collection(USUARIOS_PATH)
            .orderBy("id", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
            .firstOrNull()?.toObject(UsuarioResponse::class.java)?.toDomain()

    }

    suspend fun getUnUsuario(id:String): Usuario? {

        return   db
            .collection(USUARIOS_PATH)
            .document(id)
            .get()
            .await()
            .toObject(UsuarioResponse::class.java)?.toDomain()

    }

    suspend fun getAverias():List<String>{
        //Me busca dentro de una colecion y dentro de otra.
       return db
            .collection(AVERIAS_PATH)
            .document(AVERIAS_USUARIO_DOCUMENT)
            .get().await()
            .toObject(AveriasResponse::class.java)?.ids ?: emptyList()
    }

    fun registrarUsuario(usuarioModel:Usuario):Boolean {

        val usuario = hashMapOf(
            "id" to usuarioModel.id,
            "name" to usuarioModel.nombre,
            "email" to usuarioModel.email,
            "rol" to usuarioModel.rol,
            "fecha" to usuarioModel.date,
            "averiasCreadas" to usuarioModel.averiasCreadas,
            "averiasAsignadas" to usuarioModel.averiasAsignadas
        )

        return db.collection(USUARIOS_PATH).document(usuarioModel.id).set(usuario).isSuccessful
    }


}