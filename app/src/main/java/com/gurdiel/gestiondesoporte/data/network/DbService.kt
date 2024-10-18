package com.gurdiel.gestiondesoporte.data.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.gurdiel.gestiondesoporte.data.response.AveriaResponse
import com.gurdiel.gestiondesoporte.data.response.UsuarioResponse
import com.gurdiel.gestiondesoporte.domain.model.Averia
import com.gurdiel.gestiondesoporte.domain.model.Usuario
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DbService @Inject constructor(
    private val db: FirebaseFirestore,
) {

    companion object {
        const val USUARIOS_PATH = "usuarios"
        const val AVERIAS_PATH = "averias"
        const val AVERIAS_USUARIO_DOCUMENT = "averiasCreadas"
        const val FECHA = "date"

    }

    suspend fun getAllUsuarios(): List<Usuario> {

        return db.collection(USUARIOS_PATH).get().await().map { usuario ->
            usuario.toObject(UsuarioResponse::class.java).toDomain()
        }
    }
    suspend fun getAllAverias(): List<Averia> {

        return db.collection(AVERIAS_PATH).get().await().map { averia ->
            averia.toObject(AveriaResponse::class.java).toDomain()
        }
    }


//    fun getUsuarios(): Flow<List<Usuario>> {
//        return db
//            .collection(USUARIOS_PATH)
//            .orderBy(FECHA, Query.Direction.DESCENDING)
//            .snapshots()
//            .map { qs -> qs.toObjects(UsuarioResponse::class.java).mapNotNull {
//                usuarioResponse -> usuarioToDomain(usuarioResponse)
//            } }
//    }

//    fun usuarioToDomain(usuarioResponse: UsuarioResponse):Usuario?{
//
//        if (usuarioResponse.)
//
//        return null
//    }

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

    suspend fun getAveriasUsuario(id:String): List<String> {
        //Me busca dentro de una colecion y dentro de otra.
       return db
           .collection(USUARIOS_PATH)
           .document(id)
           .get().await().data?.get(AVERIAS_USUARIO_DOCUMENT) as? List<String> ?: emptyList()
    }

    fun registrarUsuario(usuarioModel: Usuario):Boolean {

        val usuario = hashMapOf(
            "id" to usuarioModel.id,
            "name" to usuarioModel.name,
            "email" to usuarioModel.email,
            "rol" to usuarioModel.rol,
            "fecha" to usuarioModel.date,
            "averiasCreadas" to usuarioModel.averiasCreadas,
            "averiasAsignadas" to usuarioModel.averiasAsignadas
        )

        return db.collection(USUARIOS_PATH).document(usuarioModel.id).set(usuario).isSuccessful
    }


}