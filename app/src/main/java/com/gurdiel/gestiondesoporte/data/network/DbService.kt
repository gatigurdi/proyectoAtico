package com.gurdiel.gestiondesoporte.data.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.gurdiel.gestiondesoporte.data.network.DbService.Companion.AVERIAS_USUARIO_DOCUMENT
import com.gurdiel.gestiondesoporte.data.network.DbService.Companion.USUARIOS_PATH
import com.gurdiel.gestiondesoporte.data.response.AveriaResponse
import com.gurdiel.gestiondesoporte.data.response.UsuarioResponse
import com.gurdiel.gestiondesoporte.domain.model.Averia
import com.gurdiel.gestiondesoporte.domain.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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


    fun getUsuarios(): Flow<List<Usuario>> {
        return db
            .collection(USUARIOS_PATH)
            //.orderBy(FECHA, Query.Direction.DESCENDING)
            .snapshots()
            .map { qs -> qs.toObjects(UsuarioResponse::class.java).mapNotNull {
                usuarioResponse -> usuarioToDomain(usuarioResponse)
            } }
    }

    fun getAverias(): Flow<List<Averia>> {
        return db
            .collection(AVERIAS_PATH)
            .snapshots()
            .map { qs -> qs.toObjects(AveriaResponse::class.java).mapNotNull {
                averiaResponse -> averiaToDomain(averiaResponse)
            } }

            }
    private fun averiaToDomain(aR: AveriaResponse):Averia?{
        val averia = Averia(
            id = aR.id,
            titulo = aR.titulo,
            descripcion = aR.descripcion,
            tipoAveria = aR.tipoAveria,
            date = aR.date,
            prioridad = aR.prioridad,
            estado = aR.estado,
            usuarioCreador = aR.usuarioCreador,
            tecnico = aR.tecnico
        )
        return averia
    }


    private fun usuarioToDomain(uR: UsuarioResponse):Usuario?{

        val usuario = Usuario(
            id = uR.id,
            name = uR.name,
            email = uR.email,
            date = uR.date,
            rol = uR.rol
        )
        return usuario
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

    fun registrarAveria(averiaModel: Averia):Boolean {

        val averia = hashMapOf(
            "id" to averiaModel.id,
            "titulo" to averiaModel.titulo,
            "descripcion" to averiaModel.descripcion,
            "tipoAveria" to averiaModel.tipoAveria,
            "date" to averiaModel.date,
            "prioridad" to averiaModel.prioridad,
            "estado" to averiaModel.estado,
            "usuarioCreador" to averiaModel.usuarioCreador,
            "tecnico" to averiaModel.tecnico
        )

        return db.collection(AVERIAS_PATH).document(averiaModel.id).set(averia).isSuccessful
    }


}